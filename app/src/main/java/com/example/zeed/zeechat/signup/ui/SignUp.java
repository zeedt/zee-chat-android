package com.example.zeed.zeechat.signup.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.zeed.zeechat.MainActivity;
import com.example.zeed.zeechat.R;
import com.example.zeed.zeechat.signup.apimodel.SignUpRequestApiModel;
import com.example.zeed.zeechat.signup.events.SignUpEvent;
import com.example.zeed.zeechat.signup.operation.SignUpOperations;
import com.example.zeed.zeechat.signup.operation.SignupInputValidation;
import com.example.zeed.zeechat.signup.services.SignUpOperationService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SignUp extends AppCompatActivity  implements SignupInputValidation {

    private TextInputLayout usernameLayout;
    private TextInputLayout passwordLayout;
    private TextInputLayout passwordConfirmationLayout;
    private TextInputLayout firstNameLayout;
    private TextInputLayout lastNameLayout;
    private TextInputLayout emailLayout;

    private boolean usernameValid = false;
    private boolean firstNameValid = false;
    private boolean lastNameValid = false;
    private boolean emailValid = false;
    private boolean passwordValid = false;

    private SignUpOperations signUpOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        signUpOperations = new SignUpOperationService();
        assignFieldsToVariable();
        setRegistrationFieldsKeyListener();
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    private void assignFieldsToVariable() {
        usernameLayout = findViewById(R.id.username);
        passwordLayout = findViewById(R.id.password);
        passwordConfirmationLayout = findViewById(R.id.confirmPassword);
        firstNameLayout = findViewById(R.id.firstName);
        lastNameLayout = findViewById(R.id.lastName);
        emailLayout = findViewById(R.id.email);
    }

    public void signUp(View view) {

        SignUpRequestApiModel requestApiModel = validateSignUp();
        if (requestApiModel != null) {
            signUpOperations.signUp(requestApiModel);
        }

    }

    @Override
    public void setRegistrationFieldsKeyListener() {

        usernameLayout.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (usernameLayout.getEditText().getText().toString().length() < 4){
                    usernameLayout.setErrorEnabled(true);
                    usernameLayout.setHint("Username cannot be less than 4 characters");
                    usernameValid = false;
                } else {
                    usernameLayout.setErrorEnabled(false);
                    usernameLayout.setHint("");
                    usernameValid = true;
                }
                return false;
            }
        });

        passwordLayout.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (passwordLayout.getEditText().getText().toString().length() < 4){
                    passwordLayout.setErrorEnabled(true);
                    passwordLayout.setHint("Password cannot be less than 4 characters");
                    passwordValid = false;
                } else {
                    passwordLayout.setErrorEnabled(false);
                    passwordLayout.setHint("");
                    passwordValid = true;
                }
                return false;
            }
        });

        firstNameLayout.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (firstNameLayout.getEditText().getText().toString().length() < 4){
                    firstNameLayout.setErrorEnabled(true);
                    firstNameLayout.setHint("First name cannot be less than 3 characters");
                    firstNameValid = false;
                } else {
                    firstNameLayout.setErrorEnabled(false);
                    firstNameLayout.setHint("");
                    firstNameValid = true;
                }
                return false;
            }
        });

        lastNameLayout.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (lastNameLayout.getEditText().getText().toString().length() < 4){
                    lastNameLayout.setErrorEnabled(true);
                    lastNameLayout.setHint("Last name cannot be less than 3 characters");
                    lastNameValid = false;
                } else {
                    lastNameLayout.setErrorEnabled(false);
                    lastNameLayout.setHint("");
                    lastNameValid = true;
                }
                return false;
            }
        });

        emailLayout.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (emailLayout.getEditText().getText().toString().length() < 4){
                    emailLayout.setErrorEnabled(true);
                    emailLayout.setHint("Email address cannot be less than 10 characters");
                    emailValid = false;
                } else {
                    emailLayout.setErrorEnabled(false);
                    emailLayout.setHint("");
                    emailValid = true;
                }
                return false;
            }
        });

        passwordConfirmationLayout.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (passwordConfirmationLayout.getEditText().getText().toString().length() < 4){
                    passwordConfirmationLayout.setErrorEnabled(true);
                    passwordConfirmationLayout.setHint("Password cannot be less than 4 characters");
                    passwordValid = false;
                } else if(!passwordConfirmationLayout.getEditText().getText().toString().trim().equals(passwordLayout.getEditText().getText().toString().trim())) {
                    passwordConfirmationLayout.setErrorEnabled(true);
                    passwordConfirmationLayout.setHint("Passwords do not match");
                    passwordValid = false;
                }
                else {
                    passwordConfirmationLayout.setErrorEnabled(false);
                    passwordConfirmationLayout.setHint("");
                    passwordValid = true;
                }
                return false;
            }
        });
    }


    @Override
    public SignUpRequestApiModel validateSignUp() {
        if (emailValid == true && firstNameValid == true && lastNameValid == true &&
                passwordValid == true && usernameValid == true) {
            SignUpRequestApiModel requestApiModel = new SignUpRequestApiModel();
            requestApiModel.setEmail(emailLayout.getEditText().getText().toString().trim());
            requestApiModel.setUsername(usernameLayout.getEditText().getText().toString().trim());
            requestApiModel.setPassword(passwordLayout.getEditText().getText().toString().trim());
            requestApiModel.setFirstName(firstNameLayout.getEditText().getText().toString().trim());
            requestApiModel.setLastName(lastNameLayout.getEditText().getText().toString().trim());
            return requestApiModel;
        }

        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFinishedSignUpCall(SignUpEvent signUpEvent) {
        if (signUpEvent.isSuccessful()){
            showConfirmDialogAndRedirectToLoginPage();
            return;
        }
        displayErrorMesssageInDialogBox(signUpEvent.getMessage());
    }

    private void displayErrorMesssageInDialogBox(String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Error")
                .setMessage(message)
                .setNeutralButton("OK", null).show();
    }

    private void showConfirmDialogAndRedirectToLoginPage() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Success")
                .setMessage("Successfully registered")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gotoSignInPage();
            }
        }).show();
    }

    private void gotoSignInPage() {
        Intent loginIntent = new Intent(this, MainActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }


}
