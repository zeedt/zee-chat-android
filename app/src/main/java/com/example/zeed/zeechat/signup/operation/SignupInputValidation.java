package com.example.zeed.zeechat.signup.operation;

import com.example.zeed.zeechat.signup.apimodel.SignUpRequestApiModel;

/**
 * Created by zeed on 2/3/19.
 */

public interface SignupInputValidation {

    void setRegistrationFieldsKeyListener();

    SignUpRequestApiModel validateSignUp();

}
