package com.omidipoor.cla.database.participate;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;


@Dao
public interface ParticipateDao {

    @Transaction
    @Query("SELECT * FROM User")
    public List<UserAndFence> getUsersAndFences();

    // returns all instances of the data class that pairs the parent entity(User) and the
    // child entity(Participate).    // This method requires Room to run two queries,
    // so add the @Transaction annotation to this
    // method to ensure that the whole operation is performed atomically.
    @Transaction
    @Query("SELECT * FROM User")
    public List<UserWithParticipates> getUsersWithParticipateList();

    @Insert
    Long insertParticipate(Participate participate);


    @Query("SELECT * FROM participate ORDER BY created_at desc")
    LiveData<List<Participate>> fetchAllParticipates();


    @Query("SELECT * FROM participate WHERE participateId =:participateId")
    LiveData<Participate> getParticipate(int participateId);


    @Update
    void updateParticipate(Participate participate);


    @Delete
    void deleteParticipate(Participate participate);
}
