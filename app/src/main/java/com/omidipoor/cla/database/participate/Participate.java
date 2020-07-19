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

    public Participate() {

    }

    public Participate(int participateId, Date createdAt, Date modifiedAt, int fenceId, int userOwnerId) {
        this.participateId = participateId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.fenceId = fenceId;
        this.userOwnerId = userOwnerId;
    }

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


    public int getParticipateId() {
        return participateId;
    }

    public void setParticipateId(int participateId) {
        this.participateId = participateId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public int getFenceId() {
        return fenceId;
    }

    public void setFenceId(int fenceId) {
        this.fenceId = fenceId;
    }

    public int getUserOwnerId() {
        return userOwnerId;
    }

    public void setUserOwnerId(int userOwnerId) {
        this.userOwnerId = userOwnerId;
    }
}
