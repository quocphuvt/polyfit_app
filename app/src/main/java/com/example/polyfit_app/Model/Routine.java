package com.example.polyfit_app.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Hades on 30,October,2019
 **/
@Entity(tableName = "polyfit_routine")
public class Routine {
    @ColumnInfo(name = "step_count")
    private int stepCount;
    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "createdAt")
    private String createdAt;
    @ColumnInfo(name = "updatedAt")
    private String updatedAt;
    @ColumnInfo(name = "time_practice")
    private String timePractice;
    @ColumnInfo(name = "calories_consumed")
    private String caloriesConsumed;
    @ColumnInfo(name = "polyfitUserId")
    private int polyfitUserId;


    public Routine() {
    }


    @Ignore
    public Routine(int stepCount, String createdAt, String updatedAt, String timePractice, String caloriesConsumed, int polyfitUserId) {
        this.stepCount = stepCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.timePractice = timePractice;
        this.caloriesConsumed = caloriesConsumed;
        this.polyfitUserId = polyfitUserId;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTimePractice() {
        return timePractice;
    }

    public void setTimePractice(String timePractice) {
        this.timePractice = timePractice;
    }

    public String getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public void setCaloriesConsumed(String caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }

    public int getPolyfitUserId() {
        return polyfitUserId;
    }

    public void setPolyfitUserId(int polyfitUserId) {
        this.polyfitUserId = polyfitUserId;
    }
}

