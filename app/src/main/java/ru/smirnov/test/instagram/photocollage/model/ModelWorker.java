package ru.smirnov.test.instagram.photocollage.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Alexander on 25.01.2015.
 * Helper class to parse the model.
 */
public class ModelWorker {
    private static final ObjectMapper sObjectMapper = new ObjectMapper();

    public ModelRequest parseRequestFromString(String input) throws IOException {
        return sObjectMapper.readValue(input, ModelRequest.class);
    }

    public User parseUserFromString(String input) throws IOException {
        return sObjectMapper.readValue(input, User.class);
    }

}
