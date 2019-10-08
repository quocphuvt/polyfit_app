package com.example.polyfit_app.Service.local.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.polyfit_app.Model.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM  polyfit_users")
    List<User> getAllUsers();

    @Insert
    void registerUser(User user);
}
