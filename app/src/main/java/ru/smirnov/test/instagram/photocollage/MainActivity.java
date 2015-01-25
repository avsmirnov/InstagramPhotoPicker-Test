package ru.smirnov.test.instagram.photocollage;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ru.smirnov.test.instagram.photocollage.model.ModelWorker;
import ru.smirnov.test.instagram.photocollage.model.User;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private AQuery mAq;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAq = new AQuery(this);
        mAq.id(R.id.activity_main_make_collage).clicked(this);
    }

    @Override
    public void onClick(View v) {
        String name = String.valueOf(mAq.id(R.id.activity_main_user_name).getText());
        Api.findUser(mAq, name, new AjaxCallbackUserSearch());
        // loadPhoto();
    }

    private void loadPhoto() {

        Log.e("TEST", "user " + mUser);
    }

    public class AjaxCallbackLoadPhotos extends  AjaxCallback<JSONObject> {

        @Override
        public void callback(String url, JSONObject object, AjaxStatus status) {
            super.callback(url, object, status);
        }
    }

    public class AjaxCallbackUserSearch extends  AjaxCallback<JSONObject> {

        @Override
        public void callback(String pUrl, JSONObject pObject, AjaxStatus pStatus) {

            mUser = null;

            if (pObject != null) {
                ModelWorker modelWorker = new ModelWorker();
                try {
                    JSONArray jsonArray = pObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {
                        mUser = modelWorker.parseUserFromString(jsonArray.getString(0));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (mUser == null) {
                    Toast.makeText(MainActivity.this, R.string.user_not_found, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.user_founded_name)
                            + mUser.getUsername(), Toast.LENGTH_SHORT).show();
                    loadPhoto();
                }
            } else {
                Toast.makeText(MainActivity.this, R.string.user_not_found, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

}
