package ru.smirnov.test.instagram.photocollage.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.smirnov.test.instagram.photocollage.R;
import ru.smirnov.test.instagram.photocollage.adapter.ImagesAdapter;
import ru.smirnov.test.instagram.photocollage.model.ModelRequest;
import ru.smirnov.test.instagram.photocollage.model.ModelWorker;
import ru.smirnov.test.instagram.photocollage.model.Data;
import ru.smirnov.test.instagram.photocollage.model.Photo;
import ru.smirnov.test.instagram.photocollage.model.User;
import ru.smirnov.test.instagram.photocollage.utility.Api;

/**
 * Created by Alexander on 25.01.2015.
 */
public class MainActivity extends ActionBarActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private final static String TAG_USER_DATA = "TAG_USER_DATA";
    private final static String TAG_PHOTO_LIST = "TAG_PHOTO_LIST";

    private User mUser;
    private AQuery mAq;
    private String mMaxId;
    private List<Photo> mPhotoList = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefresh;
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAq = new AQuery(this);
        mAq.id(R.id.activity_main_give_collage).clicked(this);
        mAq.id(R.id.activity_main_cancel_select).clicked(this);
        mAq.id(R.id.activity_main_create_collage).clicked(this);

        mSwipeRefresh = (SwipeRefreshLayout) mAq.id(R.id.activity_main_swipe).getView();
        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        mSwipeRefresh.setOnRefreshListener(this);

        mGridView = mAq.id(R.id.activity_main_grid_view).getGridView();
        mGridView.setOnItemClickListener(this);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(TAG_PHOTO_LIST)) {
                mPhotoList = savedInstanceState.getParcelableArrayList(TAG_PHOTO_LIST);
            }
            if (savedInstanceState.containsKey(TAG_USER_DATA)) {
                mUser = savedInstanceState.getParcelable(TAG_USER_DATA);
            }
        }

        if (mPhotoList != null) showPhotoList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_give_collage:
                mUser = null;
                mSwipeRefresh.setRefreshing(true);

                String name = String.valueOf(mAq.id(R.id.activity_main_user_name).getText());
                Api.findUser(mAq, name, new AjaxCallbackUserSearch());
                // setUser(); // temp
                //loadPhoto();
                break;
            case R.id.activity_main_cancel_select:
                Log.e("TEST", "cancel choice");
                break;
            case R.id.activity_main_create_collage:
                Log.e("TEST", "create_collage");
        }
    }


    private void clearPhotoList() {
        mPhotoList.clear();
        mMaxId = "";
    }

    private void loadPhoto() {
        if (mUser != null) {
            Api.loadPhotos(mAq, mUser.getId(), mMaxId, new AjaxCallbackLoadPhotos());
        }
    }

    private void showPhotoList() {
        Collections.sort(mPhotoList, new Comparator<Photo>() {
            @Override
            public int compare(Photo lhs, Photo rhs) {
                int ll = lhs.getLikes(), rl = rhs.getLikes();
                return ll > rl ? -1 : (ll == rl ? 0 : 1);
            }
        });

        Log.e("TEST"0, "-| size " + mPhotoList.size());
        mGridView.setAdapter(new ImagesAdapter(mPhotoList));
        mSwipeRefresh.setRefreshing(false);
    }

    protected void showToast(String pMessage) {
        Toast.makeText(this, pMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        clearPhotoList();
        loadPhoto();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(TAG_PHOTO_LIST,
                (ArrayList<? extends android.os.Parcelable>) mPhotoList);
        outState.putParcelable(TAG_USER_DATA, mUser);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public class AjaxCallbackLoadPhotos extends  AjaxCallback<JSONObject> {

        @Override
        public void callback(String pUrl, JSONObject pObject, AjaxStatus pStatus) {
            ModelRequest modelRequest = null;
            ModelWorker modelWorker = new ModelWorker();

            if (pObject != null) {
                try {
                    modelRequest = modelWorker.parseRequestFromString(String.valueOf(pObject));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (modelRequest != null) {
                    if (modelRequest.getData() != null && modelRequest.getData().size() > 0) {
                        List<Data> tempItem = modelRequest.getData();
                        mPhotoList.addAll(modelWorker.transformDataListToPhotoList(tempItem));

                        Data lastItem = tempItem.get(tempItem.size() - 1);
                        mMaxId = lastItem.getId();
                        loadPhoto();
                    } else {
                        showPhotoList();
                    }
                } else {
                    showToast(getString(R.string.photo_not_found));
                }
            } else {
                showToast(getString(R.string.photo_not_found));
            }
        }
    }

    public class AjaxCallbackUserSearch extends  AjaxCallback<JSONObject> {

        @Override
        public void callback(String pUrl, JSONObject pObject, AjaxStatus pStatus) {
            if (pObject != null) {
                ModelWorker modelWorker = new ModelWorker();
                try {
                    JSONArray jsonArray = pObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {
                        mUser = modelWorker.parseUserFromString(jsonArray.getString(0));
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

                if (mUser == null) {
                    showToast(getString(R.string.user_not_found));
                } else {
                    showToast(getString(R.string.user_founded_name) + mUser.getUsername());
                    loadPhoto();
                }
            } else {
                showToast(getString(R.string.user_not_found));
            }
        }
    }

}
