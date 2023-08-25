package com.amway.commerce.datetime;

import com.amway.commerce.string.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @author: Jason.Hu
 * @date: 2023-08-04
 * @desc: 时间工具类
 */
@Slf4j
public class DateTimeUtil {

    /**
     * yyyy-MM-dd
     */
    public static final String PATTERN_DATE = "yyyy-MM-dd";

    /**
     * yyyyMMdd
     */
    public static final String PATTERN_DATE_COMPACT = "yyyyMMdd";

    /**
     * yyyy年MM月dd日
     */
    public static final String PATTERN_DATE_CHINA = "yyyy年MM月dd日";

    /**
     * yyyy/MM/dd
     */
    public static final String PATTERN_DATE_BIAS = "yyyy/MM/dd";

    /**
     * HH:mm:ss
     */
    public static final String PATTERN_TIME = "HH:mm:ss";

    /**
     * HHmmss
     */
    public static final String PATTERN_TIME_COMPACT = "HHmmss";

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyyMMddHHmmss
     */
    public static final String PATTERN_DATE_TIME_COMPACT = "yyyyMMddHHmmss";

    /**
     * yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String PATTERN_DATE_TIME_MILLI = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String PATTERN_DATE_TIME_T = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * yyMM
     */
    public static final String PATTERN_YY_MM = "yyMM";

    /**
     * yyyyMM
     */
    public static final String PATTERN_YYYY_MM = "yyyyMM";

    /**
     * yyyy年MM月
     */
    public static final String PATTERN_CHINA_YYYY_MM = "yyyy年MM月";

    /**
     * 最大时间：2099-12-31 23:59:59
     */
    public static final String MAX_LOCAL_DATE_TIME = "2099-12-31 23:59:59";

    private static SimpleDateFormat simpleDateFormat;

    /**
     * Date转换为 String
     *
     * @param date    Date类型日期
     * @param pattern 待转换的时间格式
     * @return 如果 date不为空，则返回转换结果，否则返回 null
     */
    public static final String dateToStr(Date date, String pattern) {
        if (date == null) {
            log.warn("date is null");
            return null;
        }
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * LocalDateTime转换为 String
     *
     * @param localDateTime LocalDateTime类型日期
     * @param pattern       待转换的时间格式
     * @return 如果 localDateTime不为空，则返回转换结果，否则返回 null
     */
    public static String localDateTimeToStr(LocalDateTime localDateTime, String pattern) {
        if (localDateTime == null) {
            log.warn("localDateTime is null");
            return null;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * LocalDateTime转换为 Date
     *
     * @param localDateTime LocalDateTime类型日期
     * @return 如果 localDateTime不为空，则返回转换结果，否则返回 null
     */
    public static final Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            log.warn("localDateTime is null");
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date转换为 LocalDateTime
     *
     * @param date Date类型日期
     * @return 如果 date不为空，则返回转换结果，否则返回 null
     */
    public static final LocalDateTime dateToLocalDateTime(Date date) {
        if (date == null) {
            log.warn("date is null");
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * String转换为 Date
     *
     * @param dateStr 时间字符串
     * @param pattern 待转换的时间格式
     * @return 如果 dateStr不为空，则返回转换结果，否则返回 null
     */
    public static final Date strToDate(String dateStr, String pattern) throws ParseException {
        if (StringUtil.isBlank(dateStr)) {
            log.warn("dateStr is null");
            return null;
        }
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(dateStr);
    }

    /**
     * String转换为 LocalDateTime
     *
     * @param dateStr 时间字符串
     * @param pattern 待转换的时间格式
     * @return 如果 dateStr不为空，则返回转换结果，否则返回 null
     */
    public static LocalDateTime strToLocalDateTime(String dateStr, String pattern) {
        if (StringUtil.isBlank(dateStr)) {
            log.warn("dateStr is null");
            return null;
        }
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * String转换为 LocalDate
     *
     * @param dateStr 时间字符串
     * @param pattern 待转换的时间格式
     * @return 如果 dateStr不为空，则返回转换结果，否则返回 null
     */
    public static LocalDate strToLocalDate(String dateStr, String pattern) {
        if (StringUtil.isBlank(dateStr)) {
            log.warn("dateStr is null");
            return null;
        }
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前日期的开始时间
     *
     * @param dateStr 时间字符串
     * @param pattern 待转换的时间格式
     * @return 如果 dateStr不为空，则返回当前日期的开始时间（日期 + 00:00），否则返回 null
     */
    public static LocalDateTime getLocalDateStart(String dateStr, String pattern) {
        if (StringUtil.isBlank(dateStr)) {
            log.warn("dateStr is null");
            return null;
        }
        return LocalDateTime.of(strToLocalDate(dateStr, pattern), LocalTime.MIN);
    }

    /**
     * 比较两个时间的大小
     *
     * @param time1
     * @param time2
     * @return 0：相等；1：time1大；-1：time2大
     */
    public static int compare(LocalDateTime time1, LocalDateTime time2) {
        if (time1.isAfter(time2)) {
            return 1;
        } else if (time1.isBefore(time2)) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 获取某月某天的开始时间
     *
     * @param dateTime   指定日期
     * @param dayOfMonth 指定到月的某天
     * @return LocalDateTime
     */
    public static LocalDateTime getStartDayOfMonth(LocalDateTime dateTime, Integer dayOfMonth) {
        LocalDate now = dateTime.toLocalDate();
        // 当前日期月份的第一天 2023-0x-01
        LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
        // 当前日期月份的最后一天 2023-0x-xx
        LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate tempDay = firstDay;
        LocalDate localDay;
        // tempDay在最后一天之前 或者 tempDay与lastDay所属同月
        while (tempDay.isBefore(lastDay) || tempDay.getMonthValue() == lastDay.getMonthValue()) {
            // dayOfMonth 小于等于 tempDay所在月的总天数（dayOfMonth最大不能超过当前月的天数，如1月31天，2月28/29天）
            if (tempDay.lengthOfMonth() >= dayOfMonth) {
                // 设置 localDay为当前月的第 dayOfMonth天
                localDay = tempDay.withDayOfMonth(dayOfMonth);
                /**
                 * 以下两个 if条件来判断 localDay是否在当前月内（条件判断重复？）
                 */
                if (localDay.isAfter(firstDay) || localDay.isEqual(firstDay)) {
                    if (localDay.isEqual(lastDay) || localDay.isBefore(lastDay)) {
                        // 返回 localDay的 0点
                        return localDay.atStartOfDay();
                    }
                }
            }
            // 不满足则增加一个月，以退出循环
            tempDay = tempDay.plusMonths(1);
        }
        // tempDay不在当前月内，返回当前系统时间
        log.info("DayOfMonth is not within the month of DateTime");
        return LocalDateTime.now();
    }

    /**
     * Long转换为 String
     *
     * @param time    Long类型时间戳
     * @param pattern 待转换的时间格式
     * @return 如果 time不为空，则返回转换结果，否则返回 null
     */
    public static String longToStr(Long time, String pattern) {
        if (time == null) {
            return null;
        }
        return localDateTimeToStr(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()), pattern);
    }
}
