package com.example.polyfit_app.Service.remote;

import com.example.polyfit_app.Model.Exercise;
import com.example.polyfit_app.Model.Responses.ExerciseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ExerciseAPI {
    @GET("exercises/getAll")
    Call<ExerciseResponse> getAllExercise();

    @GET("exercises/exDetail/{id}")
    Call<ExerciseResponse> getExerciseDetails(@Path("id") int id);
}
