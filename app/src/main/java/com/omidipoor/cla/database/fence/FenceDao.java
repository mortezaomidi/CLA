package com.omidipoor.cla.database.fence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.omidipoor.cla.database.fence.Fence;

import java.util.List;

@Dao
public interface FenceDao {

    @Query("SELECT * FROM fence")
    List<Fence> getAll();

    @Insert
    void insertAll(Fence... fences);

    @Delete
    void delete(Fence fence);

}
