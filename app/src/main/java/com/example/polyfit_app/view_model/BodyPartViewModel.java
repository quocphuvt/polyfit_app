package com.example.polyfit_app.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.polyfit_app.models.Responses.BodypartResponse;
import com.example.polyfit_app.view_model.repository.BodyPartsRepository;

public class BodyPartViewModel extends AndroidViewModel {
    private BodyPartsRepository bodyPartsRepository;
    public BodyPartViewModel(@NonNull Application application) {
        super(application);
        this.bodyPartsRepository = new BodyPartsRepository(application);
    }

    private LiveData<BodypartResponse> getAllBodyparts() {
        return this.bodyPartsRepository.getAllBodyParts();
    }
}
