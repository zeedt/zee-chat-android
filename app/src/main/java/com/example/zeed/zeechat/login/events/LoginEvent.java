package com.example.zeed.zeechat.login.events;


import com.example.zeed.zeechat.login.apimodel.LoginErrorResponse;

public class LoginEvent {

    private String errorMessage;

    private boolean successful;

    private LoginErrorResponse loginResponse;

    public LoginEvent(String errorMessage, boolean successful) {
        this.errorMessage = errorMessage;
        this.successful = successful;
    }

    public LoginEvent(String errorMessage, boolean successful, LoginErrorResponse loginResponse) {
        this.errorMessage = errorMessage;
        this.successful = successful;
        this.loginResponse = loginResponse;
    }

    public LoginEvent(boolean successful, LoginErrorResponse loginResponse) {
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

    public LoginErrorResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginErrorResponse loginResponse) {
        this.loginResponse = loginResponse;
    }
}
