package com.example.android.asiannations.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DAO {

    @Insert
    public void insertNation(Nations nations);

    @Query("Select * from Nations")
    List<Nations> getNations();

    @Query("Delete from Nations")
     void nukeTable();

}
