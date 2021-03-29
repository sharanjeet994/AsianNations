package com.example.android.asiannations.room;


import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Nations.class},version = 1,exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract DAO dao();
}
