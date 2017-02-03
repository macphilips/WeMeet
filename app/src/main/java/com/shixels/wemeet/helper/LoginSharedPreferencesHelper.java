package com.shixels.wemeet.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MOROLANI on 2/1/2017
 * <p>
 * owm
 * .
 */

public class LoginSharedPreferencesHelper {
    private static final String KEY_SHARED_PREFS = "com.shixels.wemeet.";
    private static final String KEY_HAS_RUN = "login_screen_has_run";

    private static SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences(KEY_SHARED_PREFS, Context.MODE_PRIVATE);
    }

    public static boolean hasUserLoggedIn(Context context, String welcomeScreenKey) {
        return getCompletedFromPreferences(getSharedPrefs(context), welcomeScreenKey);
    }

    public static void storeUserLoggedIn(Context context, String welcomeScreenKey) {
        getSharedPrefs(context).edit().putBoolean(getKey(welcomeScreenKey), true).apply();
    }

    public static void storeUserLoggedOut(Context context, String welcomeScreenKey) {
        getSharedPrefs(context).edit().putBoolean(getKey(welcomeScreenKey), false).apply();
    }

    private static boolean getCompletedFromPreferences(SharedPreferences preferences, String welcomeScreenKey) {
        return preferences.getBoolean(getKey(welcomeScreenKey), false);
    }

    private static String getKey(String welcomeKey) {
        return KEY_HAS_RUN + welcomeKey;
    }
}
