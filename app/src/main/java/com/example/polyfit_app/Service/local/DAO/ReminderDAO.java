package com.example.polyfit_app.Service.local.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.polyfit_app.Model.Reminder;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ReminderDAO  {
    @Insert
     void insert(Reminder ... reminder);
    @Query("SELECT * FROM polyfit_reminder")
    List<Reminder> getReminder();
    @Update
    void update(Reminder ... reminders);
    @Delete
    void delete(Reminder reminders);

}
