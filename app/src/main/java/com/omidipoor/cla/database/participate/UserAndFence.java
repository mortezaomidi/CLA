package com.omidipoor.cla.database.participate;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.omidipoor.cla.database.user.User;

public class UserAndFence {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "fenceId"
    )
    public Participate participate;
}
