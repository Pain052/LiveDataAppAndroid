package com.example.recyclerviewlivedataapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.recyclerviewlivedataapplication.model.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user")
    List<User> getAllUser();

    @Query("SELECT * FROM user WHERE username = :username")
    List<User> checkUserExist(String username);

    @Query("SELECT * FROM user WHERE username LIKE '%' || :username || '%'")
    List<User> searchUser(String username);
}
