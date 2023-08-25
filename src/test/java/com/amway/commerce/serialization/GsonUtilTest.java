package com.amway.commerce.serialization;

import org.junit.Test;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: Jason.Hu
 * @date: 2023-08-10
 * @desc:
 */
public class GsonUtilTest {

    /**
     * json转换为实体对象
     * GsonUtil和 JsonUtil的联系和区别：
     * 1、Gson需要自己去实现 LocalDateTime、LocalDate、LocalTime、OffsetDateTime序列化和反序列化方法，
     *   具体为继承抽象类 TypeAdapter，实现 write和 read方法；而 jackson可以利用 jackson-datatype-jsr310库去实现
     * 2、对于反序列化时间带时区偏移量 OffsetDateTime的时间类型，Gson是直接解析成和原来一样的时间，
     *    而 jackson-datatype-jsr310是解析成 UTC（+0000）的时间格式，也就是末尾为 Z，例如：2023-08-11T03:27:06.589Z
     * 3、对于要使用 LocalDateTime、LocalDate、LocalTime的时间类型，请使用JsonUtil
     * 4、对于[仅使用 OffsetDateTime带时区的时间类型]，建议使用 GsonUtil
     * 5、如果对带时区偏移量的时间格式要求不严格的话，建议使用 JsonUtil
     */
    @Test
    public void parseObject() {

        B b = new B();
        b.setDate(new Date());
        b.setDesc("中文");
        b.setId(1);
        b.setOffsetDateTime(OffsetDateTime.now());
//        b.setLocalDateTime(LocalDateTime.now());
        String s = JsonUtil.toJson(b);
        // B(desc=中文, id=1, offsetDateTime=2023-08-11T11:27:06.287+08:00, date=Fri Aug 11 11:27:06 CST 2023)
        System.out.println(GsonUtil.parseObject(s, B.class));
        // B(desc=中文, id=1, offsetDateTime=2023-08-11T03:27:06.287Z, date=Fri Aug 11 11:27:06 CST 2023)
        System.out.println(JsonUtil.parseObject(s, B.class));

        B b2 = new B();
        b2.setDate(new Date());
        b2.setDesc("中文");
        b2.setId(1);
        System.out.println(ZonedDateTime.now(ZoneId.of("+0000")).toOffsetDateTime());
        b2.setOffsetDateTime(ZonedDateTime.now(ZoneId.of("+0000")).toOffsetDateTime());
        String s1 = JsonUtil.toJson(b2);
        // B(desc=中文, id=1, offsetDateTime=2023-08-11T03:27:06.589Z, date=Fri Aug 11 11:27:06 CST 2023)
        System.out.println(GsonUtil.parseObject(s1, B.class));
        // B(desc=中文, id=1, offsetDateTime=2023-08-11T03:27:06.589Z, date=Fri Aug 11 11:27:06 CST 2023)
        System.out.println(JsonUtil.parseObject(s1, B.class));

        B b3 = new B();
        b3.setDate(null);
        b3.setDesc("中文");
        b3.setId(1);
        b3.setOffsetDateTime(null);
        String s2 = JsonUtil.toJson(b3);
        // B(desc=中文, id=1, offsetDateTime=null, date=null)
        System.out.println(GsonUtil.parseObject(s2, B.class));
        // B(desc=中文, id=1, offsetDateTime=null, date=null)
        System.out.println(JsonUtil.parseObject(s2, B.class));
    }

    /**
     * Object转换为 json字符串
     */
    @Test
    public void toJson() {
        B b = new B();
        b.setDate(new Date());
        b.setDesc("中文");
        b.setId(1);
        b.setOffsetDateTime(OffsetDateTime.now());
        // {"desc":"中文","id":1,"offsetDateTime":"2023-08-11T11:58:59.94+08:00","date":"2023-08-11 11:58:59","localDateTime":null}
        System.out.println(GsonUtil.toJson(b));

        List<B> list = new ArrayList<>();
        list.add(b);
        String s = GsonUtil.toJson(list);
        // [{"desc":"中文","id":1,"offsetDateTime":"2023-08-11T11:58:59.94+08:00","date":"2023-08-11 11:58:59","localDateTime":null}]
        System.out.println(s);
        // [{desc=中文, id=1.0, offsetDateTime=2023-08-11T11:58:59.94+08:00, date=2023-08-11 11:58:59, localDateTime=null}]
        System.out.println(GsonUtil.parseObject(s, List.class));

        List<List<B>> lists = new ArrayList<>();
        lists.add(list);
        String s1 = GsonUtil.toJson(lists);
        System.out.println(s1);
        System.out.println(GsonUtil.parseObject(s1, List.class));
    }
}