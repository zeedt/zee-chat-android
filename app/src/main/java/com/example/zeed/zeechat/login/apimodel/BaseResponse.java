package com.example.zeed.zeechat.login.apimodel;

import com.google.gson.annotations.SerializedName;


public class BaseResponse {

    @SerializedName("error")
    private String error;

    @SerializedName("error_description")
    private String errorDescription;

    public BaseResponse(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
