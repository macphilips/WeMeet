package com.shixels.wemeet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shixels.wemeet.OnNavigationItemClickListener;
import com.shixels.wemeet.R;
import com.shixels.wemeet.helper.NavigationItem;

/**
 * Created by MOROLANI on 11/11/2016
 * <p>
 * owm
 * .
 */

public class NavigationListAdapter extends BaseAdapter {
    private static final String TAG = "NavigationListAdapter";
    private final Context mContext;
    private final NavigationItem[] ITEMS;
    int counter = 0;
    private LayoutInflater mInflater;
    private OnNavigationItemClickListener onNavigationItemClickListener;

    public NavigationListAdapter(Context mContext, NavigationItem[] items) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.ITEMS = items;

    }

    @Override
    public int getCount() {
        return ITEMS.length;
    }

    @Override
    public Object getItem(int i) {
        return ITEMS[i];
    }

    @Override
    public long getItemId(int i) {
        return ITEMS[i].hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.nav_item_layout, viewGroup, false);
            holder.icon = (ImageView) view.findViewById(R.id.nav_item_icon);
            holder.status = (TextView) view.findViewById(R.id.nav_item_status);
            holder.label = (TextView) view.findViewById(R.id.nav_item_label);
            holder.item = ITEMS[i];
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        NavigationItem item = ITEMS[i];
        holder.icon.setImageResource(item.getIconRes());
        holder.label.setText(item.getLabel());
        if (item.isActivated()) {
            holder.label.setTextColor(Color.RED);
        } else {
            holder.label.setTextColor(mContext.getResources().getColor(R.color.text_grey));
        }
        holder.status.setText(item.getStatus());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onNavigationItemClickListener != null) {
                    onNavigationItemClickListener.onNavigationItemClicked(((ViewHolder) view.getTag()).item);
                }
            }
        });
        return view;
    }


    public void setOnNavigationItemClickListener(OnNavigationItemClickListener listener) {
        this.onNavigationItemClickListener = listener;
    }

    private class ViewHolder {
        public NavigationItem item;
        ImageView icon;
        TextView label, status;
    }
}
