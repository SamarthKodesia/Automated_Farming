package com.example.blotcanvas.automated_farming;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class fragmentAbout extends Fragment {
    View view;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_about,container,false);
        //Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
        //imageView.startAnimation(animation);
        return view;
    }

}
