package api.dongsheng.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    private static final ObjectMapper mapper = newObjectMapper();

    public static ObjectMapper newRawObjectMapper() {
        return new ObjectMapper()
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper newObjectMapper() {
        return newRawObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public static ObjectMapper mapper() {
        return mapper;
    }

    public static String toJson(Object src) throws JsonProcessingException {
        return mapper.writeValueAsString(src);
    }

    public static <T> T fromJson(String content, Class<T> clazz) throws IOException {
        return mapper.readValue(content, clazz);
    }

    public static <T> T fromJson(String content, JavaType clazz) throws IOException {
        return mapper.readValue(content, clazz);
    }

    public static <T> T fromJson(String content, TypeReference<T> clazz) throws IOException {
        return mapper.readValue(content, clazz);
    }

    public static String toJsonQuietly(Object src) {
        try {
            return mapper.writeValueAsString(src);
        } catch (Throwable ignored) {
        }
        return null;
    }

    public static <T> T fromJsonQueitly(String content, Class<T> clazz) {
        try {
            return mapper.readValue(content, clazz);
        } catch (Throwable ignored) {
        }
        return null;
    }

    public static <T> T fromJsonQueitly(String content, JavaType clazz) {
        try {
            return mapper.readValue(content, clazz);
        } catch (Throwable ignored) {
        }
        return null;
    }

    public static <T> T fromJsonQueitly(String content, TypeReference<T> clazz) {
        try {
            return mapper.readValue(content, clazz);
        } catch (Throwable ignored) {
        }
        return null;
    }
}
