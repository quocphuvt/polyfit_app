package com.example.polyfit_app.service.remote;

import com.example.polyfit_app.model.response.DishesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DishesAPI {
    @GET("dishes/getAllDishesByMeal/{id}")
    Call<DishesResponse> getDishesByMeal(@Path("id") int id);
}
