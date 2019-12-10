package com.example.polyfit_app.bodyparts;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.polyfit_app.model.User;
import com.example.polyfit_app.model.response.BodypartResponse;
import com.example.polyfit_app.service.remote.BodypartsAPI;
import com.example.polyfit_app.service.remote.RetrofitClient;
import com.example.polyfit_app.utils.Helpers;
import com.example.polyfit_app.utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BodyPartsRepository {
    private MutableLiveData<BodypartResponse> mutableLiveData = new MutableLiveData<>();
    private Application application;
    private Retrofit retrofit = RetrofitClient.getInstance();
    private BodypartsAPI bodypartsAPI;
    private User user;

    public BodyPartsRepository(Application application) {
        this.application = application;
        this.bodypartsAPI = retrofit.create(BodypartsAPI.class);
    }

    public MutableLiveData<BodypartResponse> getMutableLiveData() {
        setBodyPartData();
        return mutableLiveData;
    }

    public void setBodyPartData() {
        user = Helpers.getUserFromPreferences(application);
        int levelId = Util.getLevelId(user.getBmi());
        Call<BodypartResponse> bodypartResponseCall = bodypartsAPI.getAllBodypartsByLevelId(levelId);
        bodypartResponseCall.enqueue(new Callback<BodypartResponse>() {
            @Override
            public void onResponse(Call<BodypartResponse> call, Response<BodypartResponse> response) {
                if(response.isSuccessful()) {
                    BodypartResponse bodypartResponse = response.body();
                    if(bodypartResponse.getStatus() == 0) {
                        mutableLiveData.setValue(bodypartResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<BodypartResponse> call, Throwable t) {

            }
        });
    }
}
