package ru.smirnov.test.instagram.photocollage.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

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
import ru.smirnov.test.instagram.photocollage.model.Data;
import ru.smirnov.test.instagram.photocollage.model.ModelRequest;
import ru.smirnov.test.instagram.photocollage.model.ModelWorker;
import ru.smirnov.test.instagram.photocollage.model.Photo;
import ru.smirnov.test.instagram.photocollage.model.User;
import ru.smirnov.test.instagram.photocollage.utility.Api;
import ru.smirnov.test.instagram.photocollage.utility.CONST;


/**
 * Created by Alexander on 25.01.2015.
 * Load user, load photo, photo picker
 */
public class MainActivity extends BaseActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private final static String TAG_USER_DATA = "TAG_USER_DATA";
    private final static String TAG_PHOTO_LIST = "TAG_PHOTO_LIST";

    private User mUser;

    private String mMaxId;
    private GridView mGridView;
    private ImagesAdapter mImagesAdapter;
    private SwipeRefreshLayout mSwipeRefresh;
    private List<Photo> mPhotoList = new ArrayList<>();
    private List<Photo> mPhotoSelectedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (toolbar != null) toolbar.setNavigationIcon(R.drawable.ic_launcher);

        aq.id(R.id.activity_main_give_collage).clicked(this);
        aq.id(R.id.activity_main_cancel_select).clicked(this);
        aq.id(R.id.activity_main_create_collage).clicked(this);

        aq.id(R.id.activity_main_user_name).getEditText()
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            aq.id(R.id.activity_main_give_collage).click();
                            return true;
                        }
                        return false;
                    }
                });

        mSwipeRefresh = (SwipeRefreshLayout) aq.id(R.id.activity_main_swipe).getView();
        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        mSwipeRefresh.setOnRefreshListener(this);

        mGridView = aq.id(R.id.activity_main_grid_view).getGridView();
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
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_give_collage:
                if (!freezeUI) {
                    closeKeyboard(aq.id(R.id.activity_main_user_name).getView());
                    freezeUI = true;
                    mUser = null;
                    mSwipeRefresh.setRefreshing(true);
                    clearPhotoList();

                    String name = String.valueOf(aq.id(R.id.activity_main_user_name).getText());
                    Api.findUser(aq, name, new AjaxCallbackUserSearch());
                }
                break;
            case R.id.activity_main_cancel_select:
                if (!freezeUI) {
                    mPhotoSelectedList.clear();
                    mImagesAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.activity_main_create_collage:
                if (!freezeUI) {
                    final int count = CONST.IMAGES_FOR_COLLAGE;
                    if (mPhotoSelectedList.size() != count) {
                        showToast(String.format(getString(R.string.select_images_need_count), count));
                    } else if (mPhotoSelectedList.size() == count) {
                        freezeUI = true;

                        Intent intent = new Intent(this, CollageActivity.class);
                        intent.putParcelableArrayListExtra(CONST.TAG_SELECTED_PHOTO,
                                (ArrayList<? extends android.os.Parcelable>) mPhotoSelectedList);
                        startActivity(intent);
                    }
                }
                break;
        }
    }

    private void clearPhotoList() {
        mPhotoSelectedList.clear();
        mPhotoList.clear();
        mImagesAdapter.notifyDataSetChanged();
        mMaxId = "";
    }

    private void loadPhoto() {
        if (mUser != null) {
            Api.loadPhotos(aq, mUser.getId(), mMaxId, new AjaxCallbackLoadPhotos());
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

        mImagesAdapter = new ImagesAdapter(mPhotoList, mPhotoSelectedList);
        mGridView.setAdapter(mImagesAdapter);
        mSwipeRefresh.setRefreshing(false);
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
        Photo item = mPhotoList.get(position);

        if (!mPhotoSelectedList.contains(item)) {
            final int count = CONST.IMAGES_FOR_COLLAGE;
            if (mPhotoSelectedList.size() < count) mPhotoSelectedList.add(item);
            else showToast(String.format(getString(R.string.select_images_need_count), count));
        } else {
            mPhotoSelectedList.remove(item);
        }

        mImagesAdapter.notifyDataSetChanged();
    }

    public class AjaxCallbackLoadPhotos extends AjaxCallback<JSONObject> {

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
                    mSwipeRefresh.setRefreshing(false);
                }
            } else {
                showToast(getString(R.string.photo_not_found));
                mSwipeRefresh.setRefreshing(false);
            }

            freezeUI = false;
        }
    }

    public class AjaxCallbackUserSearch extends AjaxCallback<JSONObject> {

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
                    mSwipeRefresh.setRefreshing(false);
                } else {
                    showToast(getString(R.string.user_founded_name) + mUser.getUsername());
                    loadPhoto();
                }
            } else {
                showToast(getString(R.string.user_not_found));
                mSwipeRefresh.setRefreshing(false);
            }

            freezeUI = false;
        }
    }

}
