package com.omidipoor.cla.database.participate;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.omidipoor.cla.database.user.User;

import java.util.List;

public class UserWithParticipates {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "userOwnerId"
    )
    public List<Participate> participateList;

}