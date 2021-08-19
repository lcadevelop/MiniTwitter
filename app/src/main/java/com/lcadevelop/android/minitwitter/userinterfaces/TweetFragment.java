package com.lcadevelop.android.minitwitter.userinterfaces;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lcadevelop.android.minitwitter.R;
import com.lcadevelop.android.minitwitter.retrofit.AuthTwitterClient;
import com.lcadevelop.android.minitwitter.retrofit.AuthTwitterService;
import com.lcadevelop.android.minitwitter.retrofit.response.Tweet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TweetFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private List<Tweet>tweetList;

    Context context;
    RecyclerView recyclerView;

    AuthTwitterClient authTwitterClient;
    AuthTwitterService authTwitterService;

    public TweetFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TweetFragment newInstance(int columnCount) {
        TweetFragment fragment = new TweetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweet_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            retrofitInit();
            loadTweetData();
        }
        return view;
    }

    public void retrofitInit(){
        authTwitterClient = AuthTwitterClient.getAuthTwitterClient();
        authTwitterService = authTwitterClient.getAuthTwitterService();
    }

    public void loadTweetData(){
        Call<List<Tweet>> call = authTwitterService.getAllTweets();
        call.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                if (response.isSuccessful()) {
                    tweetList = response.body();
                    recyclerView.setAdapter(new MyTweetRecyclerViewAdapter(getActivity(), tweetList));
                }
                else {
                    Toast.makeText(getActivity(), R.string.listtweet_fail, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_LONG).show();
            }
        });
    }
}