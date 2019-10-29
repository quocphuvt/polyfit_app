package com.example.polyfit_app.Service.remote;

import com.example.polyfit_app.Model.Responses.LevelResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LevelAPI {
    @GET("level/getAll")
    Call<LevelResponse> getAllLevel();

    @GET("level/getOne/{id}")
    Call<LevelResponse> getLevelById(@Path("id") int levelId);

}
