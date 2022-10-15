package bot.app.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Base64;

public class StringSerialization {
    public static Object fromString(String s, Class<?> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(s, clazz);
    }

    public static String toString(Object o) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(o);
    }
}
