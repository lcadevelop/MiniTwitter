package com.lcadevelop.android.minitwitter.userinterfaces;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.lcadevelop.android.minitwitter.DashboardActivity;
import com.lcadevelop.android.minitwitter.R;
import com.lcadevelop.android.minitwitter.retrofit.MiniTwitterClient;
import com.lcadevelop.android.minitwitter.retrofit.MiniTwitterService;
import com.lcadevelop.android.minitwitter.retrofit.request.RequestLogin;
import com.lcadevelop.android.minitwitter.retrofit.response.ResponseLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity
{
    EditText edittxtloginemail;
    EditText edittxtloginpassword;

    String email;
    String password;

    Button btnlogin;
    TextView txtregister;

    MiniTwitterClient miniTwitterClient;
    MiniTwitterService miniTwitterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        edittxtloginemail = findViewById(R.id.id_edit_email_login);
        edittxtloginpassword = findViewById(R.id.id_edit_password_login);
        btnlogin = findViewById(R.id.id_btn_login);
        txtregister = findViewById(R.id.id_txt_login_registro);
    }

    public void eventsInit()
    {
        btnlogin.setOnClickListener(view -> doLogin());

        txtregister.setOnClickListener(view -> doRegister());
    }

    public void doLogin()
    {
        email = edittxtloginemail.getText().toString();
        password = edittxtloginpassword.getText().toString();

        if (email.isEmpty())
        {
            edittxtloginemail.setError(getText(R.string.email_error));
        }
        else if (password.isEmpty())
            {
                edittxtloginpassword.setError(getText(R.string.password_error));
            }
            else
                {
                    RequestLogin requestLogin = new RequestLogin(email, password);
                    Call<ResponseLogin> responseLoginCall = miniTwitterService.doLogin(requestLogin);

                    responseLoginCall.enqueue(new Callback<ResponseLogin>() {
                        @Override
                        public void onResponse(Call<ResponseLogin> responseLoginCall, Response<ResponseLogin> response)
                        {
                            if (response.isSuccessful())
                            {
                                Toast.makeText(LoginActivity.this, R.string.login_ok, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, R.string.login_fail, Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseLogin> responseLoginCall, Throwable t)
                        {
                            Toast.makeText(LoginActivity.this, R.string.network_error, Toast.LENGTH_LONG).show();
                        }
                    });
                }
    }

    public void doRegister()
    {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
}