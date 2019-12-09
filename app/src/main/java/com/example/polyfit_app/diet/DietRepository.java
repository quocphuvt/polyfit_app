package com.example.polyfit_app.diet;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.polyfit_app.model.User;
import com.example.polyfit_app.model.response.DietsResponse;
import com.example.polyfit_app.service.remote.DietsAPI;
import com.example.polyfit_app.service.remote.RetrofitClient;
import com.example.polyfit_app.utils.Helpers;
import com.example.polyfit_app.utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DietRepository {
    private MutableLiveData<DietsResponse> mutableLiveData = new MutableLiveData<>();
    private Application application;
    private Retrofit retrofit = RetrofitClient.getInstance();
    private DietsAPI dietsAPI;

    public DietRepository(Application application) {
        this.application = application;
        this.dietsAPI = retrofit.create(DietsAPI.class);
    }

    public MutableLiveData<DietsResponse> getMutableLiveData() {
        setDietData();
        return mutableLiveData;
    }

    public void setDietData() {
        User user = Helpers.getUserFromPreferences(this.application);
        Call<DietsResponse> dietsResponseCall = dietsAPI.getAllDietsByLevelId(Util.getLevelId(user.getBmi()));
        dietsResponseCall.enqueue(new Callback<DietsResponse>() {
            @Override
            public void onResponse(Call<DietsResponse> call, Response<DietsResponse> response) {
                if(response.isSuccessful()) {
                    DietsResponse dietsResponse = response.body();
                    if(dietsResponse.getStatus() == 0) {
                        mutableLiveData.setValue(dietsResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<DietsResponse> call, Throwable t) {

            }
        });
    }
}
