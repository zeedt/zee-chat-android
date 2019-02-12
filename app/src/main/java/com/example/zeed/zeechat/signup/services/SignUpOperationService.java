package com.example.zeed.zeechat.signup.services;

import android.util.Log;

import com.example.zeed.zeechat.signup.apimodel.SignUpRequestApiModel;
import com.example.zeed.zeechat.signup.apimodel.SignUpResponseModel;
import com.example.zeed.zeechat.signup.events.SignUpEvent;
import com.example.zeed.zeechat.signup.operation.SignUpOperations;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpOperationService implements SignUpOperations {

    private Retrofit retrofit;

    private static final String BASE_URL = "http://10.0.2.2:8976";

    @Override
    public void signUp(SignUpRequestApiModel signUpRequestApiModel) {
        retrofit = setUpRetrofit();
        SignUpApi signUpApi = retrofit.create(SignUpApi.class);
        try {

            Response<SignUpResponseModel> signUpCallResponse = signUpApi.signUpUser(signUpRequestApiModel).execute();
            if (signUpCallResponse.code() == 200) {
                EventBus.getDefault().post(new SignUpEvent(signUpCallResponse.body().isSuccessful(), signUpCallResponse.body().getMessage()));
                return;
            }
            if (signUpCallResponse.code() == 400) {
                processBadRequestResponse(signUpCallResponse.errorBody());
                return;
            }
            EventBus.getDefault().post(new SignUpEvent(false, "Registration not successful"));
        } catch (Exception e) {
            Log.e("SIGNUP_ERROR", "Error occurred while registering user due to ", e);
            EventBus.getDefault().post(new SignUpEvent(false, "Registration not successful"));
        }

    }

    private void processBadRequestResponse(ResponseBody responseBody) {

        Gson gson = new Gson();
        try {
            SignUpResponseModel responseModel = gson.fromJson(responseBody.string(), SignUpResponseModel.class);
            if (responseModel.getError() != null && responseModel.getErrors().length > 0) {
                EventBus.getDefault().post(new SignUpEvent(false, responseModel.getErrors()[0].getDefaultMessage()));
                return;
            }
        } catch (Exception e) {
            Log.e("ERROR", "processBadRequestResponse: ", e);
        }
        EventBus.getDefault().post(new SignUpEvent(false, "Registration not successful"));
    }

    private Retrofit setUpRetrofit() {
        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS).build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
