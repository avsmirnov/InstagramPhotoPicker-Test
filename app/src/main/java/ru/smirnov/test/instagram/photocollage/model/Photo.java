package ru.smirnov.test.instagram.photocollage.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alexander on 25.01.2015.
 * Save only what we need
 */
public class Photo implements Parcelable {
    private int likes;
    private String lowResolution;
    private String standardResolution;

    public Photo() {}

    public Photo(int likes, String lowResolution, String standardResolution) {
        setLikes(likes);
        setLowResolution(lowResolution);
        setStandardResolution(standardResolution);
    }


    public Photo(Parcel in) {
        setLikes(in.readInt());
        setLowResolution(in.readString());
        setStandardResolution(in.readString());
    }


    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getLowResolution() {
        return lowResolution;
    }

    public void setLowResolution(String lowResolution) {
        this.lowResolution = lowResolution;
    }

    public String getStandardResolution() {
        return standardResolution;
    }

    public void setStandardResolution(String standardResolution) {
        this.standardResolution = standardResolution;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getLikes());
        dest.writeString(getLowResolution());
        dest.writeString(getStandardResolution());
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

}
