package ru.smirnov.test.instagram.photocollage.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Alexander on 25.01.2015.
 */
public class User {
    @JsonProperty("username")
    String username;
    @JsonProperty("website")
    String website;
    @JsonProperty("profile_picture")
    String profile_picture;
    @JsonProperty("full_name")
    String full_name;
    @JsonProperty("bio")
    String bio;
    @JsonProperty("id")
    String id;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
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

    public String getProfile_picture() {
        return profile_picture;
    }

    public String getFullName() {
        return full_name;
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

}