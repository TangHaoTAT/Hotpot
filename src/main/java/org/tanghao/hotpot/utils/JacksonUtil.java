package org.tanghao.hotpot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Jackson工具类
 *
 * @author TangHao
 * @date 2022-09-18 21:00:28
 */
public class JacksonUtil {

    /**
     * 对象映射器
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将对象转换成JSON字符串
     *
     * @param object 对象
     * @return JSON字符串
     */
    public static String convertObjectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON字符串转换成对象
     *
     * @param json JSON字符串
     * @param objectType 对象类型
     * @return 对象
     */
    public static <T> T convertJsonToObject(String json, Class<T> objectType) {
        try {
            return objectMapper.readValue(json, objectType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON字符串转换成List列表
     *
     * @param json JSON字符串
     * @param objectType 对象类型
     * @return 列表
     */
    public static <T> List<T> convertJsonToList(String json, Class<T> objectType) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, objectType);
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON字符串转换成Set集合
     *
     * @param json JSON字符串
     * @param elementType 元素类型
     * @return Set集合
     */
    public static <E> Set<E> convertJsonToSet(String json, Class<E> elementType) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(Set.class, elementType);
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON字符串转换成Map集合
     *
     * @param json JSON字符串
     * @param keyType 键类型
     * @param valueType 值类型
     * @return Map集合
     */
    public static <K, V> Map<K, V> convertJsonToMap(String json, Class<K> keyType, Class<V> valueType) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructMapType(Map.class, keyType, valueType);
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过输入流获取对象
     *
     * @param inputStream 输入流
     * @param objectType 对象类型
     * @return 对象
     */
    public static <T> T getObjectByInputStream(InputStream inputStream, Class<T> objectType) {
        try {
            return objectMapper.readValue(inputStream, objectType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
