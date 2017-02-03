package com.shixels.wemeet.helper;

import java.util.ArrayList;

/**
 * Created by MOROLANI on 1/31/2017
 * <p>
 * owm
 * .
 */
public class Card {
    private long cardTime;
    private int likesValue;
    private int sharedValue;
    private int commentValue;
    private boolean hasLike;
    private boolean hasComment;
    private boolean hasShared;
    private User user;
    private ArrayList<Comment> comments;
    private ArrayList<Shared> sharedList;
    private ArrayList<Like> likes;
    private String postImageURL;

    public boolean hasLike() {
        return hasLike;
    }

    public void hasLike(boolean hasLike) {
        this.hasLike = hasLike;
    }

    public boolean hasComment() {
        return hasComment;
    }

    public void hasComment(boolean hasComment) {
        this.hasComment = hasComment;
    }

    public boolean hasShared() {
        return hasShared;
    }

    public void hasShared(boolean hasShared) {
        this.hasShared = hasShared;
    }

    public long getCardTime() {
        return cardTime;
    }

    void setCardTime(long cardTime) {
        this.cardTime = cardTime;
    }

    public int getLikesValue() {
        return likesValue;
    }

    void setLikesValue(int likesValue) {
        this.likesValue = likesValue;
    }

    public int getSharedValue() {
        return sharedValue;
    }

    void setSharedValue(int sharedValue) {
        this.sharedValue = sharedValue;
    }

    public int getCommentValue() {
        return commentValue;
    }

    void setCommentValue(int commentValue) {
        this.commentValue = commentValue;
    }

    public User getUser() {
        return user;
    }

    void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<Shared> getSharedList() {
        return sharedList;
    }

    void setSharedList(ArrayList<Shared> sharedList) {
        this.sharedList = sharedList;
    }

    public ArrayList<Like> getLikes() {
        return likes;
    }

    void setLikes(ArrayList<Like> likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardTime=" + cardTime +
                ", likesValue=" + likesValue +
                ", sharedValue=" + sharedValue +
                ", commentValue=" + commentValue +
                ", user=" + user +
                ", postImageURL='" + postImageURL + '\'' +
                '}';
    }

    public String getPostImageURL() {
        return postImageURL;
    }

    void setPostImageURL(String postImageURL) {
        this.postImageURL = postImageURL;
    }
}
