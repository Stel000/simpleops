package com.stelo.simpleops.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

@Slf4j
public class JsonUtils {

    public static JSONObject getJsonObject(String json) {
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            log.error("Transfer data:[{}] to JSONObject error",json,e);
            throw new IllegalArgumentException("Can't transfer content to JSONObject");
        }
    }
}
