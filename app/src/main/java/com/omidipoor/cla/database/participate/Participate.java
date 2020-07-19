package com.omidipoor.cla.database.participate;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import com.omidipoor.cla.database.fence.Fence;
import com.omidipoor.cla.database.user.User;
import com.omidipoor.cla.util.TimestampConverter;

import java.util.Date;

@Entity(tableName = "participate")
public class Participate {

    @PrimaryKey(autoGenerate = true)
    public int participateId; // @PrimaryKey public long userId;

    @ColumnInfo(name = "created_at")
    @TypeConverters({TimestampConverter.class})
    public Date createdAt;

    @ColumnInfo(name = "modified_at")
    @TypeConverters({TimestampConverter.class})
    public Date modifiedAt;

    //  reference to the primary key (on to one) of the parent entities
    public int fenceId;

    //  reference to the primary key (on to many) of the parent entities
    public int userOwnerId;




}
