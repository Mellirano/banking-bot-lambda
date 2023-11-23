package ua.udunt.lex.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ObjectUtil {

    private static final ObjectMapper MAPPER;
    private static final ObjectMapper WRAP_MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        WRAP_MAPPER = new ObjectMapper();
        WRAP_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        WRAP_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        WRAP_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        WRAP_MAPPER.enable(SerializationFeature.WRAP_ROOT_VALUE);
        WRAP_MAPPER.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
    }

    public static <T> T fromJSON(String json, final Class<T> clazz) {
        if (LibUtil.isNullOrEmpty(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            log.error("", e);
            return null;
        }
    }

    public static String toJSON(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("", e);
            return null;
        }
    }

}
