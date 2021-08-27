package com.lcadevelop.android.minitwitter.retrofit;

import com.lcadevelop.android.minitwitter.retrofit.request.RequestNewTweet;
import com.lcadevelop.android.minitwitter.retrofit.response.ResponseNewTweet;
import com.lcadevelop.android.minitwitter.retrofit.response.Tweet;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthTwitterService
{
    @GET("tweets/all")
    Call<List<Tweet>> getAllTweets();

    @POST("tweets/create")
    Call<ResponseNewTweet> newTweet(@Body RequestNewTweet requestNewTweet);
}
