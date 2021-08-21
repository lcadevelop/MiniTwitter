package com.lcadevelop.android.minitwitter.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lcadevelop.android.minitwitter.R;
import com.lcadevelop.android.minitwitter.common.Constant;
import com.lcadevelop.android.minitwitter.common.SharedPreferencesManager;
import com.lcadevelop.android.minitwitter.retrofit.MiniTwitterClient;
import com.lcadevelop.android.minitwitter.retrofit.MiniTwitterService;
import com.lcadevelop.android.minitwitter.retrofit.request.RequestSignUp;
import com.lcadevelop.android.minitwitter.retrofit.response.ResponseSignUp;
import com.lcadevelop.android.minitwitter.ui.home.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity
{
    EditText editTextsignusername;
    EditText editTextsignemail;
    EditText editTextsignpassword;

    String username;
    String email;
    String password;

    MiniTwitterClient miniTwitterClient;
    MiniTwitterService miniTwitterService;

    Button btnSignUp;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        retrofitInit();
        findViews();
        eventsInit();
    }

    public void retrofitInit()
    {
        miniTwitterClient = MiniTwitterClient.getMiniTwitterClient();
        miniTwitterService = miniTwitterClient.getMiniTwitterService();
    }

    public void findViews()
    {
        editTextsignusername = findViewById(R.id.id_edit_user_sign);
        editTextsignemail = findViewById(R.id.id_edit_email_sign);
        editTextsignpassword = findViewById(R.id.id_edit_password_sign);

        btnSignUp = findViewById(R.id.id_btn_sign);
        btnCancel = findViewById(R.id.id_btn_sign_cancel);
    }

    public void eventsInit()
    {
        btnSignUp.setOnClickListener(view -> {
            doSignUp();
        });

        btnCancel.setOnClickListener(view -> {
            doLogin();
        });
    }

    public void doSignUp(){
        username = editTextsignusername.getText().toString();
        email = editTextsignemail.getText().toString();
        password = editTextsignpassword.getText().toString();

        if (username.isEmpty()){
            editTextsignusername.setError(getText(R.string.user_error));
        } else if (email.isEmpty()){
            editTextsignemail.setError(getText(R.string.email_error));
        } else if (password.isEmpty()){
            editTextsignpassword.setError(getText(R.string.password_error));
        } else {
            RequestSignUp requestSignUp = new RequestSignUp(username, email, password, Constant.SIGN_CODE);
            Call<ResponseSignUp> responseSignUpCall = miniTwitterService.doSignUp(requestSignUp);

            responseSignUpCall.enqueue(new Callback<ResponseSignUp>() {
                @Override
                public void onResponse(Call<ResponseSignUp> responseSignUpCall, Response<ResponseSignUp> response) {
                    if (response.isSuccessful())
                    {
                        SharedPreferencesManager.setSomeStringValue(Constant.PREFERENCE_TOKEN, response.body().getToken());
                        SharedPreferencesManager.setSomeStringValue(Constant.PREFERENCE_USERNAME, response.body().getUsername());
                        SharedPreferencesManager.setSomeStringValue(Constant.PREFERENCE_EMAIL, response.body().getEmail());
                        SharedPreferencesManager.setSomeStringValue(Constant.PREFERENCE_URL_PHOTO, response.body().getPhotoUrl());
                        SharedPreferencesManager.setSomeStringValue(Constant.PREFERENCE_CREATED, response.body().getCreated());
                        SharedPreferencesManager.setSomeBolleanValue(Constant.PREFERENCE_ACTIVE, response.body().getActive());

                        Toast.makeText(SignUpActivity.this, R.string.signup_ok, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(SignUpActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseSignUp> responseSignUpCall, Throwable t)
                {
                    Toast.makeText(SignUpActivity.this, R.string.network_error, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void doLogin(){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}