package com.lcadevelop.android.minitwitter.retrofit;

import com.lcadevelop.android.minitwitter.common.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiniTwitterClient
{
    private static MiniTwitterClient miniTwitterClient = null;
    private MiniTwitterService miniTwitterService;
    private Retrofit retrofit;

    public MiniTwitterClient()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        miniTwitterService = retrofit.create(MiniTwitterService.class);
    }

    public static MiniTwitterClient getMiniTwitterClient()
    {
        if (miniTwitterClient == null)
        {
            miniTwitterClient = new MiniTwitterClient();
        }
        return miniTwitterClient;
    }

    public MiniTwitterService getMiniTwitterService()
    {
        return miniTwitterService;
    }
}
