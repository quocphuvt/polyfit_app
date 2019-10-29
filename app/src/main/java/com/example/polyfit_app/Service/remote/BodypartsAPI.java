package com.example.polyfit_app.Service.remote;

import com.example.polyfit_app.Model.Responses.BodypartResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BodypartsAPI {
    @GET("bodyparts/getAll")
    Call<BodypartResponse> getAllBodyParts();
}
