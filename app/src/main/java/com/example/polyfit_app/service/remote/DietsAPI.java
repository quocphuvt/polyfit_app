package com.example.polyfit_app.service.remote;

import com.example.polyfit_app.models.Responses.DietsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DietsAPI {
    @GET("diets/getAll")
    Call<DietsResponse> getAllDiets();

    @GET("diets/getAll/{levelId}")
    Call<DietsResponse> getAllDietsByLevelId(@Path("levelId") Integer levelId);
}
