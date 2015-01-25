package ru.smirnov.test.instagram.photocollage.utility;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;

import org.json.JSONObject;


/**
 * Created by Alexander on 25.01.2015.
 * Work with instagram api
 */
public class Api {

    public static void loadPhotos(AQuery pAq, String pUserId, String pMaxId,
                                  AjaxCallback<JSONObject> pAjaxCallback) {
        String url = String.format(CONST.API_URL_RECENT_PATTERN, pUserId);

        if (pMaxId != null && !pMaxId.equals("")) url += "&max_id=" + pMaxId;

        pAq.ajax(url, JSONObject.class, pAjaxCallback);
    }

    public static void findUser(AQuery pAq, String pUserName, AjaxCallback<JSONObject> pAjaxCallback){
        String url = String.format(CONST.API_URL_USER_SEARCH_PATTERN, pUserName);
        pAq.ajax(url, JSONObject.class, pAjaxCallback);
    }

}
