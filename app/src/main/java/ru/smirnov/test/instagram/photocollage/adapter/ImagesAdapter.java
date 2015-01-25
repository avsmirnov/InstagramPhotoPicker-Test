package ru.smirnov.test.instagram.photocollage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.List;

import ru.smirnov.test.instagram.photocollage.R;
import ru.smirnov.test.instagram.photocollage.model.Photo;

/**
 * Created by Alexander on 25.01.2015.
 * Adapter for output images
 * for {@link ru.smirnov.test.instagram.photocollage.activity.MainActivity}
 */
public class ImagesAdapter extends BaseAdapter {

    private List<Photo> mPhotoList;
    private List<Photo> mSelectedPhotoList;

    public ImagesAdapter(List<Photo> photoList, List<Photo> selectedPhotoList) {
        mPhotoList = photoList != null ? photoList : new ArrayList<Photo>();
        mSelectedPhotoList = selectedPhotoList != null ? selectedPhotoList : new ArrayList<Photo>();
    }

    @Override
    public int getCount() {
        return mPhotoList.size();
    }

    @Override
    public Photo getItem(int position) {
        return mPhotoList.size() > position ? mPhotoList.get(position) : new Photo();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_grid_view_item, parent, false);

            holder = new ViewHolder();
            holder.aq = new AQuery(convertView);
            holder.imageView = holder.aq.id(R.id.view_grid_view_item_image).getImageView();
            holder.progressBar = holder.aq.id(R.id.view_grid_view_item_progress).getProgressBar();

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Photo item = getItem(position);
        holder.photoItem = item;
        holder.aq.id(holder.imageView).background(mSelectedPhotoList.contains(item)
                    ? R.color.material_deep_teal_500
                    : android.R.color.transparent)
                .image(item.getLowResolution(), false, true, 0,R.drawable.ic_launcher);

        return convertView;
    }

    class ViewHolder {
        AQuery aq;
        Photo photoItem;
        ImageView imageView;
        ProgressBar progressBar;
    }
}
