package com.example.polyfit_app.Service.remote;

import com.example.polyfit_app.Model.Diet;
import com.example.polyfit_app.Model.Responses.DietsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DietsAPI {
    @GET("diets/getAll")
    Call<DietsResponse> getAllDiets();
}
