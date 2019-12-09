package com.example.polyfit_app.view_model.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.polyfit_app.models.Responses.BodypartResponse;
import com.example.polyfit_app.service.remote.BodypartsAPI;
import com.example.polyfit_app.service.remote.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BodyPartsRepository {
    private MutableLiveData<BodypartResponse> bodypartResponseMutableLiveData = new MutableLiveData<>();
    private Application application;
    private Retrofit retrofit = RetrofitClient.getInstance();
    private BodypartsAPI bodypartsAPI;

    public BodyPartsRepository(Application application) {
        this.application = application;
        this.bodypartsAPI = retrofit.create(BodypartsAPI.class);
    }

    public MutableLiveData<BodypartResponse> getAllBodyParts() {
        if(bodypartResponseMutableLiveData == null) {
            setAllBodyParts();
        }
        return bodypartResponseMutableLiveData;
    }

    private void setAllBodyParts() {
        Call<BodypartResponse> responseCall = bodypartsAPI.getAllBodyParts();
        responseCall.enqueue(new Callback<BodypartResponse>() {
            @Override
            public void onResponse(Call<BodypartResponse> call, Response<BodypartResponse> response) {
                if(response.isSuccessful()) {
                    BodypartResponse bodypartResponse = response.body();
                    if(bodypartResponse.getStatus() == 0) {
                        bodypartResponseMutableLiveData.setValue(bodypartResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<BodypartResponse> call, Throwable t) {

            }
        });
    }
}
