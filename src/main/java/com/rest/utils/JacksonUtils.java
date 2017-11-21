package com.rest.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import java.io.IOException;

public class JacksonUtils {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
    }

    public static String toJson(Object model){
        String json = null;

        try {
             json = mapper.writeValueAsString(model);
             log(json);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }


    public static void log(Object o){
        System.out.println(o + "");
    }

}