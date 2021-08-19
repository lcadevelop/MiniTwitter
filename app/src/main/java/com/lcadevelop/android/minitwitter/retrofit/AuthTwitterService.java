package com.lcadevelop.android.minitwitter.retrofit;

import com.lcadevelop.android.minitwitter.retrofit.response.Tweet;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AuthTwitterService
{
    @GET("tweets/all")
    Call<List<Tweet>> getAllTweets();
}
