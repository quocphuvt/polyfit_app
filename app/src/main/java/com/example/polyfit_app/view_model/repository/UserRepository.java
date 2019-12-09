package com.example.polyfit_app.view_model.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.polyfit_app.models.User;
import com.example.polyfit_app.utils.Helpers;

public class UserRepository {
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private Application application;

    public UserRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<User> getUserLiveData() {
        this.setUserLiveData();
        return userLiveData;
    }

    public void setUserLiveData() {
        User user = Helpers.getUserFromPreferences(this.application);
        userLiveData.setValue(user);
    }
}
