package com.example.polyfit_app.service.remote;

import com.example.polyfit_app.model.response.ExerciseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ExerciseAPI {
    @GET("exercises/getAll")
    Call<ExerciseResponse> getAllExercise();

    @GET("exercises/exDetail/{id}")
    Call<ExerciseResponse> getExerciseDetails(@Path("id") int id);
}
