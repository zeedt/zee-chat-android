package com.example.zeed.zeechat.contact.service;

import com.example.zeed.zeechat.contact.apimodel.UserModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ContactApi {

    @GET("/user/fetch-user-details")
    Call<UserModel> addUserToList(@Query("username") String username, @Query("email") String email);

}
