package ru.smirnov.test.instagram.photocollage.activity;

import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.androidquery.AQuery;

/**
 * Created by Alexander on 25.01.2015.
 */
public abstract class BaseActivity extends ActionBarActivity {

    protected AQuery mAq;

    protected void showToast(String pMessage) {
        Toast.makeText(this, pMessage, Toast.LENGTH_SHORT).show();
    }

}
