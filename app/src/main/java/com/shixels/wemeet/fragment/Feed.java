package com.shixels.wemeet.fragment;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shixels.wemeet.R;
import com.shixels.wemeet.adapter.CardAdapter;
import com.shixels.wemeet.helper.Card;
import com.shixels.wemeet.helper.ParseFeed;
import com.shixels.wemeet.utils.image.AsyncTask;
import com.shixels.wemeet.utils.image.ImageCache;
import com.shixels.wemeet.utils.image.ImageFetcher;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by MOROLANI on 2/3/2017
 * <p>
 * owm
 * .
 */

public class Feed extends Fragment {
    private static final String IMAGE_CACHE_DIR = "thumbs";
    private static final String TAG = "Feed";
    ArrayList<Card> cardList = null;
    ImageCache.ImageCacheParams cacheParams = null;
    int mImageThumbSize;
    private CardAdapter mAdapter;
    private ImageFetcher mPostFetcher;

    @SuppressLint("ValidFragment")
    private Feed() {
    }

    public static Feed newInstance() {
        Bundle args = new Bundle();
        Feed fragment = new Feed();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cacheParams =
                new ImageCache.ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);
        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory
        // The welcome screen for this app (only one that automatically shows)
    }

    private void init(View v) {
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mPostFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
        mPostFetcher.setLoadingImage(R.drawable.post_background);
        mPostFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);

        cardList = new ArrayList<>();
        mAdapter = new CardAdapter(getActivity(), cardList);
        mAdapter.setPostFetcher(mPostFetcher);

        new BackgroundTask().execute();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.feed_layout, container, false);
        init(v);
        return v;

    }

    private class BackgroundTask extends AsyncTask<Void, Void, ArrayList<Card>> {
        @Override
        protected void onPostExecute(ArrayList<Card> cards) {
            super.onPostExecute(cards);
            if (cards != null) {
                mAdapter.setCardList(cards);
            }
        }

        @Override
        protected ArrayList<Card> doInBackground(Void... params) {
            AssetManager manager = getContext().getAssets();
            try {

                BufferedInputStream inputStream;//
                inputStream = new BufferedInputStream(manager.open("Feed.json"));
                StringBuilder builder = new StringBuilder();
                byte[] bytes = new byte[1024];
                int c = 0;
                while ((c = inputStream.read(bytes)) != -1) {
                    builder.append(new String(bytes));
                }
                String s = builder.toString();
                ArrayList<Card> cards = ParseFeed.processFeed(s);
                int i = 0;
                for (Card cc : cards) {
                    Log.d(TAG, String.valueOf(cc));
                }
                return cards;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new ArrayList<>();
        }
    }
}
