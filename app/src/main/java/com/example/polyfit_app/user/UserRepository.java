package com.example.polyfit_app.user;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.polyfit_app.model.User;
import com.example.polyfit_app.service.remote.RetrofitClient;
import com.example.polyfit_app.utils.Helpers;

import retrofit2.Retrofit;

public class UserRepository {
    private MutableLiveData<User> mutableLiveData = new MutableLiveData<>();
    private Application application;
    private Retrofit retrofit = RetrofitClient.getInstance();

    public UserRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<User> getMutableLiveData() {
        this.setUserData();
        return mutableLiveData;
    }

    public void setUserData() {
        User user = Helpers.getUserFromPreferences(this.application);
        mutableLiveData.setValue(user);
    }
}
