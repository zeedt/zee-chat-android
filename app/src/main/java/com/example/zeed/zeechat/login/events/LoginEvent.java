package com.example.zeed.zeechat.login.events;


import com.example.zeed.zeechat.login.apimodel.LoginResponse;

public class LoginEvent {

    private String errorMessage;

    private boolean successful;

    private LoginResponse loginResponse;

    public LoginEvent(String errorMessage, boolean successful) {
        this.errorMessage = errorMessage;
        this.successful = successful;
    }

    public LoginEvent(String errorMessage, boolean successful, LoginResponse loginResponse) {
        this.errorMessage = errorMessage;
        this.successful = successful;
        this.loginResponse = loginResponse;
    }

    public LoginEvent(boolean successful, LoginResponse loginResponse) {
        this.successful = successful;
        this.loginResponse = loginResponse;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }
}
