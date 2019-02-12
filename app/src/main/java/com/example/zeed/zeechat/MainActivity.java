package com.example.zeed.zeechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.example.zeed.zeechat.contact.ui.ContactActivity;
import com.example.zeed.zeechat.login.apimodel.LoginRequest;
import com.example.zeed.zeechat.login.apimodel.LoginErrorResponse;
import com.example.zeed.zeechat.login.events.LoginEvent;
import com.example.zeed.zeechat.login.operation.LoginOperation;
import com.example.zeed.zeechat.login.services.LoginOperationImpl;
import com.example.zeed.zeechat.signup.ui.SignUp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout usernameInputLayout;
    private TextInputLayout passwordInputLayout;
    private LoginOperation loginOperation;
    private ConstraintLayout parentConstraintLayout;
    private ProgressBar progressBar;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        assignFieldsToVariables();
        checkIfUserIsAlreadyLoggedIn();

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        loginOperation = new LoginOperationImpl(this);
    }

    private void checkIfUserIsAlreadyLoggedIn() {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("ZeeChat", MODE_PRIVATE);

        String token =  prefs.getString("token", null);
        String username =  prefs.getString("username", null);

        if (!TextUtils.isEmpty(token)) {
            navigateToLandingPage();
            return;
        }

        scrollView.setVisibility(View.VISIBLE);

    }

    private void assignFieldsToVariables() {
        usernameInputLayout = findViewById(R.id.username);
        passwordInputLayout = findViewById(R.id.password);
        parentConstraintLayout = findViewById(R.id.parent_constraint_layout);
        progressBar = findViewById(R.id.contentLoadingProgressBar);
        scrollView = findViewById(R.id.login_scroll_view);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void loadSignUpPage(View view) {
        Intent signUpPage = new Intent(this, SignUp.class);
        startActivity(signUpPage);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessfulLogin(LoginEvent loginEvent) {
        loginOperation.dismissLoginProgress();
        if (loginEvent.isSuccessful()) {

            persistCredentialsAndNavigateToLandingPage(loginEvent.getLoginResponse());

        } else {
            setLoginFormVisible();
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this)
            .setTitle("Error").setMessage(loginEvent.getLoginResponse().getErrorDescription())
            .setPositiveButton("OK" , null);
            alertBuilder.show();

        }
    }

    private void persistCredentialsAndNavigateToLandingPage(LoginErrorResponse loginResponse) {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ZeeChat", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("token", loginResponse.getAccessToken());
        editor.putString("firstName", loginResponse.getFirstName());
        editor.putString("lastName", loginResponse.getLastName());
        editor.putString("username", loginResponse.getUsername());
        editor.commit();

        navigateToLandingPage();
    }

    private void navigateToLandingPage() {
        Intent intent = new Intent(this, ContactActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    public void validateAndSignInUser(View view) {
        String username = usernameInputLayout.getEditText().getText().toString();
        String password = passwordInputLayout.getEditText().getText().toString();
        LoginErrorResponse validationResponse = loginOperation.validateCredentials(username, password);
        if (validationResponse == null) {
            signInUser(new LoginRequest(username, password));
        }
    }

    private void signInUser(LoginRequest loginRequest) {
        setProgressBarVisible();
        loginOperation.loginUser(loginRequest);
    }

    private void setLoginFormVisible() {
        parentConstraintLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void setProgressBarVisible() {
        parentConstraintLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

}
