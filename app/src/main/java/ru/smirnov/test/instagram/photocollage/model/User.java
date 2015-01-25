package ru.smirnov.test.instagram.photocollage.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Created by Alexander on 25.01.2015.
 * Usefully class
 */
public class User implements Parcelable {
    @JsonProperty("username")
    private String username;
    @JsonProperty("website")
    private String website;
    @JsonProperty("profile_picture")
    private String profilePicture;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("bio")
    private String bio;
    @JsonProperty("id")
    private String id;

    public User() {
    }

    public User(Parcel in) {
        setId(in.readString());
        setBio(in.readString());
        setWebsite(in.readString());
        setUsername(in.readString());
        setFullName(in.readString());
        setProfilePicture(in.readString());
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getWebsite() {
        return website;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBio() {
        return bio;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format(" id=%s \n username=%s \n full_name=%s",
                getId(), getUsername(), getFullName());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
        dest.writeString(getBio());
        setWebsite(getWebsite());
        setUsername(getUsername());
        setFullName(getFullName());
        setProfilePicture(getProfilePicture());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

}