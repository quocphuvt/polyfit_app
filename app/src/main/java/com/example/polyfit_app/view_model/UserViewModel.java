package com.example.polyfit_app.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.polyfit_app.models.User;
import com.example.polyfit_app.view_model.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    public UserViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository(application);
    }

    public LiveData<User> getUser() {
        return userRepository.getUserLiveData();
    }

    public void setUser() {
        userRepository.setUserLiveData();
    }
}
