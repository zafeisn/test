package com.amway.commerce.serialization;

import com.amway.commerce.string.StringUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * @author: Jason.Hu
 * @date: 2023-09-20
 */
public class JsonUtilTest {

    @Data
    static class A<T> {
        private Integer id;
        private String desc;
        private Date date;
        private LocalDateTime localDateTime;
        private LocalDate localDate;
        private T t;
        public A() {
        }
        public A(Integer id, String desc, Date date, LocalDateTime localDateTime, LocalDate localDate, T t) {
            this.id = id;
            this.desc = desc;
            this.date = date;
            this.localDateTime = localDateTime;
            this.localDate = localDate;
            this.t = t;
        }
    }

    @Data
    static class B {
        private int id;
        private String desc;
        private Date date;
        private OffsetDateTime offsetDateTime;
        private LocalDateTime localDateTime;
        public B(){
        }
        public B(int id, String desc, Date date, OffsetDateTime offsetDateTime, LocalDateTime localDateTime) {
            this.id = id;
            this.desc = desc;
            this.date = date;
            this.offsetDateTime = offsetDateTime;
            this.localDateTime = localDateTime;
        }
    }

    @Test
    public void testToJson() throws JsonProcessingException {
        Date date = new Date(0);
        LocalDateTime dateTime_01 = LocalDateTime.of(2000, 12, 31, 2, 2, 50,321);
        LocalDateTime dateTime_02 = LocalDateTime.of(2023, 12, 31, 2, 2, 40,345);
        OffsetDateTime offsetDateTime_01 = OffsetDateTime.of(dateTime_01, ZoneOffset.UTC);
        OffsetDateTime offsetDateTime_02 = OffsetDateTime.of(dateTime_02, ZoneOffset.UTC);

        // normal：简单对象，包含时间类型
        B b1 = new B(1,"你好世界hello world", date, offsetDateTime_01, dateTime_01);
        B b2 = new B(2,"你好，世界。HELLO,WORLD!", date, offsetDateTime_02, dateTime_02);
        Assert.assertEquals("{\"id\":1,\"desc\":\"你好世界hello world\",\"date\":\"1970-01-01 08:00:00\",\"offsetDateTime\":\"2000-12-31T02:02:50.000000321Z\",\"localDateTime\":\"2000-12-31T02:02:50.000000321\"}",
                JsonUtil.toJson(b1));
        Assert.assertEquals("{\"id\":2,\"desc\":\"你好，世界。HELLO,WORLD!\",\"date\":\"1970-01-01 08:00:00\",\"offsetDateTime\":\"2023-12-31T02:02:40.000000345Z\",\"localDateTime\":\"2023-12-31T02:02:40.000000345\"}",
                JsonUtil.toJson(b2));

        // boundary：复杂对象、长文本、null、空值、空格、特殊字符、数字、0、Integer最大值、关键字
        // 复杂对象，如A<List<B>>
        LocalDate localDate_01 = LocalDate.of(2000, 2, 29);
        LocalDate localDate_02 = LocalDate.of(2023, 12, 31);
        List<B> list_01 = new ArrayList<>();
        list_01.add(b1);
        List<B> list_02 = new ArrayList<>();
        list_02.add(b1);
        list_02.add(b2);
        List<B> list_03 = new ArrayList<>();
        list_03.add(b1);
        list_03.add(b2);
        list_03.add(b2);
        Map<String, B> map = new HashMap();
        map.put("b1", b1);
        map.put("b2", b2);
        A<List<B>> a1 = new A<>(-1,"我是A1^_^",date,dateTime_01, localDate_01, list_01);
        A<List<B>> a2 = new A<>(0,"我是A2~.~",date,dateTime_02, localDate_02, list_02);
        A<List<B>> a3 = new A<>(1,"我是A3$#$",date,dateTime_02, localDate_02, list_03);
        Assert.assertEquals("{\"id\":-1,\"desc\":\"我是A1^_^\",\"date\":\"1970-01-01 08:00:00\",\"localDateTime\":\"2000-12-31T02:02:50.000000321\",\"localDate\":\"2000-02-29\",\"t\":[{\"id\":1,\"desc\":\"你好世界hello world\",\"date\":\"1970-01-01 08:00:00\",\"offsetDateTime\":\"2000-12-31T02:02:50.000000321Z\",\"localDateTime\":\"2000-12-31T02:02:50.000000321\"}]}",
                JsonUtil.toJson(a1));
        Assert.assertEquals("{\"id\":0,\"desc\":\"我是A2~.~\",\"date\":\"1970-01-01 08:00:00\",\"localDateTime\":\"2023-12-31T02:02:40.000000345\",\"localDate\":\"2023-12-31\",\"t\":[{\"id\":1,\"desc\":\"你好世界hello world\",\"date\":\"1970-01-01 08:00:00\",\"offsetDateTime\":\"2000-12-31T02:02:50.000000321Z\",\"localDateTime\":\"2000-12-31T02:02:50.000000321\"},{\"id\":2,\"desc\":\"你好，世界。HELLO,WORLD!\",\"date\":\"1970-01-01 08:00:00\",\"offsetDateTime\":\"2023-12-31T02:02:40.000000345Z\",\"localDateTime\":\"2023-12-31T02:02:40.000000345\"}]}",
                JsonUtil.toJson(a2));
        Assert.assertEquals("{\"id\":1,\"desc\":\"我是A3$#$\",\"date\":\"1970-01-01 08:00:00\",\"localDateTime\":\"2023-12-31T02:02:40.000000345\",\"localDate\":\"2023-12-31\",\"t\":[{\"id\":1,\"desc\":\"你好世界hello world\",\"date\":\"1970-01-01 08:00:00\",\"offsetDateTime\":\"2000-12-31T02:02:50.000000321Z\",\"localDateTime\":\"2000-12-31T02:02:50.000000321\"},{\"id\":2,\"desc\":\"你好，世界。HELLO,WORLD!\",\"date\":\"1970-01-01 08:00:00\",\"offsetDateTime\":\"2023-12-31T02:02:40.000000345Z\",\"localDateTime\":\"2023-12-31T02:02:40.000000345\"},{\"id\":2,\"desc\":\"你好，世界。HELLO,WORLD!\",\"date\":\"1970-01-01 08:00:00\",\"offsetDateTime\":\"2023-12-31T02:02:40.000000345Z\",\"localDateTime\":\"2023-12-31T02:02:40.000000345\"}]}",
                JsonUtil.toJson(a3));
        Assert.assertEquals("{\"b2\":{\"id\":2,\"desc\":\"你好，世界。HELLO,WORLD!\",\"date\":\"1970-01-01 08:00:00\",\"offsetDateTime\":\"2023-12-31T02:02:40.000000345Z\",\"localDateTime\":\"2023-12-31T02:02:40.000000345\"},\"b1\":{\"id\":1,\"desc\":\"你好世界hello world\",\"date\":\"1970-01-01 08:00:00\",\"offsetDateTime\":\"2000-12-31T02:02:50.000000321Z\",\"localDateTime\":\"2000-12-31T02:02:50.000000321\"}}",
                JsonUtil.toJson(map));
        // 长文本
        String longStr = StringUtil.streamToStr(JsonUtilTest.class.getResourceAsStream("/long.txt"));
        Assert.assertEquals("\"领域驱动设计是一种软件架构方法它强调将业务领域的知识和概念映射到软件模型中通过深入了解业务领域的核心概念和流程能够帮助开发人员构建更贴近业务需求的软件系统在中业务领域被划分为一系列有界上下文每个有界上下文都是一个独立的领域模型通过明确有界上下文的边界和交互方式能够避免不同部分之间的模型混乱和冲突强调使用领域特定语言来描述业务需求和规则使业务专家和开发人员能够更好地沟通和协作通过使用开发人员能够将业务逻辑和规则融入到代码中从而提高代码的可读性和可维护性总之领域驱动设计是一种将业务领域知识和概念映射到软件模型中的架构方法它能够帮助开发人员构建更贴近业务需求的软件系统提高代码质量和可维护性295gehanzi(ABC)，。！【】\"",
                JsonUtil.toJson(longStr));
        // null、空值、空格、特殊字符、数字、0、Integer最大值、关键字
        Assert.assertEquals(null, JsonUtil.toJson(null));
        Assert.assertEquals("\"\"", JsonUtil.toJson(""));
        Assert.assertEquals("\" \"", JsonUtil.toJson(" "));
        Assert.assertEquals("\" \"", JsonUtil.toJson(" "));
        Assert.assertEquals("\"\\n\"", JsonUtil.toJson("\n"));
        Assert.assertEquals("\"\\\\\"", JsonUtil.toJson("\\"));
        Assert.assertEquals("true", JsonUtil.toJson(true));
        Assert.assertNotEquals(true, JsonUtil.toJson("true"));
        Assert.assertEquals("123", JsonUtil.toJson(123));
        Assert.assertEquals("2147483647", JsonUtil.toJson(Integer.MAX_VALUE));
        Assert.assertEquals("0", JsonUtil.toJson(0));
        Assert.assertEquals("0.0", JsonUtil.toJson(0.0000000000000000000));
        Assert.assertEquals("0E-19", JsonUtil.toJson(new BigDecimal("0.0000000000000000000")));
        Assert.assertNotEquals(new BigDecimal(0), JsonUtil.toJson(new BigDecimal(0)));
        Assert.assertEquals("0", JsonUtil.toJson(new BigDecimal(0)));
        // java代码
        Assert.assertNotNull(JsonUtil.toJson("new BigDecimal(0)"));
        String str = null;
        Assert.assertNotNull(str, JsonUtil.toJson("str = \"我是Java代码\";"));
        Assert.assertNotNull("", JsonUtil.toJson("new Thread(()->{System.out.println(\"我是Java代码\");})"));
    }

