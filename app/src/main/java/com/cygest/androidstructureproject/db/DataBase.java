package com.cygest.androidstructureproject.db;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cygest.androidstructureproject.db.dao.UserDAO;
import com.cygest.androidstructureproject.db.entities.UserEntity;

@Database(entities = { UserEntity.class }, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {
    private static DataBase instance;

    public static DataBase getInstance(Application application) {
        DataBase db = instance;
        if (db != null) {
            return db;
        }
        synchronized (DataBase.class) {
            if (instance == null) {
                instance = Room.databaseBuilder(application, DataBase.class, "your_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
            }
            return instance;
        }
    }

    public abstract UserDAO userDAO();
}
