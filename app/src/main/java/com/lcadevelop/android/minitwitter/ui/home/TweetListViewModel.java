package com.lcadevelop.android.minitwitter.ui.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lcadevelop.android.minitwitter.data.TweetRepository;
import com.lcadevelop.android.minitwitter.retrofit.response.Tweet;

import java.util.List;

public class TweetListViewModel extends AndroidViewModel {
    private TweetRepository tweetRepository;
    private LiveData<List<Tweet>> listTweets;

    public TweetListViewModel(@NonNull Application application) {
        super(application);

        tweetRepository = new TweetRepository();
        listTweets = tweetRepository.getAllTweets();
    }

    public LiveData<List<Tweet>> getListTweets(){
        return listTweets;
    }
}
