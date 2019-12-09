package com.example.polyfit_app.service.local.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.polyfit_app.model.StepCount;

import java.util.List;

/**
 * Created by Hades on 31,October,2019
 **/
@Dao
public interface StepDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StepCount... stepCounts);

    @Query("SELECT * FROM polyfit_step_count")
    List<StepCount> getStepCount();
}
