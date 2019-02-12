package com.example.zeed.zeechat.login.operation;


import com.example.zeed.zeechat.login.apimodel.LoginRequest;
import com.example.zeed.zeechat.login.apimodel.LoginErrorResponse;

import retrofit2.Response;

public interface LoginOperation {

    void   loginUser(LoginRequest loginRequest);

    LoginErrorResponse validateCredentials(String username, String password);

    void makeLoginCall(LoginRequest loginRequest);

    LoginErrorResponse processLoginResponseFromErrorBody(Response response);

    void dispatchSuccessfulLoginEvent(LoginErrorResponse loginResponse);

    void dispatchFailureLoginEvent(LoginErrorResponse loginResponse);

    void showLoginProgress();

    void dismissLoginProgress();

}
