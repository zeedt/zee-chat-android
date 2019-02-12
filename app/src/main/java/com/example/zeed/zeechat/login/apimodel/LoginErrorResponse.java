package com.example.zeed.zeechat.login.apimodel;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginErrorResponse extends BaseErrorResponse {

    @SerializedName("username")
    private String username;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private String expiresIn;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("refresh_token")
    private String refreshToken;

    @Builder
    public LoginErrorResponse(String error, String errorDescription, String message, String username,
                              String lastName, String firstName, String accessToken, String expiresIn,
                              String tokenType, String refreshToken) {
        super(error, errorDescription, message);
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
    }
}
