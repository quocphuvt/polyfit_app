package com.example.polyfit_app.user;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.polyfit_app.model.User;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    public UserViewModel(Application application) {
        super(application);
        this.userRepository = new UserRepository(application);
    }

    public LiveData<User> getUser() {
        return userRepository.getMutableLiveData();
    }

    public void setUser() {
        userRepository.setUserData();
    }
}
