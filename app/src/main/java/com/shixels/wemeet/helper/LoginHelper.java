package com.shixels.wemeet.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.shixels.wemeet.utils.image.Utils;

/**
 * Created by MOROLANI on 2/1/2017
 * <p>
 * owm
 * .
 */

public class LoginHelper {
    public static final int LOGIN_REQUEST = 12;

    private static final String KEY_LOGIN_STARTED = "com.shixels.wemeet.login_started";

    private Activity mActivity;
    private Class<? extends Activity> mActivityClass;
    private boolean doLogin = false;


    public LoginHelper(Activity activity, Class<? extends Activity> activityClass) {
        mActivity = activity;
        mActivityClass = activityClass;
    }

    private boolean getWelcomeScreenStarted(Bundle savedInstanceState) {
        if (!doLogin) {
            doLogin = savedInstanceState != null && savedInstanceState.getBoolean(KEY_LOGIN_STARTED, false);
        }
        return doLogin;
    }

    private boolean shouldShow() {
        // Only use this method if screen orientation changes
        // return !getWelcomeScreenStarted(savedInstanceState) && !LoginSharedPreferencesHelper.hasUserLoggedIn(mActivity, Utils.getKey(mActivityClass));

        return !LoginSharedPreferencesHelper.hasUserLoggedIn(mActivity, Utils.getKey(mActivityClass));
    }


    public boolean show() {
        boolean shouldShow = shouldShow();
        if (shouldShow) {
            //   doLogin = true;
            startActivity(LOGIN_REQUEST);
        }
        return shouldShow;
    }


    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_LOGIN_STARTED, doLogin);
    }

    private void startActivity(int requestCode) {
        Intent intent = new Intent(mActivity, mActivityClass);
        mActivity.startActivityForResult(intent, requestCode);
    }

}
