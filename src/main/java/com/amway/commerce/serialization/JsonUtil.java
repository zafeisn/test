package com.amway.commerce.serialization;

import com.amway.commerce.string.StringUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

/**
 * @author: Jason.Hu
 * @date: 2023-08-09
 * @desc: Json工具类  jackson - v2.13.3  jackson-datatype-jsr310 - v2.13.3
 */
@Slf4j
public class JsonUtil {

    /**
     * objectMapper
     */
    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    /**
     * Date类型时间格式
     */
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    static {
        // 对象的所有字段全部列入，还是其他的选项，可以忽略null等
        OBJECTMAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 设置 Date类型的序列化及反序列化格式
        OBJECTMAPPER.setDateFormat(new SimpleDateFormat(DATE_PATTERN));
        // 忽略空 Bean转 json的错误
        OBJECTMAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 忽略未知属性，防止 json字符串中存在，java对象中不存在对应属性的情况出现错误
        OBJECTMAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 注册一个时间序列化及反序列化的处理模块，用于解决jdk8中localDateTime等的序列化问题
        OBJECTMAPPER.registerModule(new JavaTimeModule());
    }

    /**
     * Object转换为 Json
     *
     * @param obj 源对象
     * @return 转换后的 Json字符串
     */
    public static String toJson(Object obj) {
        String json = null;
        if (obj != null) {
            try {
                json = OBJECTMAPPER.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                log.warn(e.getMessage(), e);
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return json;
    }

    /**
     * json字符串转换为 Object
     *
     * @param jsonStr 源 json字符串
     * @param clazz   对象类
     * @param <T>     泛型
     * @return 转换后的对象
     */
    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        return parseObject(jsonStr, clazz, null);
    }

    /**
     * json字符串转换为 Object
     *
     * @param jsonStr 源 json字符串
     * @param type    对象类型
     * @param <T>     泛型
     * @return 转换后的对象
     */
    public static <T> T parseObject(String jsonStr, TypeReference type) {
        return parseObject(jsonStr, null, type);
    }

    /**
     * json字符串转换为 Object
     * <p>
     * JsonUtil和 GsonUtil的联系和区别：
     * 1、Gson需要自己去实现 LocalDateTime、LocalDate、LocalTime、OffsetDateTime序列化和反序列化方法，
     * 具体为继承抽象类 TypeAdapter，实现 write和 read方法；而 jackson可以利用 jackson-datatype-jsr310库去实现
     * 2、对于反序列化时间带时区偏移量 OffsetDateTime的时间类型，Gson是直接解析成和原来一样的时间，
     * 而 jackson-datatype-jsr310是解析成 UTC（+0000）的时间格式，也就是末尾为 Z，例如：2023-08-11T03:27:06.589Z
     * 3、对于要使用 LocalDateTime、LocalDate、LocalTime的时间类型，请使用JsonUtil
     * 4、对于[仅使用 OffsetDateTime带时区的时间类型]，建议使用 GsonUtil
     * 5、如果对带时区偏移量的时间格式要求不严格的话，建议使用 JsonUtil
     *
     * @param jsonStr 源 json字符串
     * @param clazz   对象类
     * @param type    对象类型
     * @param <T>     泛型
     */
    private static <T> T parseObject(String jsonStr, Class<T> clazz, TypeReference type) {
        T obj = null;
        if (StringUtil.isNotBlank(jsonStr)) {
            try {
                if (clazz != null) {
                    obj = OBJECTMAPPER.readValue(jsonStr, clazz);
                } else {
                    obj = (T) OBJECTMAPPER.readValue(jsonStr, type);
                }
            } catch (JsonProcessingException e) {
                log.warn(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
        return obj;
    }
}
