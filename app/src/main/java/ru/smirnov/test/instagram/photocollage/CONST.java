package ru.smirnov.test.instagram.photocollage;

/**
 * Created by Alexander on 25.01.2015.
 * Container for project constance
 */
public class CONST {
    private static final String API_URL = "https://api.instagram.com/v1/";
    public static final String CLIENT_ID = "f304767b178248599b2645d61d0b5722";

    /**
     * Search for a user by name.
     */
    public static final String API_URL_USER_SEARCH_PATTERN =
            API_URL + "users/search?q=%s&client_id=" + CLIENT_ID;

    /**
     * Get the most recent media published by a user.
     * May return a mix of both image and video types.
     */
    public static final String API_URL_RECENT_PATTERN =
            API_URL + "users/%d/media/recent/?client_id=" + CLIENT_ID;



}
