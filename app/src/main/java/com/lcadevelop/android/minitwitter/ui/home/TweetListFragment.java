package com.lcadevelop.android.minitwitter.ui.home;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lcadevelop.android.minitwitter.R;
import com.lcadevelop.android.minitwitter.retrofit.response.Tweet;
import java.util.List;


public class TweetListFragment extends Fragment {

    private int mColumnCount = 1;

    private List<Tweet> tweetList;
    private TweetListViewModel tweetViewModel;

    public TweetListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tweetViewModel = new ViewModelProvider(requireActivity()).get(TweetListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweetlist, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            TweetListRecyclerViewAdapter adapter = new TweetListRecyclerViewAdapter(getActivity(), tweetList);
            recyclerView.setAdapter(adapter);
            loadTweetData();
        }
        return view;
    }

    public void loadTweetData()
    {
        tweetViewModel.getListTweets().observe(requireActivity(), new Observer<List<Tweet>>() {
            @Override
            public void onChanged(List<Tweet> tweets) {
                tweetList = tweets;
            }
        });
    }
}