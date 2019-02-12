package com.example.zeed.zeechat.login.apimodel;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor
public class BaseErrorResponse {

    @SerializedName("error")
    private String error;

    @SerializedName("error_description")
    private String errorDescription;

    private String message;

}
