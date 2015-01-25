package ru.smirnov.test.instagram.photocollage.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import java.util.List;

import ru.smirnov.test.instagram.photocollage.R;
import ru.smirnov.test.instagram.photocollage.model.Photo;
import ru.smirnov.test.instagram.photocollage.utility.CONST;

/**
 * Created by Alexander on 25.01.2015.
 * Activity which create and show made collage, give ability send collage by email
 */
public class CollageActivity extends BaseActivity implements View.OnClickListener {

    private Bitmap mCollageBitmap =
            Bitmap.createBitmap(CONST.COLLAGE_SIZE, CONST.COLLAGE_SIZE, Bitmap.Config.RGB_565);
    // ARGB_8888
    // RGB_565
    private List<Photo> mPhotoSelected;
    private int mCollagePartWasAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);

        if (getIntent() != null) {
            mPhotoSelected = getIntent().getParcelableArrayListExtra(CONST.TAG_SELECTED_PHOTO);
        }

        if (mPhotoSelected == null || mPhotoSelected.size() != 4) {
            showToast(getString(R.string.error));
            finish();
        } else {
            mAq = new AQuery(this);
            mAq.id(R.id.activity_collage_send_email).clicked(this);

            int size = mPhotoSelected.size();
            for (int i = 0; i < size; i++) {
                loadBitmap(mPhotoSelected.get(i).getStandardResolution(), i);
            }
        }
    }

    private void loadBitmap(String url, int position) {
        mAq.ajax(url, Bitmap.class, getCallback(position));
    }

    private void compositeBitmap(Bitmap object, int part) {
        int left = 0, top = 0;
        object = Bitmap.createScaledBitmap(object, CONST.PHOTO_SIZE, CONST.PHOTO_SIZE, false);
        mCollagePartWasAdded++;

        switch (part) {
            case 0:
                break;
            case 1:
                top = CONST.PHOTO_SIZE;
                break;
            case 2:
                left = CONST.PHOTO_SIZE;
                break;
            case 3:
                top = CONST.PHOTO_SIZE;
                left = CONST.PHOTO_SIZE;
                break;
        }

        Canvas canvas = new Canvas(mCollageBitmap);
        canvas.drawBitmap(object, left, top, null);
        canvas.save();

        if (mCollagePartWasAdded == 4) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAq.id(R.id.activity_collage_image).image(mCollageBitmap);
                }
            });
        }
    }

    private void sendBitmapToEmail() {
        // Store image in Device to send image to mail
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), mCollageBitmap,
                "collage", null);

        Uri collageUri = Uri.parse(path);
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "sandrsmirnov@gmail.com");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Элементарный коллаж - получите Ваш коллаж!");
        emailIntent.putExtra(Intent.EXTRA_STREAM, collageUri);
        emailIntent.setType("image/png");
        startActivity(Intent.createChooser(emailIntent, "Send email using"));
    }

    private AjaxCallback<Bitmap> getCallback(final int position) {
        return new AjaxCallback<Bitmap>() {

            @Override
            public void callback(String url, final Bitmap object, AjaxStatus status) {
                if (object != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            compositeBitmap(object, position);
                        }
                    }).start();
                } else {
                    showToast(getString(R.string.error) + " - " + position);
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_collage_send_email:
                sendBitmapToEmail();
                break;
        }
    }

}
