package com.omidipoor.cla.database.participate;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.omidipoor.cla.database.fence.Fence;
import com.omidipoor.cla.database.user.User;

public class FenceAndParticipate {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "fenceId"
    )
    public Participate participate;
}
