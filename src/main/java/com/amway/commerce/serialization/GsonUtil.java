package com.amway.commerce.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author: Jason.Hu
 * @date: 2023-08-10
 * @desc: Gson工具类 - v2.8.5
 */
public class GsonUtil {

    private static final Gson GSON;

    static {
        GSON = new GsonBuilder()
                // 当 Map的 key为复杂对象时，需要开启该方法
                .enableComplexMapKeySerialization()
                // 不过滤空值
                .serializeNulls()
                // 设置 Date类型时间格式
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                // 防止特殊字符出现乱码
                .disableHtmlEscaping()
                // OffsetDateTime序列化和反序列化
                .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeTypeAdapter())
                .create();
    }

    /**
     * json转换为实体对象
     * <p>
     * GsonUtil和 JsonUtil的联系和区别：
     * 1、Gson需要自己去实现 LocalDateTime、LocalDate、LocalTime、OffsetDateTime序列化和反序列化方法，
     * 具体为继承抽象类 TypeAdapter，实现 write和 read方法；而 jackson可以利用 jackson-datatype-jsr310库去实现
     * 2、对于反序列化时间带时区偏移量 OffsetDateTime的时间类型，Gson是直接解析成和原来一样的时间，
     * 而 jackson-datatype-jsr310是解析成 UTC（+0000）的时间格式，也就是末尾为 Z，例如：2023-08-11T03:27:06.589Z
     * 3、对于要使用 LocalDateTime、LocalDate、LocalTime的时间类型，请使用JsonUtil
     * 4、对于[仅使用 OffsetDateTime带时区的时间类型]，建议使用 GsonUtil
     * 5、如果对带时区偏移量的时间格式要求不严格的话，建议使用 JsonUtil
     *
     * @param jsonStr 待转换的 json字符串
     * @param clazz   目标对象类型
     * @return 转换后的目标对象
     */
    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        return GSON.fromJson(jsonStr, clazz);
    }

    /**
     * Object转换为 json字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        return GSON.toJson(object);
    }


    /**
     * Gson序列化和反序列化 Java8+的 OffsetDateTime时间格式类
     * OffsetDateTime比 LocalDateTime多时区偏移量
     * 例如：2023-12-31T23:00+08:00 东八区
     */
    static class OffsetDateTimeTypeAdapter extends TypeAdapter<OffsetDateTime> {

        private DateTimeFormatter formatter;

        /**
         * 空参构造器，默认使用 ISO_OFFSET_DATE_TIME时间格式
         */
        public OffsetDateTimeTypeAdapter() {
            this(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }

        /**
         * 含参构造器
         *
         * @param formatter
         */
        public OffsetDateTimeTypeAdapter(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        /**
         * 设置时间格式
         *
         * @param formatter
         */
        public void setFormatter(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        /**
         * OffsetDateTime序列化，默认使用 ISO_OFFSET_DATE_TIME时间格式
         *
         * @param out
         * @param date 带时区偏移量的时间
         * @throws IOException
         */
        @Override
        public void write(JsonWriter out, OffsetDateTime date) throws IOException {
            out.value(formatter.format(date));
        }

        /**
         * OffsetDateTime反序列化，默认使用 ISO_OFFSET_DATE_TIME时间格式
         *
         * @param reader
         * @return
         * @throws IOException
         */
        @Override
        public OffsetDateTime read(JsonReader reader) throws IOException {
            // 判断是否为 null
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return null;
            }
            String date = reader.nextString();
            return OffsetDateTime.parse(date, formatter);
        }
    }
}
