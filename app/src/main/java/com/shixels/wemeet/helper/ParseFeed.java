package com.shixels.wemeet.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MOROLANI on 2/1/2017
 * <p>
 * owm
 * .
 */

public class ParseFeed {
    public static ArrayList<Card> processFeed(String feed) {
        ArrayList<Card> cards = new ArrayList<>();
        try {
            JSONObject jsonFeed = new JSONObject(feed);
            if (jsonFeed.has("feed")) {
                JSONArray jsonData = jsonFeed.getJSONArray("feed");
                for (int i = 0; i < jsonData.length(); i++) {
                    cards.add(getCard(jsonData.getJSONObject(i)));
                }
            }

        } catch (JSONException e) {
            cards = new ArrayList<>();
            e.printStackTrace();
        }
        return cards;
    }

    private static Card getCard(JSONObject cardData) throws JSONException {
        Card card = new Card();
        User user = null;
        if (cardData.has("user")) {
            user = getUserData(cardData.getJSONObject("user"));
            card.setUser(user);
        }
        if (cardData.has("comments")) {
            JSONObject commentData = cardData.getJSONObject("comments");
            card.setCommentValue((commentData.has("no_of_comments")) ? commentData.getInt("no_of_comments") : 0);
            card.setComments(getCommentsData(commentData));
        }
        if (cardData.has("likes")) {
            JSONObject likeData = cardData.getJSONObject("likes");
            card.setLikesValue((likeData.has("no_of_likes")) ? likeData.getInt("no_of_likes") : 0);
            card.setLikes(getLikesData(likeData));
        }
        if (cardData.has("shared")) {
            JSONObject sharedData = cardData.getJSONObject("shared");
            card.setSharedValue((sharedData.has("no_of_shared")) ? sharedData.getInt("no_of_shared") : 0);
            card.setSharedList(getSharedData(sharedData));
        }

        card.setPostImageURL((cardData.has("post_image_url")) ? cardData.getString("post_image_url") : "post1.jpg");
        card.setCardTime((cardData.has("post_time")) ? cardData.getLong("post_time") : 0L);

        card.hasComment((cardData.has("has_commented")) && cardData.getBoolean("has_commented"));
        card.hasLike((cardData.has("has_liked")) && cardData.getBoolean("has_liked"));
        card.hasShared((cardData.has("has_shared")) && cardData.getBoolean("has_shared"));

        return card;
    }

    private static ArrayList<Shared> getSharedData(JSONObject sharedData) throws JSONException {
        ArrayList<Shared> likes = new ArrayList<>();
        JSONArray commentArray = sharedData.getJSONArray("share");
        return likes;
    }

    private static ArrayList<Like> getLikesData(JSONObject likeData) throws JSONException {
        ArrayList<Like> likes = new ArrayList<>();

        JSONArray commentArray = likeData.getJSONArray("like");
        return likes;
    }

    private static ArrayList<Comment> getCommentsData(JSONObject commentData) throws JSONException {
        ArrayList<Comment> comments = new ArrayList<>();
        JSONArray commentArray = commentData.getJSONArray("comment");

        return comments;
    }

    private static User getUserData(JSONObject userData) throws JSONException {
        User user = new User();

        user.setUsername((userData.has("username")) ? userData.getString("username") : "");
        user.setFirstName((userData.has("first_name")) ? userData.getString("first_name") : "");
        user.setLastName((userData.has("last_name")) ? userData.getString("last_name") : "");
        user.setImageURL((userData.has("image_url")) ? userData.getString("image_url") : "");
        user.setStatus((userData.has("status")) ? userData.getString("status") : "");

        return user;
    }
}
