package ru.smirnov.test.instagram.photocollage;

import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;

import org.json.JSONObject;

/**
 * Created by Alexander on 25.01.2015.
 * Work with instagram api
 */
public class Api {

    public static void findUser(AQuery pAq, String pUserName, AjaxCallback pAjaxCallback){
        Log.e("TEST", "-| API:findUser");

        String url = String.format(CONST.API_URL_USER_SEARCH_PATTERN, pUserName);
        pAq.ajax(url, JSONObject.class, pAjaxCallback);
    }

}
