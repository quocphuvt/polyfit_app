package com.example.polyfit_app.view_model.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.polyfit_app.models.Responses.DietsResponse;
import com.example.polyfit_app.service.remote.DietsAPI;
import com.example.polyfit_app.service.remote.RetrofitClient;

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

    public MutableLiveData<DietsResponse> getDietsByLevelId(Integer levelId) {
        Call<DietsResponse> dietsResponseCall = this.dietsAPI.getAllDietsByLevelId(levelId);
        dietsResponseCall.enqueue(new Callback<DietsResponse>() {
            @Override
            public void onResponse(Call<DietsResponse> call, Response<DietsResponse> response) {
                if(response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DietsResponse> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
}
