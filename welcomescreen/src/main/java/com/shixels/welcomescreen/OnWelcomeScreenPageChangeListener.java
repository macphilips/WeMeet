package com.shixels.welcomescreen;

import android.support.v4.view.ViewPager;

/**
 * Implemented by library components to respond to page scroll events
 * and initial setup
 * <p>
 * Created by stephentuso on 11/16/15.
 */
/* package */ interface OnWelcomeScreenPageChangeListener extends ViewPager.OnPageChangeListener {
    void setup(WelcomeConfiguration config);
}

