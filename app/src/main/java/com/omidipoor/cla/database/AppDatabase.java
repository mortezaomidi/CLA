package com.omidipoor.cla.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.omidipoor.cla.database.fence.Fence;
import com.omidipoor.cla.database.fence.FenceDao;
import com.omidipoor.cla.database.participate.Participate;
import com.omidipoor.cla.database.participate.ParticipateDao;
import com.omidipoor.cla.database.user.User;
import com.omidipoor.cla.database.user.UserDao;

@Database(entities = {User.class, Fence.class, Participate.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract FenceDao fenceDao();
    public abstract ParticipateDao participateDao();


    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "geo-database")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}