    @Test
    public void testParseObject() throws JsonProcessingException {
        Date date = new Date(0);
        LocalDateTime dateTime_01 = LocalDateTime.of(2000, 12, 31, 2, 2, 50,321);
        LocalDateTime dateTime_02 = LocalDateTime.of(2023, 12, 31, 2, 2, 40,345);
        OffsetDateTime offsetDateTime_01 = OffsetDateTime.of(dateTime_01, ZoneOffset.UTC);
        OffsetDateTime offsetDateTime_02 = OffsetDateTime.of(dateTime_02, ZoneOffset.UTC);

        // normal：简单对象
        B b1 = new B(1,"你好世界hello world", date, offsetDateTime_01, dateTime_01);
        B b2 = new B(2,"你好，世界。HELLO,WORLD!", date, offsetDateTime_02, dateTime_02);

        Assert.assertEquals(b1, JsonUtil.parseObject(JsonUtil.toJson(b1), B.class));
        Assert.assertEquals(b2, JsonUtil.parseObject(JsonUtil.toJson(b2), B.class));
        Assert.assertEquals(b1, JsonUtil.parseObject(JsonUtil.toJson(b1), new TypeReference<B>(){}));
        Assert.assertEquals(b2, JsonUtil.parseObject(JsonUtil.toJson(b2), new TypeReference<B>(){}));

        // boundary：null、空值、空格、特殊字符、关键字、数字、极限值、0、长文本、复杂对象
        LocalDate localDate_01 = LocalDate.of(2000, 2, 29);
        LocalDate localDate_02 = LocalDate.of(2023, 12, 31);
        List<B> list_01 = new ArrayList<>();
        list_01.add(b1);
        List<B> list_02 = new ArrayList<>();
        list_02.add(b1);
        list_02.add(b2);
        List<B> list_03 = new ArrayList<>();
        list_03.add(b1);
        list_03.add(b2);
        list_03.add(b2);
        A<List<B>> a1 = new A<>(-1,"我是A1^_^",date,dateTime_01, localDate_01, list_01);
        A<List<B>> a2 = new A<>(0,"我是A2~.~",date,dateTime_02, localDate_02, list_02);
        A<List<B>> a3 = new A<>(1,"我是A3$#$",date,dateTime_02, localDate_02, list_03);
        Map<String, B> map_01 = new HashMap();
        map_01.put("b1", b1);
        map_01.put("b2", b2);
        Map<String, A> map_02 = new HashMap();
        map_02.put("a1", a1);
        map_02.put("a2", a2);
        // null
        Assert.assertEquals(null, JsonUtil.parseObject(JsonUtil.toJson(null), String.class));
        Assert.assertEquals(null, JsonUtil.parseObject(JsonUtil.toJson(null), new TypeReference<String>(){}));
        // 空值、空格、特殊字符、关键字
        Assert.assertEquals("", JsonUtil.parseObject(JsonUtil.toJson(""), Object.class));
        Assert.assertEquals("", JsonUtil.parseObject(JsonUtil.toJson(""), new TypeReference<Object>(){}));
        Assert.assertEquals(" ", JsonUtil.parseObject(JsonUtil.toJson(" "), Object.class));
        Assert.assertEquals(" ", JsonUtil.parseObject(JsonUtil.toJson(" "), new TypeReference<Object>(){}));
        Assert.assertEquals("\n", JsonUtil.parseObject(JsonUtil.toJson("\n"), Object.class));
        Assert.assertEquals("\n", JsonUtil.parseObject(JsonUtil.toJson("\n"), new TypeReference<Object>(){}));
        Assert.assertEquals("\\", JsonUtil.parseObject(JsonUtil.toJson("\\"), Object.class));
        Assert.assertEquals("\\", JsonUtil.parseObject(JsonUtil.toJson("\\"), new TypeReference<Object>(){}));
        Assert.assertEquals(true, JsonUtil.parseObject(JsonUtil.toJson(true), Boolean.class));
        Assert.assertEquals(true, JsonUtil.parseObject(JsonUtil.toJson(true), new TypeReference<Boolean>(){}));
        // 数字、极限值
        Assert.assertEquals(new Integer(123), JsonUtil.parseObject(JsonUtil.toJson(123), Integer.class));
        Assert.assertEquals(new Integer(123), JsonUtil.parseObject(JsonUtil.toJson(123), new TypeReference<Integer>(){}));
        Assert.assertEquals(new Integer(2147483647), JsonUtil.parseObject(JsonUtil.toJson(Integer.MAX_VALUE), Integer.class));
        Assert.assertEquals(new Integer(2147483647), JsonUtil.parseObject(JsonUtil.toJson(Integer.MAX_VALUE), new TypeReference<Integer>(){}));
        // 0
        Assert.assertEquals(new Long(0), JsonUtil.parseObject(JsonUtil.toJson(0), Long.class));
        Assert.assertEquals(new Long(0), JsonUtil.parseObject(JsonUtil.toJson(0), new TypeReference<Long>(){}));
        Assert.assertEquals(new Double(0.0), JsonUtil.parseObject(JsonUtil.toJson(0.0000000000000000000), Double.class));
        Assert.assertEquals(new Double(0.0), JsonUtil.parseObject(JsonUtil.toJson(0.0000000000000000000), new TypeReference<Double>(){}));
        Assert.assertEquals(new BigDecimal("0.0000000000000000000"), JsonUtil.parseObject(JsonUtil.toJson(new BigDecimal("0.0000000000000000000")), BigDecimal.class));
        Assert.assertEquals(new BigDecimal("0.0000000000000000000"), JsonUtil.parseObject(JsonUtil.toJson(new BigDecimal("0.0000000000000000000")), new TypeReference<BigDecimal>(){}));
        // 长文本
        String longStr = StringUtil.streamToStr(JsonUtilTest.class.getResourceAsStream("/DDD.txt"));
        Assert.assertEquals(longStr, JsonUtil.parseObject(JsonUtil.toJson(longStr), String.class));
        Assert.assertEquals(longStr, JsonUtil.parseObject(JsonUtil.toJson(longStr), new TypeReference<String>(){}));
        // 复杂对象
        Assert.assertNotEquals(a1, JsonUtil.parseObject(JsonUtil.toJson(a1), A.class));
        Assert.assertEquals(a1, JsonUtil.parseObject(JsonUtil.toJson(a1), new TypeReference<A<List<B>>>(){}));
        Assert.assertNotEquals(a2, JsonUtil.parseObject(JsonUtil.toJson(a2), A.class));
        Assert.assertEquals(a2, JsonUtil.parseObject(JsonUtil.toJson(a2), new TypeReference<A<List<B>>>(){}));
        Assert.assertNotEquals(a3, JsonUtil.parseObject(JsonUtil.toJson(a3), A.class));
        Assert.assertEquals(a3, JsonUtil.parseObject(JsonUtil.toJson(a3), new TypeReference<A<List<B>>>(){}));
        Assert.assertNotEquals(map_01, JsonUtil.parseObject(JsonUtil.toJson(map_01), Map.class));
        Assert.assertEquals(map_01, JsonUtil.parseObject(JsonUtil.toJson(map_01), new TypeReference<Map<String, B>>(){}));
        Assert.assertNotEquals(map_02, JsonUtil.parseObject(JsonUtil.toJson(map_02), Map.class));
        Assert.assertEquals(map_02, JsonUtil.parseObject(JsonUtil.toJson(map_02), new TypeReference<Map<String, A<List<B>>>>(){}));
        // java代码
        Assert.assertNotNull(JsonUtil.parseObject(JsonUtil.toJson("System.out.println(\"我是Java代码\");"), Object.class));

        // exception：非法参数
        Assert.assertThrows(JsonParseException.class, ()->{JsonUtil.parseObject("123a", Integer.class);});
        Assert.assertThrows(JsonParseException.class, ()->{JsonUtil.parseObject("123a", new TypeReference<A<List<B>>>(){});});
    }
}