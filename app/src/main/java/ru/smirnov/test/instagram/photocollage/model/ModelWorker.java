package ru.smirnov.test.instagram.photocollage.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.smirnov.test.instagram.photocollage.utility.CONST;

/**
 * Created by Alexander on 25.01.2015.
 * Helper class to parse the model.
 */
public class ModelWorker {
    private static final ObjectMapper sObjectMapper = new ObjectMapper();

    public List<Photo> transformDataListToPhotoList(List<Data> pListData) {
        List<Photo> photoList = new ArrayList<>();

        for (Data item : pListData) {
            if (CONST.IMAGE.equals(item.getType())) photoList.add(transformDataToPhoto(item));
        }

        return photoList;
    }

    public Photo transformDataToPhoto(Data pData) {
        return new Photo(
                pData.getLikes(),
                pData.getImages().getLowResolution(),
                pData.getImages().getStandardResolution()
        );
    }

    public ModelRequest parseRequestFromString(String pInput) throws IOException {
        sObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return sObjectMapper.readValue(pInput, ModelRequest.class);
    }

    public User parseUserFromString(String pInput) throws IOException {
        return sObjectMapper.readValue(pInput, User.class);
    }

}
