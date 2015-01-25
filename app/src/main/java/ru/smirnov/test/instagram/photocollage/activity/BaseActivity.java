package ru.smirnov.test.instagram.photocollage.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.androidquery.AQuery;

import ru.smirnov.test.instagram.photocollage.R;

/**
 * Created by Alexander on 25.01.2015.
 */
public abstract class BaseActivity extends ActionBarActivity {

    protected AQuery aq;
    protected Toolbar toolbar;
    protected boolean freezeUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        aq = new AQuery(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        freezeUI = false;
    }

    protected void closeKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

    }

    protected abstract int getLayout();

    protected void showToast(String pMessage) {
        Toast.makeText(this, pMessage, Toast.LENGTH_SHORT).show();
    }

}
