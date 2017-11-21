package com.rest.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import java.io.File;
import java.io.IOException;
import static com.rest.utils.Common.log;

public class JacksonUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
    }

    public static String toJson(Object model){
        log("toJson");
        try {
             String json = mapper.writeValueAsString(model);
             log(json);
             return json;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T>T fromJson(String json, Class<T> clazz){
        log("fromJson");
        try {
            T object = mapper.readValue(json, clazz);
            log(object.toString());
            return object;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T>void writeJsonToFile(String path, T model){
        log("writeJsonToFile");
        try {
            mapper.writeValue(new File(path), model);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T>T readJsonFromFile(String path, Class<T> clazz){
        log("readJsonFromFile");
        try {
            T model = mapper.readValue(new File(path), clazz);
            log(model.toString());
            return model;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}