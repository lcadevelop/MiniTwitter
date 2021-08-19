package com.lcadevelop.android.minitwitter.userinterfaces;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.lcadevelop.android.minitwitter.R;
import com.lcadevelop.android.minitwitter.common.Constant;
import com.lcadevelop.android.minitwitter.common.SharedPreferencesManager;
import com.lcadevelop.android.minitwitter.retrofit.response.Like;
import com.lcadevelop.android.minitwitter.retrofit.response.Tweet;
import java.util.List;

public class MyTweetRecyclerViewAdapter extends RecyclerView.Adapter<MyTweetRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private final List<Tweet> mValues;
    private String username;

    public MyTweetRecyclerViewAdapter(Context context, List<Tweet> items) {
        this.context = context;
        mValues = items;
        username = SharedPreferencesManager.getSomeStringValue(Constant.PREFERENCE_USERNAME);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tweet, parent, false);
        return new ViewHolder(view);

        //return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        if (!holder.mItem.getUser().getPhotoUrl().equals("")){
            Glide.with(context)
                    .load("https://www.minitwitter.com/apiv1/uploads/photos/" + holder.mItem.getUser().getPhotoUrl())
                    .into(holder.tweetAvatar);
        }

        for (Like like: holder.mItem.getLikes()){
            if (like.getUsername().equals(username)){
                Glide.with(context)
                        .load(R.drawable.ic_like_pink)
                        .into(holder.tweetLikes);
                holder.tweetNumberLikes.setTextColor(context.getResources().getColor(R.color.color_pink));
                holder.tweetNumberLikes.setTypeface(null, Typeface.BOLD);
                break;
            }
        }

        holder.tweetUsername.setText(holder.mItem.getUser().getUsername());
        holder.tweetMessage.setText(holder.mItem.getMensaje());
        holder.tweetNumberLikes.setText(String.valueOf(holder.mItem.getLikes().size()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView tweetAvatar;
        public final ImageView tweetLikes;
        public final TextView tweetUsername;
        public final TextView tweetMessage;
        public final TextView tweetNumberLikes;
        public Tweet mItem;

        public ViewHolder(View view) {
            super(view);
            tweetAvatar = view.findViewById(R.id.id_image_tweet);
            tweetLikes = view.findViewById(R.id.id_image_tweet_like);
            tweetUsername = view.findViewById(R.id.id_text_user_tweet);
            tweetMessage = view.findViewById(R.id.id_text_message_tweet);
            tweetNumberLikes = view.findViewById(R.id.id_text_like_number);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tweetUsername.getText() + "'";
        }
    }
}