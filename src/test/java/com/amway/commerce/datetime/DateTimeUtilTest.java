package com.amway.commerce.datetime;

import com.sun.org.apache.xerces.internal.parsers.DTDParser;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author: Jason.Hu
 * @date: 2023-08-04
 * @desc:
 */
@Slf4j
public class DateTimeUtilTest {

    private static Date date = new Date();

    private static Date nullDate = null;

    private static LocalDateTime localDateTime = LocalDateTime.now();

    private static LocalDateTime nullLocalDateTime = null;

    private static String dateStr = "2019-09-29 03:45:59.445";

    private static String nullDateStr = null;

    /**
     * Date转换为 Str
     */
    @Test
    public void dateToStr() {
        // 2023-08-09 11:53:20.233
        System.out.println(DateTimeUtil.dateToStr(date, DateTimeUtil.PATTERN_DATE_TIME_MILLI));
        // ERROR - date is null   null
        System.out.println(DateTimeUtil.dateToStr(nullDate, DateTimeUtil.PATTERN_DATE_TIME_MILLI));
    }

    /**
     * LocalDateTime转换为 String
     */
    @Test
    public void localDateTimeToStr() {
        // 2023-08-09 11:54:16.618
        System.out.println(DateTimeUtil.localDateTimeToStr(localDateTime, DateTimeUtil.PATTERN_DATE_TIME_MILLI));
        // ERROR - localDateTime is null   null
        System.out.println(DateTimeUtil.localDateTimeToStr(nullLocalDateTime, DateTimeUtil.PATTERN_DATE_TIME_MILLI));
    }

    /**
     * LocalDateTime转换为 Date
     */
    @Test
    public void localDateTimeToDate() {
        // Wed Aug 09 11:55:01 CST 2023
        System.out.println(DateTimeUtil.localDateTimeToDate(localDateTime));
        // ERROR - localDateTime is null  null
        System.out.println(DateTimeUtil.localDateTimeToDate(nullLocalDateTime));
    }

    /**
     * Date转换为 LocalDateTime
     */
    @Test
    public void dateToLocalDateTime() {
        // 2023-08-09T11:56:09.318
        System.out.println(DateTimeUtil.dateToLocalDateTime(date));
        // ERROR - date is null   null
        System.out.println(DateTimeUtil.dateToLocalDateTime(nullDate));
    }

    /**
     * String转换为 Date
     */
    @Test
    public void strToDate() throws ParseException {
        // Sun Sep 29 03:45:59 CST 2019
        System.out.println(DateTimeUtil.strToDate(dateStr, DateTimeUtil.PATTERN_DATE_TIME_MILLI));
        // ERROR - dateStr is null  null
        System.out.println(DateTimeUtil.strToDate(nullDateStr, DateTimeUtil.PATTERN_TIME));
    }

    /**
     * String转换为 LocalDateTime
     */
    @Test
    public void strToLocalDateTime() {
        // 2019-09-29T03:45:59.445
        System.out.println(DateTimeUtil.strToLocalDateTime(dateStr, DateTimeUtil.PATTERN_DATE_TIME_MILLI));
        // ERROR - dateStr is null  null
        System.out.println(DateTimeUtil.strToLocalDateTime(nullDateStr, DateTimeUtil.PATTERN_TIME));
    }

    /**
     * String转换为 LocalDate
     */
    @Test
    public void strToLocalDate() {
        // 2019-09-29
        System.out.println(DateTimeUtil.strToLocalDate(dateStr, DateTimeUtil.PATTERN_DATE_TIME_MILLI));
        // ERROR - dateStr is null  null
        System.out.println(DateTimeUtil.strToLocalDate(nullDateStr, DateTimeUtil.PATTERN_TIME));
    }

    /**
     * 获取当前日期的开始时间
     */
    @Test
    public void getLocalDateStart() {
        // 2019-09-29T00:00
        System.out.println(DateTimeUtil.getLocalDateStart(dateStr, DateTimeUtil.PATTERN_DATE_TIME_MILLI));
        // ERROR - dateStr is null  null
        System.out.println(DateTimeUtil.strToLocalDate(nullDateStr, DateTimeUtil.PATTERN_TIME));
    }

    /**
     * 比较两个时间的大小
     */
    @Test
    public void compareTwoTime() {
        // 0
        System.out.println(DateTimeUtil.compare(LocalDateTime.now(), LocalDateTime.now()));
    }

    /**
     * Long转换为 String
     */
    @Test
    public void longToStr() {
        Long longTime = 9999999999999999L;
        // +318857-05-21 01:46:39.999
        System.out.println(DateTimeUtil.longToStr(longTime, DateTimeUtil.PATTERN_DATE_TIME_MILLI));
    }

    /**
     * 获取指定天数在本月的开始时间
     */
    @Test
    public void getStartDayOfMonth() {

        LocalDateTime now = LocalDateTime.now();
        // 2023-08-16T18:00:04.294
        System.out.println(now);
        // 2023-08-06T00:00
        System.out.println(DateTimeUtil.getStartDayOfMonth(now, 6));
        // 2023-08-17T00:00
        System.out.println(DateTimeUtil.getStartDayOfMonth(now, 17));
        // 2023-08-16T17:59:37.549
        System.out.println(DateTimeUtil.getStartDayOfMonth(now, 32));
        // 2023-08-31T00:00
        System.out.println(DateTimeUtil.getStartDayOfMonth(now, 31));
        // 2023-08-01T00:00
        System.out.println(DateTimeUtil.getStartDayOfMonth(now, 1));
    }
}