package com.lcadevelop.android.minitwitter.ui.home;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.lcadevelop.android.minitwitter.R;
import com.lcadevelop.android.minitwitter.common.Constant;
import com.lcadevelop.android.minitwitter.common.SharedPreferencesManager;
import com.lcadevelop.android.minitwitter.databinding.FragmentOneTweetBinding;
import com.lcadevelop.android.minitwitter.retrofit.response.Like;
import com.lcadevelop.android.minitwitter.retrofit.response.Tweet;
import java.util.List;

public class TweetListRecyclerViewAdapter extends RecyclerView.Adapter<TweetListRecyclerViewAdapter.ViewHolder> {

    private List<Tweet> mValues;
    private Context context;
    private String username;

    public TweetListRecyclerViewAdapter(Context context, List<Tweet> items) {
        mValues = items;
        this.context = context;
        username = SharedPreferencesManager.getSomeStringValue(Constant.PREFERENCE_USERNAME);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentOneTweetBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (mValues != null){
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
    }

    @Override
    public int getItemCount() {
        if (mValues != null){
            return mValues.size();
        }
        return 0;
    }

    public void SetData(List<Tweet>tweetList){
        this.mValues = tweetList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView tweetAvatar;
        public final ImageView tweetLikes;
        public final TextView tweetUsername;
        public final TextView tweetMessage;
        public final TextView tweetNumberLikes;
        public Tweet mItem;

        public ViewHolder(FragmentOneTweetBinding binding) {
            super(binding.getRoot());
            tweetAvatar = binding.idImageTweet;
            tweetLikes = binding.idImageTweetLike;
            tweetUsername = binding.idTextUserTweet;
            tweetMessage = binding.idTextMessageTweet;
            tweetNumberLikes = binding.idTextLikeNumber;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tweetUsername.getText() + "'";
        }
    }
}