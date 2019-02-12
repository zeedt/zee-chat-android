package com.example.zeed.zeechat.login.services;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.zeed.zeechat.login.apimodel.LoginRequest;
import com.example.zeed.zeechat.login.apimodel.LoginErrorResponse;
import com.example.zeed.zeechat.login.events.LoginEvent;
import com.example.zeed.zeechat.login.operation.LoginOperation;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginOperationImpl implements LoginOperation {

    private static final String CLIENT_ID = "zeechat-service";

    private static final String CLIENT_SECRET = "secret";

    private static final String GRANT_TYPE = "password";

    private static final String BASE_URL = "http://10.0.2.2:8976";

    private static Retrofit retrofit;

    private Context context;

    private static final String DEFAULT_ERROR_MESSAGE = "System error occurred. Please try again";

    public LoginOperationImpl(Context context) {
        this.context = context;
    }

    @Override
    public void loginUser(LoginRequest loginRequest) {
        loginRequest.setClientId(CLIENT_ID);
        loginRequest.setClientSecret(CLIENT_SECRET);
        loginRequest.setGrantType(GRANT_TYPE);

        retrofit = setUpRetrofit();
        makeLoginCall(loginRequest);
    }

    @Override
    public LoginErrorResponse validateCredentials(String username, String password) {
        if (TextUtils.isEmpty(username) || username.length() < 6) {
            return  LoginErrorResponse.builder().message("Username cannot be less than 6 characters").build();
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            return LoginErrorResponse.builder().message("Password cannot be less than 6 characters").build();
        }

        return null;

    }

    @Override
    public void makeLoginCall(LoginRequest loginRequest) {

        LoginApi loginApi = retrofit.create(LoginApi.class);

        String credentials = Credentials.basic(CLIENT_ID, CLIENT_SECRET);

        try {
            Response<LoginErrorResponse> responseCallback = loginApi.getAccessToken(loginRequest, GRANT_TYPE, loginRequest.getUsername(), loginRequest.getPassword(),
                    credentials).execute();

            if (responseCallback.code() == 200)  {
                dispatchSuccessfulLoginEvent(responseCallback.body());
            } else {
                dispatchFailureLoginEvent(processLoginResponseFromErrorBody(responseCallback));
            }
        } catch (Exception e) {
            Log.e("ERROR","Error occurred while login in due to ", e);
            dispatchFailureLoginEvent(LoginErrorResponse.builder().message(DEFAULT_ERROR_MESSAGE).build());
        }

    }

    private static Retrofit setUpRetrofit() {
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

    @Override
    public LoginErrorResponse processLoginResponseFromErrorBody(Response response) {

        try {
            Gson gson = new Gson();
            return gson.fromJson(response.errorBody().string(), LoginErrorResponse.class);
        } catch (Exception e) {
            Log.e("GSON_ERROR", "Error occurred due to ", e);
        }

        return null;
    }

    @Override
    public void dispatchSuccessfulLoginEvent(LoginErrorResponse loginResponse) {
        EventBus.getDefault().post(new LoginEvent(true, loginResponse));
    }

    @Override
    public void dispatchFailureLoginEvent(LoginErrorResponse loginResponse) {
        EventBus.getDefault().post(new LoginEvent(false, loginResponse));
    }

    @Override
    public void showLoginProgress() {

    }

    @Override
    public void dismissLoginProgress() {

    }
}
