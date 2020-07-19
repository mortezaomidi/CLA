package com.omidipoor.cla.database;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.omidipoor.cla.database.fence.Fence;
import com.omidipoor.cla.database.participate.Participate;
import com.omidipoor.cla.database.user.User;

import java.util.List;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    // this methods used for adding pre define data to db
    private static User addUser(final AppDatabase db, User user) {
        db.userDao().insertAll(user);
        return user;
    }

    private static Fence addFence(final AppDatabase db, Fence fence) {
        db.fenceDao().insertAll(fence);
        return fence;
    }

    private static Participate addParticipate(final AppDatabase db, Participate participate) {
        db.participateDao().insertAll(participate);
        return participate;
    }

    private static void populateWithTestData(AppDatabase db) {
        User user = new User();
        user.setFirstName("Morteza");
        user.setLastName("Omidipoor");
        user.setAge(25);
        addUser(db, user);

        List<User> userList = db.userDao().getAll();
        Log.d(DatabaseInitializer.TAG, "Rows Count: " + userList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}
