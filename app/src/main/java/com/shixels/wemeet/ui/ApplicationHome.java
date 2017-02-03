package com.shixels.wemeet.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shixels.welcomescreen.WelcomeHelper;
import com.shixels.wemeet.OnNavigationItemClickListener;
import com.shixels.wemeet.R;
import com.shixels.wemeet.adapter.CardAdapter;
import com.shixels.wemeet.adapter.NavigationListAdapter;
import com.shixels.wemeet.fragment.Account;
import com.shixels.wemeet.fragment.Events;
import com.shixels.wemeet.fragment.Feed;
import com.shixels.wemeet.fragment.Notification;
import com.shixels.wemeet.fragment.Post;
import com.shixels.wemeet.helper.LoginHelper;
import com.shixels.wemeet.helper.LoginSharedPreferencesHelper;
import com.shixels.wemeet.helper.NavigationItem;
import com.shixels.wemeet.helper.ParseUserData;
import com.shixels.wemeet.helper.User;
import com.shixels.wemeet.utils.image.ImageCache;
import com.shixels.wemeet.utils.image.ImageFetcher;
import com.shixels.wemeet.utils.image.Utils;

import java.io.BufferedInputStream;
import java.io.IOException;

public class ApplicationHome extends AppCompatActivity
        implements OnNavigationItemClickListener {
    private static final int REQUEST_WELCOME_SCREEN_RESULT = 13;
    private static final String IMAGE_CACHE_DIR = "thumbs";
    private static final String TAG = "ApplicationHome";
    private static NavigationItem[] NAVIGATION_ITEM;

    static {

        /*POSTS = new int[]{R.drawable.post1, R.drawable.post2,
                R.drawable.post3, R.drawable.post4, R.drawable.post4,
                R.drawable.post5, R.drawable.post6, R.drawable.post7,
                R.drawable.post8, R.drawable.post9, R.drawable.post10}; */
        NavigationItem item1 = new NavigationItem();
        item1.setIconRes(R.drawable.ic_menu_camera);
        item1.setIconResActivated(R.drawable.ic_menu_camera_red);
        item1.setId("feed");
        item1.setLabel("Feed");
        item1.setStatus("");
        item1.setActivated(true);


        NavigationItem item2 = new NavigationItem();
        item2.setIconRes(R.drawable.ic_menu_gallery);
        item2.setIconResActivated(R.drawable.ic_menu_gallery_red);
        item2.setId("events");
        item2.setLabel("Events");
        item2.setStatus("");
        item2.setFragment(Events.newInstance());

        NavigationItem item3 = new NavigationItem();
        item3.setIconRes(R.drawable.ic_instagram_grey600_24dp);
        item3.setIconResActivated(R.drawable.ic_instagram_grey600_24dp);
        item3.setId("post");
        item3.setLabel("Post");
        item3.setStatus("");
        item3.setFragment(Post.newInstance());

        NavigationItem item4 = new NavigationItem();
        item4.setIconRes(R.drawable.ic_menu_send);
        item4.setIconResActivated(R.drawable.ic_menu_send_red);
        item4.setId("notifications");
        item4.setLabel("Notifications");
        item4.setStatus("3");
        item4.setFragment(Notification.newInstance());

        NavigationItem item5 = new NavigationItem();
        item5.setIconRes(R.drawable.ic_menu_share);
        item5.setIconResActivated(R.drawable.ic_menu_share_red);
        item5.setId("account");
        item5.setLabel("Account");
        item5.setStatus("");
        item5.setFragment(Account.newInstance());

        NavigationItem item6 = new NavigationItem();
        item6.setIconRes(R.drawable.ic_menu_manage);
        item6.setIconResActivated(R.drawable.ic_menu_manage_red);
        item6.setId("logout");
        item6.setLabel("Logout");
        item6.setStatus("");
        NAVIGATION_ITEM = new NavigationItem[]{item1, item2, item3, item4, item5, item6};
    }

    ImageCache.ImageCacheParams cacheParams = null;
    int mImageThumbSize;
    private WelcomeHelper sampleWelcomeScreen;
    private LoginHelper helper;
    private CardAdapter mAdapter;
    private ImageFetcher mPostFetcher;
    private NavigationListAdapter mNavAdapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_WELCOME_SCREEN_RESULT) {
            if (resultCode == RESULT_OK) {
                //   helper = new LoginHelper(this, Login.class);
                helper.show();
            } else {
                finish();
            }
        } else if (requestCode == LoginHelper.LOGIN_REQUEST) {
            if (resultCode == RESULT_OK) {
                setupFeed();
            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        sampleWelcomeScreen = new WelcomeHelper(this, WelcomeScreen.class);
        helper = new LoginHelper(this, Login.class);
        if (!sampleWelcomeScreen.show(savedInstanceState, REQUEST_WELCOME_SCREEN_RESULT)) {
            helper.show();
        }

        cacheParams =
                new ImageCache.ImageCacheParams(this, IMAGE_CACHE_DIR);
        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory
        // The welcome screen for this app (only one that automatically shows)

        if (LoginSharedPreferencesHelper.hasUserLoggedIn(this, Utils.getKey(Login.class))) {
            setupFeed();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Feed");

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ListView ls = (ListView) navigationView.findViewById(R.id.nav_item_list);
        mNavAdapter = new NavigationListAdapter(this, NAVIGATION_ITEM);
        mNavAdapter.setOnNavigationItemClickListener(this);
        ls.setAdapter(mNavAdapter);
    }

    private void setupFeed() {
        initToolbar();
        mPostFetcher = new ImageFetcher(this, mImageThumbSize);
        mPostFetcher.setLoadingImage(R.drawable.post_background);
        mPostFetcher.addImageCache(this.getSupportFragmentManager(), cacheParams);
        new LoadUser().execute();
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment mFragment = Feed.newInstance();
        NAVIGATION_ITEM[0].setFragment(mFragment);
        ft.add(R.id.container, mFragment);
        ft.commit();

    }

    private void addParentToStack(NavigationItem item) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction temp = fm.beginTransaction();

        temp.setCustomAnimations(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_right_exit);

        temp.replace(R.id.container, item.getFragment());
        //  temp.addToBackStack(null);
        temp.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationItemClicked(NavigationItem item) {
        Log.d(TAG, "OnNavigationItemClick");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (item.getId().equalsIgnoreCase("logout")) {

            showDialog(1);
            new LogUserOut().execute();
            return;
        }
        for (NavigationItem i : NAVIGATION_ITEM) {
            i.setActivated(false);
        }
        item.setActivated(true);
        addParentToStack(item);
        mNavAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        sampleWelcomeScreen.onSaveInstanceState(outState);
        // helper.onSaveInstanceState(outState);
    }

    private void setupNavigationInfo(User result) {
        NavigationView v = (NavigationView) findViewById(R.id.nav_view);
        ((TextView) v.findViewById(R.id.nav_user_name)).setText(result.getFullname());
        ((TextView) v.findViewById(R.id.nav_user_status)).setText(result.getStatus());
        ImageView viewById = (ImageView) v.findViewById(R.id.nav_user_dp);
        mPostFetcher.loadImage(result.getImageURL(), viewById);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        ProgressDialog dialog = new ProgressDialog(this);
        if (id == 0) {
            dialog.setMessage("loading...");
        } else if (id == 1) {
            dialog.setMessage("Logging Out");
        }
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setIcon(R.mipmap.ic_launcher);
        return dialog;
    }

    private void launchLoginActivity() {
        LoginSharedPreferencesHelper.storeUserLoggedOut(this, Utils.getKey(Login.class));
        dismissDialog(1);
        LoginHelper helper = new LoginHelper(this, Login.class);
        helper.show();
    }

    public class LoadUser extends android.os.AsyncTask<Void, Void, User> {
        @Override
        protected User doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            BufferedInputStream in = null;
            try {
                AssetManager manager = getBaseContext().getAssets();
                in = new BufferedInputStream(manager.open("User.json"));
                StringBuilder builder = new StringBuilder();
                byte[] bytes = new byte[1024];
                int b;
                while ((b = in.read(bytes)) != -1) {
                    builder.append(new String(bytes));
                }
                in.close();
                return ParseUserData.getUser(builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            // TODO: register the new account here.
            return null;
        }

        @Override
        protected void onPostExecute(final User result) {


            if (result != null) {
                // startActivity(new Intent(Login.this, ApplicationHome.class));
                setupNavigationInfo(result);
            }
        }


    }

    public class LogUserOut extends android.os.AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean result) {

            if (result) {
                launchLoginActivity();
            }
        }


    }
}
