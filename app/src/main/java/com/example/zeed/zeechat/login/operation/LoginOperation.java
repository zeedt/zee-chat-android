package com.example.zeed.zeechat.login.operation;


import com.example.zeed.zeechat.login.apimodel.LoginRequest;
import com.example.zeed.zeechat.login.apimodel.LoginResponse;
import com.example.zeed.zeechat.login.events.LoginEvent;

import retrofit2.Response;

public interface LoginOperation {

    void   loginUser(LoginRequest loginRequest);

    LoginResponse validateCredentials(String username, String password);

    void makeLoginCall(LoginRequest loginRequest);

    LoginResponse processLoginResponseFromErrorBody(Response response);

    void dispatchSuccessfulLoginEvent(LoginResponse loginResponse);

    void dispatchFailureLoginEvent(LoginResponse loginResponse);

    void showLoginProgress();

    void dismissLoginProgress();

}
