package ru.smirnov.test.instagram.photocollage.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Alexander on 25.01.2015.
 */
public class ModelRequest {
    @JsonProperty("meta")
    Meta meta;
    @JsonProperty("data")
    List<Data> data;
}

class Meta {
    @JsonProperty("code")
    int code;
}

class Data {
    @JsonProperty("attribution")
    String attribution;
    @JsonProperty("tags")
    List<String> tags;
    @JsonProperty("type")
    String type;
    @JsonProperty("location")
    Location location;
    @JsonProperty("comments")
    CommentMeta comments;
    @JsonProperty("filter")
    String filter;
    @JsonProperty("created_time")
    String created_time;
    @JsonProperty("link")
    String link;
    @JsonProperty("likes")
    LikeMeta likes;
    @JsonProperty("images")
    ImageMeta images;
    @JsonProperty("users_in_photo")
    List<TaggedUser> users_in_photo;
    @JsonProperty("caption")
    Caption caption;
    @JsonProperty("user_has_liked")
    boolean user_has_liked;
    @JsonProperty("id")
    String id;
    @JsonProperty("user")
    User user;
}

class Location {
    @JsonProperty("latitude")
    float latitude;
    @JsonProperty("longitude")
    float longitude;
    @JsonProperty("name")
    String name;
    @JsonProperty("id")
    long id;
}


class CommentMeta {
    @JsonProperty("count")
    int count;
    @JsonProperty("data")
    List<Comment> data;
}

class LikeMeta {
    @JsonProperty("count")
    int count;
    @JsonProperty("data")
    List<User> data;
}

class ImageMeta {
    @JsonProperty("low_resolution")
    ImageData low_resolution;
    @JsonProperty("thumbnail")
    ImageData thumbnail;
    @JsonProperty("standard_resolution")
    ImageData standard_resolution;
}

class TaggedUser {
    @JsonProperty("position")
    Position position;
    @JsonProperty("user")
    User user;
}

class Caption {
    @JsonProperty("created_time")
    String created_time;
    @JsonProperty("text")
    String text;
    @JsonProperty("from")
    User from;
    @JsonProperty("id")
    String id;
}

class Comment {
    @JsonProperty("created_time")
    String created_time;
    @JsonProperty("text")
    String text;
    @JsonProperty("from")
    User from;
    @JsonProperty("id")
    String id;
}

class ImageData {
    @JsonProperty("url")
    String url;
    @JsonProperty("width")
    int width;
    @JsonProperty("height")
    int height;
}

class Position {
    @JsonProperty("x")
    float x;
    @JsonProperty("y")
    float y;
}