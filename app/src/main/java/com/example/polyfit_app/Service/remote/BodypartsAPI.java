package com.example.polyfit_app.Service.remote;

import com.example.polyfit_app.Model.Responses.BodypartResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BodypartsAPI {
    @GET("bodyparts/getAll")
    Call<BodypartResponse> getAllBodyParts();

    @GET("bodyparts/getDetailBodyparts/{id}")
    Call<BodypartResponse> getDataOfBodypart(@Path("id") int id);
}
