package ru.smirnov.test.instagram.photocollage.model;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Created by Alexander on 25.01.2015.
 */
public class Data {
    @JsonProperty("type")
    String type;
    @JsonProperty("created_time")
    String createdTime;
    @JsonProperty("likes")
    LikeMeta likes;
    @JsonProperty("images")
    ImageMeta images;
    @JsonProperty("id")
    String id;

    public String getType() {
        return type;
    }
    public String getCreatedTime() {
        return createdTime;
    }

    public int getLikes() {
        return likes != null ? likes.getCount() : 0;
    }

    public ImageMeta getImages() {
        return images;
    }

    public String getId() {
        return id;
    }

}