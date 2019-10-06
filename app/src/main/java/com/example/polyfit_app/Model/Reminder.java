package com.example.polyfit_app.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "polyfit_reminder")
public class Reminder {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "hour")
    private int hour;
    @ColumnInfo(name = "minute")
    private int minute;
    @ColumnInfo(name = "note")
    private String note;

    @Ignore
    public Reminder(int hour, int minute, String note) { //Insert
        this.hour = hour;
        this.minute = minute;
        this.note = note;
    }

    public Reminder(int id, int hour, int minute, String note) { //Get
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
