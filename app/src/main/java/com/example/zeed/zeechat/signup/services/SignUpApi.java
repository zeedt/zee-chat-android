package com.example.zeed.zeechat.signup.services;

import com.example.zeed.zeechat.signup.apimodel.SignUpRequestApiModel;
import com.example.zeed.zeechat.signup.apimodel.SignUpResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpApi {

    @POST("/user/signup")
    Call<SignUpResponseModel> signUpUser(@Body SignUpRequestApiModel requestApiModel);

    @POST("/user/signup")
    Call<Object> signUpUserObject(@Body SignUpRequestApiModel requestApiModel);

}
