package com.roacg.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.*;

/**
 * Create by skypyb on 2019-12-13
 */
public final class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        //序列化的时候序列对象的所有非空属性
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //反序列化的时候如果多了其他属性,不抛出异常
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //如果是空对象的时候,不抛异常
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        //不使用默认的dateTime进行序列化,
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        //使用JSR310提供的序列化类,里面包含了大量的JDK8时间序列化类
        OBJECT_MAPPER.registerModule(new JavaTimeModule());

        //语言环境
        OBJECT_MAPPER.setLocale(Locale.CHINA);
    }

    private JsonUtil() {
        throw new UnsupportedOperationException();
    }


    public static ObjectNode createObjectNode() {
        return OBJECT_MAPPER.createObjectNode();
    }

    public static ArrayNode createArrayNode() {
        return OBJECT_MAPPER.createArrayNode();
    }

    public static Optional<String> toJson(Object obj) {

        Optional<String> result;

        try {
            result = Optional.ofNullable(objectConvertToJsonString(obj));
        } catch (Exception e) {
            result = Optional.empty();
        }
        return result;
    }

    private static String objectConvertToJsonString(Object obj) {

        if (Objects.nonNull(obj) &&
                (obj instanceof ObjectNode || obj instanceof ArrayNode)) return obj.toString();

        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Optional<T> fromJsonToObject(String json, Class<T> clazz) {

        Optional<T> result;

        try {
            result = Optional.of(OBJECT_MAPPER.readValue(json, clazz));
        } catch (IOException e) {
            result = Optional.empty();
        }

        return result;
    }

    public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {

        List<T> result;

        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
            result = OBJECT_MAPPER.readValue(json, javaType);

        } catch (IOException e) {
            result = new ArrayList<>();
        }

        return result;
    }


}
