package com.example.recyclerviewlivedataapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.recyclerviewlivedataapplication.dao.UserDAO;
import com.example.recyclerviewlivedataapplication.model.User;

@Database(entities = User.class, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "user_db";
    private static UserDatabase instance;

    public static synchronized UserDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
//                    .fallbackToDestructiveMigration() //khong cung cap Migrations va k quan tam den du lieu cu khi update
                    .build();
        }
        return instance;
    }
    public abstract UserDAO userDAO();
}
