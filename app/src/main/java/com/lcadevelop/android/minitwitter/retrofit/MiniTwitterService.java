package com.lcadevelop.android.minitwitter.retrofit;

import com.lcadevelop.android.minitwitter.retrofit.request.RequestLogin;
import com.lcadevelop.android.minitwitter.retrofit.response.ResponseLogin;
import com.lcadevelop.android.minitwitter.retrofit.response.ResponseSignUp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MiniTwitterService
{
    @POST("auth/login")
    Call<ResponseLogin> doLogin(@Body RequestLogin requestLogin);

    @POST("auth/signup")
    Call<ResponseSignUp> doSignUp(@Body ResponseSignUp responseSignUp);
}
