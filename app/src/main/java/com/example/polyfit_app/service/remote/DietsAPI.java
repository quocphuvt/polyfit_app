package com.example.polyfit_app.service.remote;

import com.example.polyfit_app.model.response.DietsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DietsAPI {
    @GET("diets/getAll")
    Call<DietsResponse> getAllDiets();

    @GET("diets/getAllDishesByDiets/{id}")
    Call<DietsResponse> getDietData(@Path("id") int id);

    @GET("diets/getAll/{levelId}")
    Call<DietsResponse> getAllDietsByLevelId(@Path("levelId") Integer levelId);
}
