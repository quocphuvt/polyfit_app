package com.example.polyfit_app.service.remote;

import com.example.polyfit_app.models.Responses.DietsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DietsAPI {
    @GET("diets/getAll")
    Call<DietsResponse> getAllDiets();
}
