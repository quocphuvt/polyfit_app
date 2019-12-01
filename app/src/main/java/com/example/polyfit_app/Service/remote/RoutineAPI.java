package com.example.polyfit_app.Service.remote;

import com.example.polyfit_app.Model.Responses.RoutineResponse;
import com.example.polyfit_app.Model.Responses.UserResponse;
import com.example.polyfit_app.Model.Routine;
import com.example.polyfit_app.Model.RoutineRequest;
import com.example.polyfit_app.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Hades on 02,November,2019
 **/
public interface RoutineAPI {
    @GET("routine/getRoutinesByUserId/{id}")
    Call<RoutineResponse> getRoutinesByUserId(@Path("id") int userId);

    @POST("routine/create")
    Call<RoutineResponse> createRoutine (@Body() RoutineRequest routine);
}
