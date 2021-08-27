package com.lcadevelop.android.minitwitter.data;

import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;
import com.lcadevelop.android.minitwitter.R;
import com.lcadevelop.android.minitwitter.common.MyApp;
import com.lcadevelop.android.minitwitter.retrofit.AuthTwitterClient;
import com.lcadevelop.android.minitwitter.retrofit.AuthTwitterService;
import com.lcadevelop.android.minitwitter.retrofit.request.RequestNewTweet;
import com.lcadevelop.android.minitwitter.retrofit.response.ResponseNewTweet;
import com.lcadevelop.android.minitwitter.retrofit.response.Tweet;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TweetRepository {
    AuthTwitterClient authTwitterClient;
    AuthTwitterService authTwitterService;
    MutableLiveData<List<Tweet>> allTweets;

    public TweetRepository(){
        authTwitterClient = AuthTwitterClient.getAuthTwitterClient();
        authTwitterService = authTwitterClient.getAuthTwitterService();

        allTweets = getAllTweets();
    }
    public MutableLiveData<List<Tweet>> getAllTweets(){
        if (allTweets == null){
            allTweets = new MutableLiveData<>();
        }
        Call<List<Tweet>> call = authTwitterService.getAllTweets();
        call.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                if (response.isSuccessful()) {
                    allTweets.setValue(response.body());
                }
                else {
                    Toast.makeText(MyApp.getContext(), R.string.listtweet_fail, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), R.string.network_error, Toast.LENGTH_LONG).show();
            }
        });

        return allTweets;
    }

    public void newTweet(String tweetMessage){
        RequestNewTweet requestNewTweet = new RequestNewTweet(tweetMessage);
        Call<ResponseNewTweet> newTweetCall = authTwitterService.newTweet(requestNewTweet);

        newTweetCall.enqueue(new Callback<ResponseNewTweet>() {
            @Override
            public void onResponse(Call<ResponseNewTweet> call, Response<ResponseNewTweet> response) {
                if (response.isSuccessful()){
                    List<Tweet> listaClonada = new ArrayList<>();
                    Tweet nuevoTweet = new Tweet(response.body().getId(), response.body().getMensaje(), response.body().getLikes(), response.body().getUser());
                    listaClonada.add(nuevoTweet);

                    for (int i =0; i < allTweets.getValue().size(); i++){
                        listaClonada.add(new Tweet(allTweets.getValue().get(i).getId(), allTweets.getValue().get(i).getMensaje(), allTweets.getValue().get(i).getLikes(), allTweets.getValue().get(i).getUser()));
                    }
                    allTweets.setValue(listaClonada);
                }
                else {
                    Toast.makeText(MyApp.getContext(), R.string.newtweet_fail, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseNewTweet> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), R.string.network_error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
