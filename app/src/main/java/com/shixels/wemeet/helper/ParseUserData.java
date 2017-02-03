package com.shixels.wemeet.helper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MOROLANI on 2/1/2017
 * <p>
 * owm
 * .
 */

public class ParseUserData {
    public static User getUser(String userData) {
        try {
            JSONObject data = new JSONObject(userData);
            return (data.has("profile") ? getUserData(data.getJSONObject("profile")) : new User());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new User();
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
