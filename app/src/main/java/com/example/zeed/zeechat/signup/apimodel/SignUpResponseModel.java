package com.example.zeed.zeechat.signup.apimodel;

public class SignUpResponseModel {

    private String error;

    private Error[] errors;

    private String message;

    private boolean successful;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Error[] getErrors() {
        return errors;
    }

    public void setErrors(Error[] errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
