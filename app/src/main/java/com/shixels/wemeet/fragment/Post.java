package com.shixels.wemeet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shixels.wemeet.R;

/**
 * Created by MOROLANI on 2/3/2017
 * <p>
 * owm
 * .
 */

public class Post extends Fragment {
    public static Post newInstance() {

        Bundle args = new Bundle();

        Post fragment = new Post();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.post_layout, container, false);
        return v;
    }
}
