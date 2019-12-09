package com.example.polyfit_app.service.local.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.polyfit_app.model.Reminder;

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
    @Query("UPDATE polyfit_reminder SET turnOn=:turnOn WHERE id = :id")
    void switchState(int turnOn, int id);

}
