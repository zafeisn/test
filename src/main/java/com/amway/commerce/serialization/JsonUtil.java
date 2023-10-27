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
 */
@Slf4j
public class JsonUtil {

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    static {
        // 对象的所有字段全部列入，还是其他的选项，可以忽略 null等
        OBJECTMAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 设置 Date类型的序列化及反序列化格式
        OBJECTMAPPER.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_PATTERN));
        // 忽略空 Bean转 json的错误
        OBJECTMAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 忽略未知属性，防止 json字符串中存在，java对象中不存在对应属性的情况出现错误
        OBJECTMAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 注册一个时间序列化及反序列化的处理模块，用于解决 jdk8中 localDateTime等的序列化问题
        OBJECTMAPPER.registerModule(new JavaTimeModule());
    }

    /**
     * 将 Object对象转换为 JSON字符串。
     *
     * @param obj Object对象
     * @return JSON字符串
     * @throws JsonProcessingException
     *
     * <p>
     * <b>例：</b><br>
     * obj=null，返回 null；<p>
     * obj=""，返回 "\"\""；<p>
     * obj=123，返回 "123"。
     */
    public static String toJson(Object obj) throws JsonProcessingException {
        String json = null;
        if (obj != null) {
            json = OBJECTMAPPER.writeValueAsString(obj);
        }
        return json;
    }

    /**
     * 将 JSON字符串解析成对应的类对象。
     *
     * @param jsonStr JSON字符串
     * @param clazz   类对象
     * @param <T>     泛型
     * @return T
     *
     * <p>
     * <b>例：</b><br>
     * jsonStr=null，clazz=String.class，返回 null；<p>
     * jsonStr={"id":"0","obj":[{"id":"1","desc":"A"}]}，clazz=B.class，返回 B(id=0,obj=[{id=1,desc="A"}])。
     */
    public static <T> T parseObject(String jsonStr, Class<T> clazz) throws JsonProcessingException {
        return parseObject(jsonStr, clazz, null);
    }

    /**
     * 将 JSON字符串解析成对应的类对象。
     *
     * @param jsonStr JSON字符串
     * @param type    对象类型的引用或指向，常用于泛型
     * @param <T>     泛型
     * @return T
     *
     * <p>
     * <b>例：</b><br>
     * jsonStr=null，clazz=new TypeReference<String>(){}，返回 null；<p>
     * jsonStr={"id":"0","obj":[{"id":"1","desc":"A"}]}，type=new TypeReference(B<List<A>>>(){})，返回 B(id=0,obj=[A(id=1,desc="A")])。
     */
    public static <T> T parseObject(String jsonStr, TypeReference type) throws JsonProcessingException {
        return parseObject(jsonStr, null, type);
    }

    private static <T> T parseObject(String jsonStr, Class<T> clazz, TypeReference type) throws JsonProcessingException {
        T obj = null;
        if (!StringUtil.isEmpty(jsonStr)) {
            if (clazz != null) {
                obj = OBJECTMAPPER.readValue(jsonStr, clazz);
            } else {
                obj = (T) OBJECTMAPPER.readValue(jsonStr, type);
            }
        }
        return obj;
    }

}
