package com.example.polyfit_app.Service.remote;


import com.example.polyfit_app.Model.Response;
import com.example.polyfit_app.Model.Responses.UserResponse;
import com.example.polyfit_app.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface    PolyFitService {
    @POST("user/register")
    Call<UserResponse> registerUser(@Body() User user);

    @POST("user/login")
    Call<UserResponse> loginUser(@Body() User user);

    @GET("user/{username}")
    Call<String> getUserByUserName(@Path("username") String username);


    @POST("history/add")
    @FormUrlEncoded
    Observable<String> addHistory(@Field("bmi") Float bmi,
                                  @Field("id_user") Integer id_user);

    @GET("user/profile")
    Call<String> getUser(@Header("Authorization") String token);


//    @POST("user/register")
//    Observable<Response> register(@Body User user);
//
//    @POST("user/login")
//    Observable<Response> login();
//
//    @GET("users/{email}")
//    Observable<User> getProfile(@Path("email") String email);
//
//    @PUT("users/{email}")
//    Observable<Response> changePassword(@Path("email") String email, @Body User user);
//
//    @POST("users/{email}/password")
//    Observable<Response> resetPasswordInit(@Path("email") String email);
//
//    @POST("users/{email}/password")
//    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body User user);


//
//    // Category
//    @GET("categories/listAllCategory")
//    Call<List<Category>> getAllCategoryDetail();
//
//    @POST("categories/")
//    Call<Category> createCategory(@Body Category category);
//
//    @DELETE("categories/{id}")
//    Call<Category> deleteCategory(@Path("id") String _id);
//
//    @PUT("categories/{id}")
//    Call<Category> updateCategory(@Path("id") String _id, @Body Category category);
//
//    // Product
//    @GET("products/listAllProduct")
//    Call<List<Product>> getAllProductDetail();
//
//    @POST("products/")
//    Call<Product> createProduct(@Body Product product);
//
//    @DELETE("products/{id}")
//    Call<Product> deleteProduct(@Path("id") String _id);
//
//    @PUT("products/{id}")
//    Call<Product> updateProduct(@Path("id") String _id, @Body Product product);

}
