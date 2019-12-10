package com.example.polyfit_app.bodyparts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.polyfit_app.model.response.BodypartResponse;

import retrofit2.http.Body;

public class BodyPartViewModel extends AndroidViewModel {
    private BodyPartsRepository bodyPartsRepository;

    public BodyPartViewModel(@NonNull Application application) {
        super(application);
        this.bodyPartsRepository = new BodyPartsRepository(application);
    }

    public LiveData<BodypartResponse> getBodyPartLiveData() {
        return this.bodyPartsRepository.getMutableLiveData();
    }

    public void setBodyPartLiveData() {
        this.bodyPartsRepository.setBodyPartData();
    }
}
