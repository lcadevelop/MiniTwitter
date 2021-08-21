package com.lcadevelop.android.minitwitter.data;

import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.lcadevelop.android.minitwitter.R;
import com.lcadevelop.android.minitwitter.common.MyApp;
import com.lcadevelop.android.minitwitter.retrofit.AuthTwitterClient;
import com.lcadevelop.android.minitwitter.retrofit.AuthTwitterService;
import com.lcadevelop.android.minitwitter.retrofit.response.Tweet;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TweetRepository {
    AuthTwitterClient authTwitterClient;
    AuthTwitterService authTwitterService;
    LiveData<List<Tweet>> allTweets;

    public TweetRepository(){
        authTwitterClient = AuthTwitterClient.getAuthTwitterClient();
        authTwitterService = authTwitterClient.getAuthTwitterService();

        allTweets = getAllTweets();
    }
    public LiveData<List<Tweet>> getAllTweets(){
        final MutableLiveData<List<Tweet>> data = new MutableLiveData<>();

        Call<List<Tweet>> call = authTwitterService.getAllTweets();
        call.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
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

        return data;
    }

}
