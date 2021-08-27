package com.lcadevelop.android.minitwitter.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lcadevelop.android.minitwitter.R;
import com.lcadevelop.android.minitwitter.common.Constant;
import com.lcadevelop.android.minitwitter.common.SharedPreferencesManager;

import java.util.Objects;

public class NewTweetDialogFragment extends DialogFragment {

    private ImageView imageCloseNewTweet;
    private ImageView imageUserNewTweet;
    private Button buttonNewTweet;
    private EditText editTextNewTweet;

    public NewTweetDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_tweet_dialog, container, false);

        imageCloseNewTweet = view.findViewById(R.id.imageViewClose);
        imageUserNewTweet = view.findViewById(R.id.imageViewAvatar);
        buttonNewTweet = view.findViewById(R.id.buttonTwittear);
        editTextNewTweet = view.findViewById(R.id.editTextMensaje);

        String photoURL = SharedPreferencesManager.getSomeStringValue(Constant.PREFERENCE_URL_PHOTO);
        if (!photoURL.isEmpty()){
            Glide.with(requireActivity()).load(Constant.FILES_URL + photoURL).into(imageUserNewTweet);
        }

        imageCloseNewTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextNewTweet.getText().toString().equals("")){
                    showDialogCancel();
                }
                else {
                    Objects.requireNonNull(getDialog()).dismiss();
                }
            }
        });

        buttonNewTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextNewTweet.getText() != null){
                    Toast.makeText(getActivity(), R.string.nuevo_tweet_empty, Toast.LENGTH_SHORT).show();
                }
                else {
                    TweetListViewModel tweetListViewModel = new ViewModelProvider(requireActivity()).get(TweetListViewModel.class);
                    tweetListViewModel.newTweet(editTextNewTweet.getText().toString());
                    Objects.requireNonNull(getDialog()).dismiss();
                }
            }
        });

        return view;
    }

    private void showDialogCancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_nuevo_tweet).setTitle(R.string.delete_nuevo_tweet_title);

        builder.setPositiveButton(R.string.nuevo_tweet_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });

        builder.setNegativeButton(R.string.nuevo_tweet_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}