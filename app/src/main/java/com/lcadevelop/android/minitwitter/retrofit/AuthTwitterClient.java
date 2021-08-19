package com.lcadevelop.android.minitwitter.retrofit;

import com.lcadevelop.android.minitwitter.common.Constant;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthTwitterClient
{
    private static AuthTwitterClient authTwitterClient = null;
    private AuthTwitterService authTwitterService;
    private Retrofit retrofit;

    public AuthTwitterClient()
    {
        OkHttpClient.Builder okhhtpclientbuilder = new OkHttpClient.Builder();
        okhhtpclientbuilder.addInterceptor(new MiniTwitterAuthInterceptor());

        OkHttpClient okHttpClient = okhhtpclientbuilder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        authTwitterService = retrofit.create(AuthTwitterService.class);
    }

    public static AuthTwitterClient getAuthTwitterClient()
    {
        if (authTwitterClient == null)
        {
            authTwitterClient = new AuthTwitterClient();
        }
        return authTwitterClient;
    }

    public AuthTwitterService getAuthTwitterService()
    {
        return authTwitterService;
    }
}
