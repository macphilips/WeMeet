package com.shixels.wemeet.helper;

import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;

/**
 * Created by MOROLANI on 2/1/2017
 * <p>
 * owm
 * .
 */

public class NavigationItem {
    private int iconRes;
    private String id;
    private String label;
    private String status;
    private boolean activated;
    private int iconResActivated;
    private Fragment fragment;

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public int getIconRes() {
        return (activated) ? iconResActivated : iconRes;
    }

    public void setIconRes(@DrawableRes int iconRes) {
        this.iconRes = iconRes;
    }

    public void setIconResActivated(@DrawableRes int iconRes) {
        this.iconResActivated = iconRes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
