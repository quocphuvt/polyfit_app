package com.example.polyfit_app.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.polyfit_app.models.Responses.DietsResponse;
import com.example.polyfit_app.view_model.repository.DietRepository;

public class DietViewModel extends AndroidViewModel {
    private DietRepository dietRepository;

    public DietViewModel(@NonNull Application application) {
        super(application);
        this.dietRepository = new DietRepository(application);
    }

    public LiveData<DietsResponse> getDietsByLevelId(Integer levelId) {
        return this.dietRepository.getDietsByLevelId(levelId);
    }

    public void updateDietDataByUser(Integer levelId) {
        this.dietRepository.getDietsByLevelId(levelId);
    }
}
