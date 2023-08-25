package com.amway.commerce.serialization;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.Assert.*;

/**
 * @author: Jason.Hu
 * @date: 2023-08-09
 * @desc:
 */
@Slf4j
public class JsonUtilTest {

    /**
     * Object转换为 Json
     */
    @Test
    public void toJson() {
        log.info("普通对象转 Json");
        A a = new A();
        a.setId(1);
        a.setDate(new Date());
        a.setLocalDate(LocalDate.now());
        a.setLocalDateTime(LocalDateTime.now());
        a.setLocalTime(LocalTime.now());
        a.setDesc("中文");
        // {"id":1,"date":"2023-08-09 15:02:07","localDateTime":"2023-08-09T15:02:07.28","localDate":"2023-08-09","localTime":"15:02:07.28","desc":null}
        System.out.println(JsonUtil.toJson(a));

        log.info("map 转Json");
        HashMap<String, Object> map = new HashMap<>();
        map.put("A", a);
        map.put("B", "b");
        // {"A":{"id":1,"date":"2023-08-09 15:02:07","localDateTime":"2023-08-09T15:02:07.28","localDate":"2023-08-09","localTime":"15:02:07.28","desc":null},"B":"b"}
        System.out.println(JsonUtil.toJson(map));

        HashMap<String, Object> map1 = null;
        // null
        System.out.println(JsonUtil.toJson(map1));
    }

    /**
     * json字符串转换为 Object
     */
    @Test
    public void parseObject() {
        A a = new A();
        a.setId(1);
        a.setDate(new Date());
        a.setLocalDate(LocalDate.now());
        a.setLocalDateTime(LocalDateTime.now());
        a.setLocalTime(LocalTime.now());
        String s = JsonUtil.toJson(a);
        A a1 = JsonUtil.parseObject(s, A.class);
        // A(id=1, date=Wed Aug 09 15:46:52 CST 2023, localDateTime=2023-08-09T15:46:53.017, localDate=2023-08-09, localTime=15:46:53.017, desc=null)
        System.out.println(a1);

        List<Object> list = new ArrayList<>();
        list.add(a);
        list.add("2");
        String toJson = JsonUtil.toJson(list);

        // [{id=1, date=2023-08-09 15:46:52, localDateTime=2023-08-09T15:46:53.017, localDate=2023-08-09, localTime=15:46:53.017, desc=null}, 2]
        List<Object> fromJson = JsonUtil.parseObject(toJson, List.class);
        System.out.println(fromJson);

        HashMap<String, Object> map = new HashMap<>();
        map.put("A", a);
        map.put("B", "b");
        String s1 = JsonUtil.toJson(map);
        // {A={id=1, date=2023-08-09 16:02:21, localDateTime=2023-08-09T16:02:21.133, localDate=2023-08-09, localTime=16:02:21.133, desc=null}, B=b}
        System.out.println(JsonUtil.parseObject(s1, Map.class));

        List<List<Object>> list1 = new ArrayList<>();
        list1.add(list);
        String s2 = JsonUtil.toJson(list1);
        // [[{"id":1,"date":"2023-08-09 16:02:21","localDateTime":"2023-08-09T16:02:21.133","localDate":"2023-08-09","localTime":"16:02:21.133","desc":null},"2"]]
        System.out.println(s2);
        //[[{id=1, date=2023-08-09 16:02:21, localDateTime=2023-08-09T16:02:21.133, localDate=2023-08-09, localTime=16:02:21.133, desc=null}, 2]]
        System.out.println(JsonUtil.parseObject(s2, List.class));
    }
}