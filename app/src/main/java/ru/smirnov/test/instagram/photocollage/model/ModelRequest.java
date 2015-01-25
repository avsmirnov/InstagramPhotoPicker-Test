package ru.smirnov.test.instagram.photocollage.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Alexander on 25.01.2015.
 * Application models
 */
public class ModelRequest {
    @JsonProperty("data")
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }
}

class LikeMeta {
    @JsonProperty("count")
    private int count;

    public int getCount() {
        return count;
    }
}

class ImageMeta {
    @JsonProperty("low_resolution")
    private ImageData lowResolution;
    @JsonProperty("standard_resolution")
    private ImageData standardResolution;

    public String getLowResolution() {
        return lowResolution != null ? lowResolution.getUrl() : "";
    }

    public String getStandardResolution() {
        return standardResolution != null ? standardResolution.getUrl() : "";
    }
}

class ImageData {
    @JsonProperty("url")
    private String url;

    public String getUrl() {
        return url;
    }
}