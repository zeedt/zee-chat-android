package com.example.zeed.zeechat.contact.events;

import com.example.zeed.zeechat.contact.apimodel.UserModel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder @Setter @Getter
public class AddContactEvent {

    private UserModel userModel;

    private boolean successful;

    private String message;

}
