package com.example.zeed.zeechat.contact.service;


import android.util.Log;

import com.example.zeed.zeechat.contact.apimodel.UserModel;
import com.example.zeed.zeechat.contact.events.AddContactEvent;
import com.example.zeed.zeechat.contact.operations.ContactOperation;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactOperationsImpl implements ContactOperation {

    private static final String BASE_URL = "http://10.0.2.2:8976";

    private static Retrofit retrofit;



    @Override
    public void addUserToList(String username, String email) {

        ContactApi contactApi = retrofit.create(ContactApi.class);

        Call<UserModel> apiResponse = contactApi.addUserToList(username, email);

        try {
            Response<UserModel> response = apiResponse.execute();
            if (response.code() == 200) {
                dispatchSuccessfulContactEvent(response.body());
                return;
            }

            if (response.code() == 500) {
                dispatchErrorContactEvent(response.body().getMessage());
                return;
            }



        } catch (Exception e) {
            Log.e("CONTACT_ERROR", "Error occurred while searching for user due to : ", e);
        }


    }

    static {
        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS).build();

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        private void dispatchSuccessfulContactEvent(UserModel userModel) {
            AddContactEvent addContactEvent = AddContactEvent.builder()
                    .successful(true)
                    .userModel(userModel)
                    .build();
            EventBus.getDefault().post(addContactEvent);
        }

        private void dispatchErrorContactEvent(String message) {
            AddContactEvent addContactEvent = AddContactEvent.builder()
                    .successful(true)
                    .message(message)
                    .build();
            EventBus.getDefault().post(addContactEvent);
        }
}
