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

public class Events extends Fragment {
    public static Events newInstance() {

        Bundle args = new Bundle();

        Events fragment = new Events();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.event_layout, container, false);

        return v;
    }
}
