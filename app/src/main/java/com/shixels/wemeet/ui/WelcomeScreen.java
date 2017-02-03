package com.shixels.wemeet.ui;

import com.shixels.welcomescreen.BasicPage;
import com.shixels.welcomescreen.WelcomeActivity;
import com.shixels.welcomescreen.WelcomeConfiguration;
import com.shixels.wemeet.R;

/**
 * Created by MOROLANI on 2/1/2017
 * <p>
 * owm
 * .
 */
public class WelcomeScreen extends WelcomeActivity {
    public static String welcomeKey() {
        return "WelcomeScreen";
    }

    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultTitleTypefacePath("Montserrat-Bold.ttf")
                .defaultHeaderTypefacePath("Montserrat-Bold.ttf")
                .page(new BasicPage(R.drawable.image,
                        "Welcome",
                        "This is my solution to Shixels Recruitment Exercise")
                        .background(android.R.color.holo_purple)
                )
                .page(new BasicPage(R.drawable.image,
                        "Welcome Page 2",
                        "Thanks for the opportunity")
                        .background(android.R.color.holo_orange_light)
                )
                .swipeToDismiss(true)
                .exitAnimation(android.R.anim.fade_out)
                .build();
    }
}
