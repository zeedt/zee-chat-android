package com.example.zeed.zeechat.login.services;


import com.example.zeed.zeechat.login.apimodel.LoginRequest;
import com.example.zeed.zeechat.login.apimodel.LoginErrorResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginApi {

    @POST("/oauth/token")
    Call<LoginErrorResponse> getAccessToken(@Body LoginRequest loginRequest, @Query("grant_type") String grantType,
                                            @Query("username") String username, @Query("password") String password,
                                            @Header("Authorization") String credentials);

    @GET("/users")
    Call<Boolean> pingService(@Header("Authorization") String token);
}
