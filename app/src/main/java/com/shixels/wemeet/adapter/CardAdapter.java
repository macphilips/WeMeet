package com.shixels.wemeet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shixels.wemeet.CircleImageView;
import com.shixels.wemeet.R;
import com.shixels.wemeet.helper.Card;
import com.shixels.wemeet.utils.image.ImageFetcher;
import com.shixels.wemeet.utils.image.ImageWorker;

import java.util.List;


/**
 * Created by MOROLANI on 11/2/2016
 * <p/>
 * owm
 * .
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> implements ImageWorker.OnImageLoadedListener {

    private final Context mContext;
    private ImageFetcher mImageFetcher;
    private List<Card> cardList;

    public CardAdapter(Context mContext, List<Card> cardList) {
        this.mContext = mContext;
        this.cardList = cardList;
    }

    public void setPostFetcher(ImageFetcher mImageFetcher) {
        this.mImageFetcher = mImageFetcher;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Card card = cardList.get(position);
        holder.card_time.setText(String.valueOf(card.getCardTime()));
        holder.card_fav.setText(String.valueOf(card.getLikesValue()));
        holder.card_share.setText(String.valueOf(card.getSharedValue()));
        holder.card_comment.setText(String.valueOf(card.getCommentValue()));
        holder.card_user.setText(String.valueOf(card.getUser().getFullname()));
        holder.card_post_type.setText(String.valueOf(card.getUser().getStatus()));


        if (card.hasLike()) {
            holder.card_fav.setCompoundDrawablesWithIntrinsicBounds((R.drawable.ic_favorite_red700_24dp), 0, 0, 0);
        } else {
            holder.card_fav.setCompoundDrawablesWithIntrinsicBounds((R.drawable.ic_favorite_border_grey_600_24dp), 0, 0, 0);
        }
        if (card.hasShared()) {
            holder.card_share.setCompoundDrawablesWithIntrinsicBounds((R.drawable.ic_menu_share_red), 0, 0, 0);
        } else {
            holder.card_share.setCompoundDrawablesWithIntrinsicBounds((R.drawable.ic_share_grey600_24dp), 0, 0, 0);
        }

        if (mImageFetcher != null) {
            mImageFetcher.loadImage(card.getPostImageURL(), holder.card_post);
        }


        if (mImageFetcher != null) {
            mImageFetcher.loadImage(card.getUser().getImageURL(), holder.card_dp);
        }
        //  holder.card_post.setImageResource(R.drawable.post1);

    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }


    @Override
    public void onImageLoaded(boolean success, int pos) {
        if (pos >= 0) {
            this.notifyItemChanged(pos);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView card_time, card_fav, card_share, card_comment, card_user, card_post_type;
        public ImageView card_post;
        CircleImageView card_dp;

        public MyViewHolder(View view) {
            super(view);
            card_time = (TextView) view.findViewById(R.id.card_time_text);

            card_fav = (TextView) view.findViewById(R.id.card_fav_img);
            card_share = (TextView) view.findViewById(R.id.card_share_img);
            card_comment = (TextView) view.findViewById(R.id.card_comment_img);

            card_user = (TextView) view.findViewById(R.id.card_user);
            card_post_type = (TextView) view.findViewById(R.id.post_type);
            card_post = (ImageView) view.findViewById(R.id.card_post);
            card_dp = (CircleImageView) view.findViewById(R.id.card_dp);

            // overflow = (ImageView) view.findViewById(R.id.overflow);

        }
    }
}
