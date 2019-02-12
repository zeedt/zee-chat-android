package com.example.zeed.zeechat.contact.apimodel;


import com.example.zeed.zeechat.login.apimodel.BaseErrorResponse;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserModel extends BaseErrorResponse implements Serializable {

    private String id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String profilePicture;

    private Date createdTime;

    @Builder
    public UserModel(String error, String errorDescription, String message, String id, String username,
                     String firstName, String lastName, String email, String password, String profilePicture,
                     Date createdTime) {
        super(error, errorDescription, message);
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
        this.createdTime = createdTime;
    }
}
