package com.amway.commerce.datetime;

import com.amway.commerce.exception.CommonError;
import com.amway.commerce.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @author: Jason.Hu
 * @date: 2023-08-04
 */
@Slf4j
public class DateTimeUtil {

    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_DATE_COMPACT = "yyyyMMdd";
    public static final String PATTERN_DATE_CHINA = "yyyy年MM月dd日";
    public static final String PATTERN_DATE_BIAS = "yyyy/MM/dd";
    public static final String PATTERN_TIME = "HH:mm:ss";
    public static final String PATTERN_TIME_COMPACT = "HHmmss";
    public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE_TIME_COMPACT = "yyyyMMddHHmmss";
    public static final String PATTERN_DATE_TIME_MILLI = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String PATTERN_DATE_TIME_T = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String PATTERN_YY_MM = "yyMM";
    public static final String PATTERN_YYYY_MM = "yyyyMM";
    public static final String PATTERN_CHINA_YYYY_MM = "yyyy年MM月";
    private static SimpleDateFormat simpleDateFormat;

    /**
     * 将 Date类型的时间格式化为时间/日期字符串。
     * 该方法需要对参数进行非空判断，若 date或 pattern为空，则抛出参数不能为空异常。
     *
     * @param date    Date类型日期，不可为空
     * @param pattern 时间格式字符串，不可为空
     * @return 格式化的时间字符串
     *
     * <p>
     * <b>例：</b><br>
     * date=null，pattern=PATTERN_DATE_TIME_MILLI，抛出“参数不能为空”异常提示信息；<p>
     * date=new Date()，pattern=null，抛出“参数不能为空”异常提示信息；<p>
     * date=Thu Sep 14 17:20:53 CST 2023，pattern=PATTERN_DATE_TIME_MILLI，返回 2023-09-14 17:20:53.220。
     */
    public static String format(Date date, String pattern) {
        isNotNull(date, pattern);
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 将 LocalDateTime类型的时间格式化为时间/日期字符串。
     * 该方法需要对参数进行非空判断，若 dateTime或 pattern为空，则抛出参数不能为空异常。
     *
     * @param dateTime LocalDateTime类型日期，不可为空
     * @param pattern  时间格式字符串，不可为空
     * @return 格式化的时间字符串
     *
     * <p>
     * <b>例：</b><br>
     * localDateTime=null，pattern=PATTERN_DATE_TIME_MILLI，抛出“参数不能为空”异常提示信息；<p>
     * localDateTime=LocalDateTime.now()，pattern=null，抛出“参数不能为空”异常提示信息；<p>
     * localDateTime=2023-09-14T17:26:26.731，pattern=PATTERN_DATE_TIME_MILLI，返回 2023-09-14 17:26:26.731。
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        isNotNull(dateTime, pattern);
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将 Long类型的时间戳格式化为时间/日期字符串。
     * 该方法需要对参数进行非空判断，若 time或 pattern为空，则抛出参数不能为空异常。
     *
     * @param time    Long类型时间戳，不可为空
     * @param pattern 时间格式字符串，不可为空
     * @return 格式化的时间字符串
     *
     * <p>
     * <b>例：</b><br>
     * long=null，pattern=PATTERN_DATE_TIME_MILLI，抛出“参数不能为空”异常提示信息；<p>
     * long=2000，pattern=null，抛出“参数不能为空”异常提示信息；<p>
     * long=2023，pattern=PATTERN_DATE_TIME_MILLI，返回 1970-01-01 08:00:02.023。
     */
    public static String format(Long time, String pattern) {
        isNotNull(time, pattern);
        return format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()), pattern);
    }

    /**
     * 将 LocalDateTime类型转换为 Date类型。
     * 该方法需要对参数进行非空判断，若 dateTime为空，则抛出参数不能为空异常。
     *
     * @param dateTime LocalDateTime类型日期，不可为空
     * @return Date类型日期
     *
     * <p>
     * <b>例：</b><br>
     * localDateTime=null，抛出“参数不能为空”异常提示信息；<p>
     * localDateTime=2023-09-14T17:36:18.522，返回 Thu Sep 14 17:36:18 CST 2023。
     */
    public static Date toDate(LocalDateTime dateTime) {
        isNotNull(dateTime);
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将 Date类型转换为 LocalDateTime类型。
     * 该方法需要对参数进行非空判断，若 date为空，则抛出参数不能为空异常。
     *
     * @param date Date类型日期，不可为空
     * @return LocalDateTime类型日期
     *
     * <p>
     * <b>例：</b><br>
     * date=null，抛出“参数不能为空”异常提示信息；<p>
     * date=Thu Sep 14 17:36:18 CST 2023，返回 2023-09-14T17:36:18.522。
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        isNotNull(date);
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 将时间字符串按照相应格式解析成 Date类型日期。
     * 该方法需要对参数进行非空判断，若 dateStr或 pattern为空，则抛出参数不能为空异常。
     *
     * @param dateStr 时间字符串，不可为空
     * @param pattern 时间格式字符串，不可为空
     * @return Date类型日期
     * @throws ParseException 时间字符串与时间格式不匹配，无法解析
     *
     * <p>
     * <b>例：</b><br>
     * dateStr=null，pattern=PATTERN_DATE_TIME_MILLI，抛出“参数不能为空”异常提示信息；<p>
     * dateStr="2023-09-14 17:20:53.220"，pattern=null，抛出“参数不能为空”异常提示信息；<p>
     * dateStr="2023-09-14 17:20:53.220"，pattern=PATTERN_DATE_TIME_MILLI，返回 Thu Sep 14 17:20:53 CST 2023。
     */
    public static Date parseDate(String dateStr, String pattern) throws ParseException {
        isNotNull(dateStr, pattern);
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(dateStr);
    }

    /**
     * 将时间字符串按照相应格式解析成 LocalDate类型日期。
     * 该方法需要对参数进行非空判断，若 dateStr或 pattern为空，则抛出参数不能为空异常。
     *
     * @param dateStr 时间字符串，不可为空
     * @param pattern 时间格式字符串，不可为空
     * @return LocalDate类型日期
     *
     * <p>
     * <b>例：</b><br>
     * dateStr=null，pattern=PATTERN_DATE_TIME_MILLI，抛出“参数不能为空”异常提示信息；<p>
     * dateStr="2023-09-14 17:26:26.731"，pattern=null，抛出“参数不能为空”异常提示信息；<p>
     * dateStr="2023-09-14 17:26:26.731"，pattern=PATTERN_DATE_TIME_MILLI，返回 2023-09-14。
     */
    public static LocalDate parseLocalDate(String dateStr, String pattern) {
        isNotNull(dateStr, pattern);
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将时间字符串按照相应格式解析成 LocalDateTime类型日期。
     * 该方法需要对参数进行非空判断，若 dateStr或 pattern为空，则抛出参数不能为空异常。
     *
     * @param dateStr 时间字符串，不可为空
     * @param pattern 时间格式字符串，不可为空
     * @return LocalDateTime类型日期
     * @throws DateTimeParseException 时间字符串与时间格式不匹配，无法解析
     *
     * <p>
     * <b>例：</b><br>
     * dateStr=null，pattern=PATTERN_DATE_TIME_MILLI，抛出“参数不能为空”异常提示信息；<p>
     * dateStr="2023-09-14 17:26:26.731"，pattern=null，抛出“参数不能为空”异常提示信息；<p>
     * dateStr="2023-09-14 17:26:26.731"，pattern=PATTERN_DATE_TIME_MILLI，返回 2023-09-14T17:26:26.731。
     */
    public static LocalDateTime parseLocalDateTime(String dateStr, String pattern) {
        isNotNull(dateStr, pattern);
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前日期的开始时间，如 2023-10-18 00:00。
     * 该方法需要对参数进行非空判断，若 dateStr或 pattern为空，则抛出参数不能为空异常。
     *
     * @param dateStr 时间字符串，不可为空
     * @param pattern 时间格式字符串，不可为空
     * @return LocalDateTime类型日期，不包含秒、毫秒
     *
     * <p>
     * <b>例：</b><br>
     * dateStr=null，pattern=PATTERN_DATE_TIME_MILLI，抛出“参数不能为空”异常提示信息；<p>
     * dateStr="2023-09-14 17:26:26.731"，pattern=null，抛出“参数不能为空”异常提示信息；<p>
     * dateStr="2023-09-14 17:26:26.731"，pattern=PATTERN_DATE_TIME_MILLI，返回 2023-09-14T00:00。
     */
    public static LocalDateTime getLocalDateStart(String dateStr, String pattern) {
        isNotNull(dateStr, pattern);
        return LocalDateTime.of(parseLocalDate(dateStr, pattern), LocalTime.MIN);
    }

    /**
     * 获取指定日期 dateTime所在月的第 dayOfMonth天的开始时间，若 dayOfMonth不在指定日期的月内，则返回当前时间。
     * 该方法需要对参数进行非空判断，若 dateTime或 dayOfMonth为空，则抛出参数不能为空异常。
     *
     * @param dateTime   LocalDateTime类型日期，不可为空
     * @param dayOfMonth 指定天数，Integer类型，不可为空
     * @return LocalDateTime类型日期
     *
     * <p>
     * <b>例：</b><br>
     * dateTime=2023-09-15T09:51:26.514，dayOfMonth=null，抛出“参数不能为空”异常提示信息；<p>
     * dateTime=null，dayOfMonth=2，抛出“参数不能为空”异常提示信息；<p>
     * dateTime=2023-09-15T09:51:26.514，dayOfMonth=1，返回 2023-09-01T00:00；<p>
     * dateTime=2023-09-15T09:51:26.514，dayOfMonth=31，返回 2023-09-15T09:52:56.183（当前系统时间）。
     */
    public static LocalDateTime getStartDayOfMonth(LocalDateTime dateTime, Integer dayOfMonth) {
        isNotNull(dateTime, dayOfMonth);
        LocalDate now = dateTime.toLocalDate();
        // 当前日期月份的第一天 2023-0x-01
        LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
        // 当前日期月份的最后一天 2023-0x-xx
        LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate tempDay = firstDay;
        LocalDate localDay;
        // tempDay在最后一天之前 或者 tempDay与 lastDay所属同月
        while (tempDay.isBefore(lastDay) || tempDay.getMonthValue() == lastDay.getMonthValue()) {
            // dayOfMonth 小于等于 tempDay所在月的总天数（dayOfMonth最大不能超过当前月的天数，如 1月 31天，2月 28/29天）
            if (tempDay.lengthOfMonth() >= dayOfMonth) {
                // 设置 localDay为当前月的第 dayOfMonth天
                localDay = tempDay.withDayOfMonth(dayOfMonth);
                /**
                 * 以下两个 if条件来判断 localDay是否在当前月内（条件判断重复？）
                 */
                if (localDay.isAfter(firstDay) || localDay.isEqual(firstDay)) {
                    if (localDay.isEqual(lastDay) || localDay.isBefore(lastDay)) {
                        // 返回 localDay的 00:00
                        return localDay.atStartOfDay();
                    }
                }
            }
            // 不满足则增加一个月，以退出循环
            tempDay = tempDay.plusMonths(1);
        }
        // tempDay不在当前月内，返回当前系统时间
        return LocalDateTime.now();
    }

    /**
     * 比较两个时间的大小。
     * 该方法需要对参数进行非空判断，若 dateTime1或 dateTime2为空，则抛出参数不能为空异常。
     *
     * @param dateTime1 LocalDateTime类型日期，不可为空
     * @param dateTime2 LocalDateTime类型日期，不可为空
     * @return -1，0，1；-1表示 dateTime2大，0表示一样大，1表示 dateTime1大。
     *
     * <p>
     * <b>例：</b><br>
     * time1=null，time2=2023-09-14T17:26:26.731，抛出“参数不能为空”异常提示信息；<p>
     * time1=2023-09-14 18:00:00.000，time2=null，抛出“参数不能为空”异常提示信息；<p>
     * time1=2023-09-14 18:00:00.000，time2=2023-09-14 17:00:00.000，返回 1；<p>
     * time1=2023-09-14 18:00:00.000，time2=2023-09-14 18:00:00.000，返回 0；<p>
     * time1=2023-09-14 17:00:00.000，time2=2023-09-14 18:00:00.000，返回 -1。
     */
    public static int compare(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        isNotNull(dateTime1, dateTime2);
        if (dateTime1.isAfter(dateTime2)) {
            return 1;
        } else if (dateTime1.isBefore(dateTime2)) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 增加指定时间的天数。
     * 该方法需要对参数进行非空判断，若 dateTime为空，则抛出参数不能为空异常。
     *
     * @param dateTime LocalDateTime类型日期，不可为空
     * @param days     要添加的天数，可为负数
     * @return 增加指定天数的 LocalDateTime类型日期
     * @throws DateTimeException 超过 LocalDateTime日期允许范围
     *
     * <p>
     * <b>例：</b><br>
     * dateTime=null，days=10，抛出“参数不能为空”异常提示信息；<p>
     * dateTime=2023-09-15T10:02:49.571，days=60，返回 2023-11-14T10:02:49.571；<p>
     * dateTime=2023-09-15T10:02:49.571，days=-60，返回 2023-07-17T10:02:49.571。
     */
    public static LocalDateTime plusDays(LocalDateTime dateTime, long days) {
        isNotNull(dateTime);
        return dateTime.plusDays(days);
    }

    /**
     * 增加指定时间的周数。
     * 该方法需要对参数进行非空判断，若 dateTime为空，则抛出参数不能为空异常。
     *
     * @param dateTime LocalDateTime类型日期，不可为空
     * @param weeks    要添加的周数，可为负数
     * @return 增加指定周数的 LocalDateTime类型日期
     * @throws DateTimeException 超过 LocalDateTime日期允许范围
     *
     * <p>
     * <b>例：</b><br>
     * dateTime=null，weeks=1，抛出“参数不能为空”异常提示信息；<p>
     * dateTime=2023-09-15T10:57:20.438，days=2，返回 2023-09-29T10:57:20.438；<p>
     * dateTime=2023-09-15T10:57:20.438，days=-2，返回 2023-09-01T10:57:20.438。
     */
    public static LocalDateTime plusWeeks(LocalDateTime dateTime, long weeks) {
        isNotNull(dateTime);
        return dateTime.plusWeeks(weeks);
    }

    /**
     * 增加指定时间的月数。
     * 该方法需要对参数进行非空判断，若 dateTime为空，则抛出参数不能为空异常。
     *
     * @param dateTime LocalDateTime类型日期，不可为空
     * @param months   要添加的月数，可为负数
     * @return 增加指定月数的 LocalDateTime类型日期
     * @throws DateTimeException 超过 LocalDateTime日期允许范围
     *
     * <p>
     * <b>例：</b><br>
     * dateTime=null，months=2，抛出“参数不能为空”异常提示信息；<p>
     * dateTime=2023-09-15T10:53:55.881，months=3，返回 2023-12-15T10:53:55.881；<p>
     * dateTime=2023-09-15T10:53:55.881，months=-3，返回 2023-06-15T10:53:55.881。
     */
    public static LocalDateTime plusMonths(LocalDateTime dateTime, long months) {
        isNotNull(dateTime);
        return dateTime.plusMonths(months);
    }

    /**
     * 获取两个 LocalDateTime类型日期的持续时间 Duration对象。
     * 该方法需要对参数进行非空判断，若 dateTime1或 dateTime2为空，则抛出参数不能为空异常。
     *
     * @param dateTime1 LocalDateTime类型日期，不可为空
     * @param dateTime2 LocalDateTime类型日期，不可为空
     * @return 两个日期间的 Duration对象
     *
     * <p>
     * <b>例：</b><br>
     * dateTime1=null，dateTime2=2023-09-18T10:21:26.725，抛出“参数不能为空”异常提示信息；<p>
     * dateTime1=2023-09-15T10:21:26.725，dateTime2=null，抛出“参数不能为空”异常提示信息；<p>
     * dateTime1=2023-09-15T10:21:26.725，dateTime2=2023-09-18T10:21:26.725，返回 Duration对象。
     */
    public static Duration between(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        isNotNull(dateTime1, dateTime2);
        return Duration.between(dateTime1, dateTime2);
    }

    /**
     * 计算两个日期的相差天数。
     * 该方法需要对参数进行非空判断，若 dateTime1或 dateTime2为空，则抛出参数不能为空异常。
     *
     * @param dateTime1 LocalDateTime类型日期，不可为空
     * @param dateTime2 LocalDateTime类型日期，不可为空
     * @return 两个日期的相差天数，可为负数
     *
     * <p>
     * <b>例：</b><br>
     * dateTime1=null，dateTime2=2023-09-18T10:21:26.725，抛出“参数不能为空”异常提示信息；<p>
     * dateTime1=2023-09-18T10:21:26.725，dateTime2=null，抛出“参数不能为空”异常提示信息；<p>
     * dateTime1=2023-09-15T10:21:26.725，dateTime2=2023-09-18T10:21:26.725，返回 3；<p>
     * dateTime1=2023-09-15T10:21:26.725，dateTime2=2023-09-12T10:21:26.725，返回 -3；<p>
     * dateTime1=2023-09-15T10:21:26.725，dateTime2=2023-09-15T10:21:26.000，返回 0。
     */
    public static Long betweenDays(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return between(dateTime1, dateTime2).toDays();
    }

    private static void isNotNull(Object... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                throw new CommonException(CommonError.NotNull.getMessage());
            }
        }
    }

}
