package com.example.zeed.zeechat;

import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zeed.zeechat.contact.ui.ContactActivity;
import com.example.zeed.zeechat.login.apimodel.LoginRequest;
import com.example.zeed.zeechat.login.apimodel.LoginResponse;
import com.example.zeed.zeechat.login.events.LoginEvent;
import com.example.zeed.zeechat.login.operation.LoginOperation;
import com.example.zeed.zeechat.login.services.LoginOperationImpl;
import com.example.zeed.zeechat.signup.ui.SignUp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private Button signinButton;
    private TextInputLayout usernameInputLayout;
    private TextInputLayout passwordInputLayout;
    private LoginOperation loginOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        loginOperation = new LoginOperationImpl(this);

        assignFieldssToVariables();
    }

    private void assignFieldssToVariables() {
        signinButton = (Button) findViewById(R.id.login_button);
        usernameInputLayout = (TextInputLayout) findViewById(R.id.username);
        passwordInputLayout = (TextInputLayout) findViewById(R.id.password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    public void loadSignUpPage(View view) {
        Intent signUpPage = new Intent(this, SignUp.class);
        startActivity(signUpPage);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessfulLogin(LoginEvent loginEvent) {
        loginOperation.dismissLoginProgress();
        if (loginEvent.isSuccessful()) {
            Intent intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, loginEvent.getLoginResponse().getErrorDescription(), Toast.LENGTH_LONG).show();
        }
    }


    public void validateAndSignInUser(View view) {
        String username = usernameInputLayout.getEditText().getText().toString();
        String password = passwordInputLayout.getEditText().getText().toString();
        LoginResponse validationResponse = loginOperation.validateCredentials(username, password);
        if (validationResponse == null) {
            signInUser(new LoginRequest(username, password));
            return;
        }


    }

    private void signInUser(LoginRequest loginRequest) {
        loginOperation.showLoginProgress();
        loginOperation.loginUser(loginRequest);
    }


}
