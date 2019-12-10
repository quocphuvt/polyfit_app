package com.example.polyfit_app.diet;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.polyfit_app.model.User;
import com.example.polyfit_app.model.response.DietsResponse;
import com.example.polyfit_app.user.UserRepository;

public class DietViewModel extends AndroidViewModel {
    private DietRepository dietRepository;
    public DietViewModel(Application application) {
        super(application);
        this.dietRepository = new DietRepository(application);
    }

    public LiveData<DietsResponse> getDietData() {
        return dietRepository.getMutableLiveData();
    }

    public void setDietData() {
        dietRepository.setDietData();
    }
}
