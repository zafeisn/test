package com.amway.commerce.datetime;

import com.amway.commerce.exception.CommonException;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * @author: Jason.Hu
 * @date: 2023-09-19
 */
public class DateTimeUtilTest {

    @Test
    public void testFormatOfDate() {
        // normal：当前时间、指定日期，不同类型的时间转换格式
        Date[] dates_01 = {new Date(), new Date(200), new Date(-200)};
        for (Date date : dates_01) {
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_TIME_MILLI).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_MILLI));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_TIME).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_COMPACT).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_COMPACT));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_CHINA_YYYY_MM).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_CHINA_YYYY_MM));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_CHINA).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_CHINA));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_TIME_T).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_T));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_YY_MM).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_YY_MM));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_BIAS).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_BIAS));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_TIME_COMPACT).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_COMPACT));
        }

        // boundary：0，极限值，特殊情况（小时格式 HH和hh），自定义Pattern
        Date[] dates_02 = {new Date(Long.MAX_VALUE), new Date(Long.MIN_VALUE), new Date(0), new Date()};
        for (Date date :dates_02) {
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_TIME_MILLI).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_MILLI));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_TIME).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_COMPACT).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_COMPACT));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_CHINA_YYYY_MM).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_CHINA_YYYY_MM));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_CHINA).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_CHINA));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_TIME_T).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_T));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_YY_MM).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_YY_MM));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_BIAS).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_BIAS));
            Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_TIME_COMPACT).format(date), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_COMPACT));
            Assert.assertEquals(new SimpleDateFormat("").format(date), DateTimeUtil.format(date, ""));
            Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(date), DateTimeUtil.format(date, "yyyy-MM-dd hh:mm:ss.SSS"));
            Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd,hh:mm:ss.SSS").format(date), DateTimeUtil.format(date, "yyyy-MM-dd,hh:mm:ss.SSS"));
            Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd，hh:mm:ss.SSS").format(date), DateTimeUtil.format(date, "yyyy-MM-dd，hh:mm:ss.SSS"));
            Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd/hh:mm:ss.SSS").format(date), DateTimeUtil.format(date, "yyyy-MM-dd/hh:mm:ss.SSS"));
            Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd$hh:mm:ss.SSS").format(date), DateTimeUtil.format(date, "yyyy-MM-dd$hh:mm:ss.SSS"));
            Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd%hh:mm:ss.SSS").format(date), DateTimeUtil.format(date, "yyyy-MM-dd%hh:mm:ss.SSS"));
        }

        // exception：参数为null，非法参数
        // 参数为null
        Date date = null;
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_MILLI);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.format(new Date(), null);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.format(date, null);});
        // 非法参数
        Assert.assertThrows(IllegalArgumentException.class, ()->{DateTimeUtil.format(new Date(), "no pattern");});

    }

    /**
     *
     */
    @Test
    public void testFormatOfLocalDateTime() {
        // normal：当前时间，指定日期，带时区的日期
        LocalDateTime[] dates_01 = {LocalDateTime.now(), LocalDateTime.of(2000, 10, 1, 23, 3, 0, 333),
                LocalDateTime.now(ZoneId.of("Europe/Paris"))};
        for (LocalDateTime date : dates_01) {
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_MILLI)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_MILLI));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_COMPACT)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_COMPACT));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_CHINA_YYYY_MM)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_CHINA_YYYY_MM));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_CHINA)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_CHINA));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_T)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_T));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_YY_MM)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_YY_MM));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_BIAS)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_BIAS));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_COMPACT)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_COMPACT));
        }

        // boundary：0，极限值，特殊情况（小时格式 HH和hh），自定义Pattern
        LocalDateTime[] dates_02 = {LocalDateTime.MAX, LocalDateTime.MIN, LocalDateTime.of(0, 1, 31, 0, 0 ,0 ,0)};
        for (LocalDateTime date : dates_02) {
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_MILLI)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_MILLI));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_COMPACT)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_COMPACT));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_CHINA_YYYY_MM)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_CHINA_YYYY_MM));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_CHINA)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_CHINA));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_T)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_T));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_YY_MM)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_YY_MM));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_BIAS)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_BIAS));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_COMPACT)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_COMPACT));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern("")), DateTimeUtil.format(date, ""));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS")), DateTimeUtil.format(date, "yyyy-MM-dd hh:mm:ss.SSS"));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd,hh:mm:ss.SSS")), DateTimeUtil.format(date, "yyyy-MM-dd,hh:mm:ss.SSS"));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd，HH:mm:ss.SSS")), DateTimeUtil.format(date, "yyyy-MM-dd，HH:mm:ss.SSS"));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss.SSS")), DateTimeUtil.format(date, "yyyy-MM-dd/HH:mm:ss.SSS"));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd$hh:mm:ss.SSS")), DateTimeUtil.format(date, "yyyy-MM-dd$hh:mm:ss.SSS"));
            Assert.assertEquals(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd%hh:mm:ss.SSS")), DateTimeUtil.format(date, "yyyy-MM-dd%hh:mm:ss.SSS"));
        }

        // exception：参数为null，非法参数
        // 参数为null
        LocalDateTime dateTime = null;
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.format(dateTime, DateTimeUtil.PATTERN_DATE_TIME_MILLI);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.format(LocalDateTime.now(), null);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.format(dateTime, null);});
        // 非法参数
        Assert.assertThrows(IllegalArgumentException.class, ()->{DateTimeUtil.format(LocalDateTime.now(), "no pattern");});

    }

    @Test
    public void testFormatOfLong() {
        // normal：正负数
        Long[] dates_01 = {2000L, -2000L, 10L, -10L};
        for (Long date : dates_01) {
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_MILLI)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_MILLI));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_COMPACT)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_COMPACT));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_CHINA_YYYY_MM)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_CHINA_YYYY_MM));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_CHINA)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_CHINA));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_T)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_T));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_YY_MM)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_YY_MM));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_BIAS)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_BIAS));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_COMPACT)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_COMPACT));
        }

        // boundary：0、极限值，特殊情况（小时格式 HH和hh），自定义Pattern
        Long[] dates_02 = {Long.MAX_VALUE, Long.MIN_VALUE, 0L};
        for (Long date : dates_02) {
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_MILLI)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_MILLI));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_COMPACT)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_COMPACT));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_CHINA_YYYY_MM)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_CHINA_YYYY_MM));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_CHINA)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_CHINA));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_T)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_T));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_YY_MM)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_YY_MM));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_BIAS)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_BIAS));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_COMPACT)), DateTimeUtil.format(date, DateTimeUtil.PATTERN_DATE_TIME_COMPACT));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("")), DateTimeUtil.format(date, ""));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS")), DateTimeUtil.format(date, "yyyy-MM-dd hh:mm:ss.SSS"));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd,hh:mm:ss.SSS")), DateTimeUtil.format(date, "yyyy-MM-dd,hh:mm:ss.SSS"));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd，HH:mm:ss.SSS")), DateTimeUtil.format(date, "yyyy-MM-dd，HH:mm:ss.SSS"));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss.SSS")), DateTimeUtil.format(date, "yyyy-MM-dd/HH:mm:ss.SSS"));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd$hh:mm:ss.SSS")), DateTimeUtil.format(date, "yyyy-MM-dd$hh:mm:ss.SSS"));
            Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd%hh:mm:ss.SSS")), DateTimeUtil.format(date, "yyyy-MM-dd%hh:mm:ss.SSS"));
        }

        // exception：参数为null、非法参数
        // 参数为null
        Long time = null;
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.format(time, DateTimeUtil.PATTERN_DATE_TIME_MILLI);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.format(10L, null);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.format(time, null);});
        // 非法参数
        Assert.assertThrows(IllegalArgumentException.class, ()->{DateTimeUtil.format(10L, "no pattern");});

    }

    @Test
    public void testToDate() {
        // normal：当前时间、指定日期、带时区的
        LocalDateTime[] dates_01 = {LocalDateTime.now(), LocalDateTime.of(2000, 10, 1, 23, 3, 0, 333),
                LocalDateTime.now(ZoneId.of("Europe/Paris"))};
        for (LocalDateTime dateTime : dates_01) {
            Assert.assertEquals(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()), DateTimeUtil.toDate(dateTime));
        }

        // boundary：极限值、0
        LocalDateTime[] dates_02 = {LocalDateTime.of(1990,1,1,0,0,0,0),
                LocalDateTime.of(0,1,1,0,0,0,0),
                LocalDateTime.of(292278993, 12, 31, 23, 59 ,59 ,999)};
        for (LocalDateTime dateTime : dates_02) {
            Assert.assertEquals(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()), DateTimeUtil.toDate(dateTime));
        }

        // exception：参数为null、非法参数
        // 参数为null
        LocalDateTime dateTime = null;
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.toDate(dateTime);});
        // 非法参数（超过Date日期范围）
        Assert.assertThrows(IllegalArgumentException.class, ()->{DateTimeUtil.toDate(LocalDateTime.MAX);});
        Assert.assertThrows(IllegalArgumentException.class, ()->{DateTimeUtil.toDate(LocalDateTime.MIN);});

    }

    @Test
    public void testToLocalDateTime() {
        // normal：当前时间、指定日期
        Date[] dates_01 = {new Date(), new Date(200), new Date(-200)};
        for (Date date : dates_01) {
            Assert.assertEquals(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), DateTimeUtil.toLocalDateTime(date));
        }

        // boundary：0、极限值
        Date[] dates_02 = {new Date(Long.MAX_VALUE), new Date(Long.MIN_VALUE), new Date(0)};
        for (Date date :dates_02) {
            Assert.assertEquals(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), DateTimeUtil.toLocalDateTime(date));
        }

        // exception：参数为null
        Date date = null;
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.toLocalDateTime(date);});
    }

    @Test
    public void testParseDate() throws ParseException {
        // normal：不同的Pattern格式
        Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE).parse("2000-02-10"), DateTimeUtil.parseDate("2000-02-10", DateTimeUtil.PATTERN_DATE));
        Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_COMPACT).parse("20000210"), DateTimeUtil.parseDate("20000210", DateTimeUtil.PATTERN_DATE_COMPACT));
        Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_CHINA).parse("2000年02月10日"), DateTimeUtil.parseDate("2000年02月10日", DateTimeUtil.PATTERN_DATE_CHINA));
        Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_BIAS).parse("2000/02/10"), DateTimeUtil.parseDate("2000/02/10", DateTimeUtil.PATTERN_DATE_BIAS));
        Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_TIME).parse("20:08:06"), DateTimeUtil.parseDate("20:08:06", DateTimeUtil.PATTERN_TIME));
        Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_TIME_COMPACT).parse("200806"), DateTimeUtil.parseDate("200806", DateTimeUtil.PATTERN_TIME_COMPACT));
        Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_TIME).parse("2000-02-10 20:08:06"), DateTimeUtil.parseDate("2000-02-10 20:08:06", DateTimeUtil.PATTERN_DATE_TIME));
        Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_TIME_COMPACT).parse("20000210200806"), DateTimeUtil.parseDate("20000210200806", DateTimeUtil.PATTERN_DATE_TIME_COMPACT));
        Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_TIME_MILLI).parse("2000-02-10 20:08:06.333"), DateTimeUtil.parseDate("2000-02-10 20:08:06.333", DateTimeUtil.PATTERN_DATE_TIME_MILLI));
        Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_TIME_T).parse("2000-02-10T20:08:06.333"), DateTimeUtil.parseDate("2000-02-10T20:08:06.333", DateTimeUtil.PATTERN_DATE_TIME_T));
        Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_YYYY_MM).parse("200002"), DateTimeUtil.parseDate("200002", DateTimeUtil.PATTERN_YYYY_MM));
        Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_CHINA_YYYY_MM).parse("2000年02月"), DateTimeUtil.parseDate("2000年02月", DateTimeUtil.PATTERN_CHINA_YYYY_MM));

        // boundary：0、极限值
        Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_TIME_MILLI).parse("1970-01-01 00:00:00.000"), DateTimeUtil.parseDate("1970-01-01 00:00:00.000", DateTimeUtil.PATTERN_DATE_TIME_MILLI));
        Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_TIME_COMPACT).parse("0000-00-00 00:00:00.000"), DateTimeUtil.parseDate("0000-00-00 00:00:00.000", DateTimeUtil.PATTERN_DATE_TIME_COMPACT));
        Assert.assertEquals(new SimpleDateFormat(DateTimeUtil.PATTERN_DATE_TIME_MILLI).parse("8099-12-31 23:59:59.999"), DateTimeUtil.parseDate("8099-12-31 23:59:59.999", DateTimeUtil.PATTERN_DATE_TIME_MILLI));

        // exception：参数为null、日期格式不匹配、非法参数
        // 参数为null
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.parseDate(null, DateTimeUtil.PATTERN_DATE_TIME_MILLI);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.parseDate("200002", null);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.parseDate(null, null);});
        // 日期格式不匹配
        Assert.assertThrows(ParseException.class, ()->{DateTimeUtil.parseDate("", DateTimeUtil.PATTERN_DATE_TIME_MILLI);});
        Assert.assertThrows(ParseException.class, ()->{DateTimeUtil.parseDate("null", DateTimeUtil.PATTERN_DATE_TIME_MILLI);});
        Assert.assertThrows(ParseException.class, ()->{DateTimeUtil.parseDate("200002", "");});
        // 非法参数
        Assert.assertThrows(IllegalArgumentException.class, ()->{DateTimeUtil.parseDate("200002", "no pattern");});

    }

    @Test
    public void testParseLocalDate() {
        // normal：不同的Pattern
        Assert.assertEquals(LocalDate.parse("2000-02-10",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE)), DateTimeUtil.parseLocalDate("2000-02-10", DateTimeUtil.PATTERN_DATE));
        Assert.assertEquals(LocalDate.parse("20000210",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_COMPACT)), DateTimeUtil.parseLocalDate("20000210", DateTimeUtil.PATTERN_DATE_COMPACT));
        Assert.assertEquals(LocalDate.parse("2000年02月10日",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_CHINA)), DateTimeUtil.parseLocalDate("2000年02月10日", DateTimeUtil.PATTERN_DATE_CHINA));
        Assert.assertEquals(LocalDate.parse("2000/02/10",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_BIAS)), DateTimeUtil.parseLocalDate("2000/02/10", DateTimeUtil.PATTERN_DATE_BIAS));
        Assert.assertEquals(LocalDate.parse("2000-02-10 20:08:06",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME)), DateTimeUtil.parseLocalDate("2000-02-10 20:08:06", DateTimeUtil.PATTERN_DATE_TIME));
        Assert.assertEquals(LocalDate.parse("20000210200806",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_COMPACT)), DateTimeUtil.parseLocalDate("20000210200806", DateTimeUtil.PATTERN_DATE_TIME_COMPACT));
        Assert.assertEquals(LocalDate.parse("2000-02-10 20:08:06.333",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_MILLI)), DateTimeUtil.parseLocalDate("2000-02-10 20:08:06.333", DateTimeUtil.PATTERN_DATE_TIME_MILLI));
        Assert.assertEquals(LocalDate.parse("2000-02-10T20:08:06",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_T)), DateTimeUtil.parseLocalDate("2000-02-10T20:08:06", DateTimeUtil.PATTERN_DATE_TIME_T));

        // boundary：0，极限值
        Assert.assertEquals(LocalDate.parse("0001-01-01",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE)), DateTimeUtil.parseLocalDate("0001-01-01", DateTimeUtil.PATTERN_DATE));
        Assert.assertEquals(LocalDate.parse("0001-01-01T00:00:00",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_T)), DateTimeUtil.parseLocalDate("0001-01-01T00:00:00", DateTimeUtil.PATTERN_DATE_TIME_T));
        Assert.assertEquals(LocalDate.parse("0001-01-01 00:00:00.000",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_MILLI)), DateTimeUtil.parseLocalDate("0001-01-01 00:00:00.000", DateTimeUtil.PATTERN_DATE_TIME_MILLI));
        Assert.assertEquals(LocalDate.parse("9999-12-31T23:59:59",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_T)), DateTimeUtil.parseLocalDate("9999-12-31T23:59:59", DateTimeUtil.PATTERN_DATE_TIME_T));
        Assert.assertEquals(LocalDate.parse("9999-12-31 23:59:59.999",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_MILLI)), DateTimeUtil.parseLocalDate("9999-12-31 23:59:59.999", DateTimeUtil.PATTERN_DATE_TIME_MILLI));
        Assert.assertEquals(LocalDate.parse("9999年12月31日",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_CHINA)), DateTimeUtil.parseLocalDate("9999年12月31日", DateTimeUtil.PATTERN_DATE_CHINA));

        // exception：参数为null，日期格式不匹配，非法参数
        // 参数为null
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.parseLocalDate(null, DateTimeUtil.PATTERN_DATE_TIME_COMPACT);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.parseLocalDate("2000-02-10", null);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.parseLocalDate(null, null);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.parseLocalDate("", null);});
        // 日期格式不匹配
        Assert.assertThrows(DateTimeParseException.class, ()->{DateTimeUtil.parseLocalDate("2000-02-10", DateTimeUtil.PATTERN_DATE_CHINA);});
        Assert.assertThrows(DateTimeParseException.class, ()->{DateTimeUtil.parseLocalDate("", DateTimeUtil.PATTERN_DATE_TIME_COMPACT);});
        Assert.assertThrows(DateTimeParseException.class, ()->{DateTimeUtil.parseLocalDate("9999-12-31", "");});
        Assert.assertThrows(DateTimeParseException.class, ()->{DateTimeUtil.parseLocalDate("", "");});
        Assert.assertThrows(DateTimeParseException.class, ()->{DateTimeUtil.parseLocalDate("null", DateTimeUtil.PATTERN_DATE);});
        // 非法参数
        Assert.assertThrows(IllegalArgumentException.class, ()->{DateTimeUtil.parseLocalDate("null", "no pattern");});
        Assert.assertThrows(IllegalArgumentException.class, ()->{DateTimeUtil.parseLocalDate("9999-12-31", "no pattern");});
    }

    @Test
    public void testParseLocalDateTime() {
        // normal：不同的Pattern
        Assert.assertEquals(LocalDateTime.parse("2000-02-10 20:08:06",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME)), DateTimeUtil.parseLocalDateTime("2000-02-10 20:08:06", DateTimeUtil.PATTERN_DATE_TIME));
        Assert.assertEquals(LocalDateTime.parse("20000210200806",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_COMPACT)), DateTimeUtil.parseLocalDateTime("20000210200806", DateTimeUtil.PATTERN_DATE_TIME_COMPACT));
        Assert.assertEquals(LocalDateTime.parse("2000-02-10 20:08:06.333",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_MILLI)), DateTimeUtil.parseLocalDateTime("2000-02-10 20:08:06.333", DateTimeUtil.PATTERN_DATE_TIME_MILLI));
        Assert.assertEquals(LocalDateTime.parse("2000-02-10T20:08:06",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_T)), DateTimeUtil.parseLocalDateTime("2000-02-10T20:08:06", DateTimeUtil.PATTERN_DATE_TIME_T));

        // boundary：0，极限值
        Assert.assertEquals(LocalDateTime.parse("0001-01-01T00:00:00",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_T)), DateTimeUtil.parseLocalDateTime("0001-01-01T00:00:00", DateTimeUtil.PATTERN_DATE_TIME_T));
        Assert.assertEquals(LocalDateTime.parse("0001-01-01 00:00:00.000",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_MILLI)), DateTimeUtil.parseLocalDateTime("0001-01-01 00:00:00.000", DateTimeUtil.PATTERN_DATE_TIME_MILLI));
        Assert.assertEquals(LocalDateTime.parse("9999-12-31T23:59:59",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_T)), DateTimeUtil.parseLocalDateTime("9999-12-31T23:59:59", DateTimeUtil.PATTERN_DATE_TIME_T));
        Assert.assertEquals(LocalDateTime.parse("9999-12-31 23:59:59.999",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_MILLI)), DateTimeUtil.parseLocalDateTime("9999-12-31 23:59:59.999", DateTimeUtil.PATTERN_DATE_TIME_MILLI));

        // exception：参数为null，Pattern模式不匹配，非法参数
        // 参数为null
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.parseLocalDateTime(null, DateTimeUtil.PATTERN_DATE_TIME_COMPACT);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.parseLocalDateTime("2000-02-10 20:08:06.333", null);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.parseLocalDateTime(null, null);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.parseLocalDateTime("", null);});
        // Pattern模式不匹配
        Assert.assertThrows(DateTimeParseException.class, ()->{DateTimeUtil.parseLocalDateTime("2000-02-10 20:08:06.333", DateTimeUtil.PATTERN_DATE);});
        Assert.assertThrows(DateTimeParseException.class, ()->{DateTimeUtil.parseLocalDateTime("", DateTimeUtil.PATTERN_DATE_TIME_COMPACT);});
        Assert.assertThrows(DateTimeParseException.class, ()->{DateTimeUtil.parseLocalDateTime("9999-12-31 23:59:59.999", "");});
        Assert.assertThrows(DateTimeParseException.class, ()->{DateTimeUtil.parseLocalDateTime("", "");});
        // 非法参数
        Assert.assertThrows(IllegalArgumentException.class, ()->{DateTimeUtil.parseLocalDateTime("null", "no pattern");});
        Assert.assertThrows(IllegalArgumentException.class, ()->{DateTimeUtil.parseLocalDateTime("9999-12-31 23:59:59.999", "no pattern");});

    }

    @Test
    public void testGetLocalDateStart() {
        // normal：不同的Pattern
        Assert.assertEquals(LocalDateTime.of(LocalDate.parse("2000-02-10 20:08:06", DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME)), LocalTime.MIN), DateTimeUtil.getLocalDateStart("2000-02-10 20:08:06", DateTimeUtil.PATTERN_DATE_TIME));
        Assert.assertEquals(LocalDateTime.of(LocalDate.parse("20000210200806",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_COMPACT)), LocalTime.MIN), DateTimeUtil.getLocalDateStart("20000210200806", DateTimeUtil.PATTERN_DATE_TIME_COMPACT));
        Assert.assertEquals(LocalDateTime.of(LocalDate.parse("2000-02-10 20:08:06.333",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_MILLI)),LocalTime.MIN), DateTimeUtil.getLocalDateStart("2000-02-10 20:08:06.333", DateTimeUtil.PATTERN_DATE_TIME_MILLI));
        Assert.assertEquals(LocalDateTime.of(LocalDate.parse("2000-02-10T20:08:06",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_T)),LocalTime.MIN), DateTimeUtil.getLocalDateStart("2000-02-10T20:08:06", DateTimeUtil.PATTERN_DATE_TIME_T));

        // boundary：0，极限值
        Assert.assertEquals(LocalDateTime.of(LocalDate.parse("0001-01-01T00:00:00",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_T)),LocalTime.MIN), DateTimeUtil.getLocalDateStart("0001-01-01T00:00:00", DateTimeUtil.PATTERN_DATE_TIME_T));
        Assert.assertEquals(LocalDateTime.of(LocalDate.parse("0001-01-01 00:00:00.000",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_MILLI)),LocalTime.MIN), DateTimeUtil.getLocalDateStart("0001-01-01 00:00:00.000", DateTimeUtil.PATTERN_DATE_TIME_MILLI));
        Assert.assertEquals(LocalDateTime.of(LocalDate.parse("9999-12-31T23:59:59",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_T)),LocalTime.MIN), DateTimeUtil.getLocalDateStart("9999-12-31T23:59:59", DateTimeUtil.PATTERN_DATE_TIME_T));
        Assert.assertEquals(LocalDateTime.of(LocalDate.parse("9999-12-31 23:59:59.999",DateTimeFormatter.ofPattern(DateTimeUtil.PATTERN_DATE_TIME_MILLI)),LocalTime.MIN), DateTimeUtil.getLocalDateStart("9999-12-31 23:59:59.999", DateTimeUtil.PATTERN_DATE_TIME_MILLI));

        // exception：参数为null，Pattern模式不匹配，非法参数
        // 参数为null
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.getLocalDateStart(null, DateTimeUtil.PATTERN_DATE_TIME_COMPACT);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.getLocalDateStart("2000-02-10 20:08:06.333", null);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.getLocalDateStart(null, null);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.getLocalDateStart("", null);});
        // Pattern模式不匹配
        Assert.assertThrows(DateTimeParseException.class, ()->{DateTimeUtil.getLocalDateStart("", DateTimeUtil.PATTERN_DATE_TIME_COMPACT);});
        Assert.assertThrows(DateTimeParseException.class, ()->{DateTimeUtil.getLocalDateStart("2000-02-10 20:08:06.333", DateTimeUtil.PATTERN_DATE_CHINA);});
        Assert.assertThrows(DateTimeParseException.class, ()->{DateTimeUtil.getLocalDateStart("9999-12-31 23:59:59.999", "");});
        Assert.assertThrows(DateTimeParseException.class, ()->{DateTimeUtil.getLocalDateStart("", "");});
        // 非法参数
        Assert.assertThrows(IllegalArgumentException.class, ()->{DateTimeUtil.getLocalDateStart("null", "no pattern");});
        Assert.assertThrows(IllegalArgumentException.class, ()->{DateTimeUtil.getLocalDateStart("9999-12-31 23:59:59.999", "no pattern");});

    }

    @Test
    public void testGetStartDayOfMonth() {
        // normal：指定日期内、属于当前月的天数
        Assert.assertEquals(LocalDateTime.of(2000,12,2,0,0), DateTimeUtil.getStartDayOfMonth(LocalDateTime.of(2000,12,21,23,12,16,999), 2));
        Assert.assertEquals(LocalDateTime.of(2000,12,5,0,0), DateTimeUtil.getStartDayOfMonth(LocalDateTime.of(2000,12,21,23,12,16,999), 5));
        Assert.assertEquals(LocalDateTime.of(2000,12,15,0,0), DateTimeUtil.getStartDayOfMonth(LocalDateTime.of(2000,12,21,23,12,16,999), 15));
        Assert.assertEquals(LocalDateTime.of(2000,12,24,0,0), DateTimeUtil.getStartDayOfMonth(LocalDateTime.of(2000,12,21,23,12,16,999), 24));

        // boundary：闰年的月初、月末
        Assert.assertEquals(LocalDateTime.of(2000,12,1,0,0), DateTimeUtil.getStartDayOfMonth(LocalDateTime.of(2000,12,21,23,12,16,999), 1));
        Assert.assertEquals(LocalDateTime.of(2000,12,31,0,0), DateTimeUtil.getStartDayOfMonth(LocalDateTime.of(2000,12,21,23,12,16,999), 31));
        Assert.assertEquals(LocalDateTime.of(2000,2,1,0,0), DateTimeUtil.getStartDayOfMonth(LocalDateTime.of(2000,2,28,23,12,16,999), 1));
        Assert.assertEquals(LocalDateTime.of(2000,2,28,0,0), DateTimeUtil.getStartDayOfMonth(LocalDateTime.of(2000,2,28,23,12,16,999), 28));
        Assert.assertEquals(LocalDateTime.of(2000,2,29,0,0), DateTimeUtil.getStartDayOfMonth(LocalDateTime.of(2000,2,28,23,12,16,999), 29));
        Assert.assertEquals(LocalDateTime.of(2000,2,1,0,0), DateTimeUtil.getStartDayOfMonth(LocalDateTime.of(2000,2,1,23,12,16,999), 1));
        Assert.assertEquals(LocalDateTime.of(2000,2,29,0,0), DateTimeUtil.getStartDayOfMonth(LocalDateTime.of(2000,2,1,23,12,16,999), 29));
        Assert.assertNotEquals(LocalDateTime.of(2000,2,1,0,0), DateTimeUtil.getStartDayOfMonth(LocalDateTime.of(2000,2,1,23,12,16,999), 30));
        Assert.assertNotEquals(LocalDateTime.of(2000,2,29,0,0), DateTimeUtil.getStartDayOfMonth(LocalDateTime.of(2000,2,29,23,12,16,999), 31));

        // exception：参数为null，0天
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.getStartDayOfMonth(null, 12);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.getStartDayOfMonth(LocalDateTime.now(), null);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.getStartDayOfMonth(null, null);});
        Assert.assertThrows(DateTimeException.class, ()->{DateTimeUtil.getStartDayOfMonth(LocalDateTime.of(2000,12,1,0,0), 0);});

    }

    @Test
    public void testCompare() {
        // normal：正常时间的比较大小
        Assert.assertEquals(0, DateTimeUtil.compare(LocalDateTime.of(2000,3,30, 23,32,50,999),LocalDateTime.of(2000,3,30, 23,32,50,999)));
        Assert.assertEquals(1, DateTimeUtil.compare(LocalDateTime.of(2010,3,30, 23,59,59,999),LocalDateTime.of(2000,3,30, 23,59,59,999)));
        Assert.assertEquals(-1, DateTimeUtil.compare(LocalDateTime.of(2000,3,30, 23,59,59,999),LocalDateTime.of(2000,3,31, 2,10,00,100)));
        Assert.assertEquals(1, DateTimeUtil.compare(LocalDateTime.of(2023,3,30, 23,59,59,999),LocalDateTime.of(2000,3,31, 2,10,00,100)));

        // boundary：跨1毫秒的时间比较，极限值
        // 跨1毫秒的时间比较
        Assert.assertEquals(-1, DateTimeUtil.compare(LocalDateTime.of(2000,3,30, 23,59,59,999),LocalDateTime.of(2000,3,31, 00,00,00,000)));
        Assert.assertEquals(1, DateTimeUtil.compare(LocalDateTime.of(2000,2,29, 00,00,00,000),LocalDateTime.of(2000,2,28, 23,59,59,99999999)));
        // 极限值
        Assert.assertEquals(1, DateTimeUtil.compare(LocalDateTime.MAX, LocalDateTime.MIN));
        Assert.assertEquals(0, DateTimeUtil.compare(LocalDateTime.MIN, LocalDateTime.MIN));
        Assert.assertEquals(-1, DateTimeUtil.compare(LocalDateTime.MIN, LocalDateTime.MAX));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.compare(null, LocalDateTime.MIN);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.compare(LocalDateTime.MIN, null);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.compare(null, null);});

    }

    @Test
    public void testPlusDays() {
        // normal：正常时间添加天数
        Assert.assertEquals(LocalDateTime.of(2000, 3, 23, 23, 32, 50, 999), DateTimeUtil.plusDays(LocalDateTime.of(2000, 3, 20, 23, 32, 50, 999), 3));
        Assert.assertEquals(LocalDateTime.of(2000, 3, 17, 23, 32, 50, 999), DateTimeUtil.plusDays(LocalDateTime.of(2000, 3, 20, 23, 32, 50, 999), -3));
        Assert.assertEquals(LocalDateTime.of(2000, 3, 28, 23, 32, 50, 999), DateTimeUtil.plusDays(LocalDateTime.of(2000, 3, 20, 23, 32, 50, 999), 8));

        // boundary：跨月、跨天、跨年、极限值
        Assert.assertEquals(LocalDateTime.of(2000, 4, 2, 23, 59, 59, 999), DateTimeUtil.plusDays(LocalDateTime.of(2000, 3, 30, 23, 59, 59, 999), 3));
        Assert.assertEquals(LocalDateTime.of(2000, 3, 30, 23, 59, 59, 999), DateTimeUtil.plusDays(LocalDateTime.of(2000, 3, 30, 23, 59, 59, 999), 0));
        Assert.assertEquals(LocalDateTime.of(2000, 3, 27, 23, 59, 59, 999), DateTimeUtil.plusDays(LocalDateTime.of(2000, 3, 30, 23, 59, 59, 999), -3));
        Assert.assertEquals(LocalDateTime.of(2000, 3, 3, 00, 00, 00, 000), DateTimeUtil.plusDays(LocalDateTime.of(2000, 2, 29, 00, 00, 00, 000), 3));
        Assert.assertEquals(LocalDateTime.of(2001, 1, 1, 00, 00, 00, 000), DateTimeUtil.plusDays(LocalDateTime.of(2000, 12, 29, 00, 00, 00, 000), 3));
        Assert.assertNotEquals(LocalDateTime.now(), DateTimeUtil.plusDays(LocalDateTime.MAX, -1));
        Assert.assertNotEquals(LocalDateTime.now(), DateTimeUtil.plusDays(LocalDateTime.MIN, 1));

        // exception：参数为null、超过极限值
        Assert.assertThrows(CommonException.class, () -> {DateTimeUtil.plusDays(null, 3);});
        Assert.assertThrows(DateTimeException.class, () -> {DateTimeUtil.plusDays(LocalDateTime.MAX, 3);});
        Assert.assertThrows(DateTimeException.class, () -> {DateTimeUtil.plusDays(LocalDateTime.MIN, -3);});

    }

    @Test
    public void testPlusWeeks() {
        // normal：正常时间
        Assert.assertEquals(LocalDateTime.of(2000, 3, 27, 23, 32, 50, 999), DateTimeUtil.plusWeeks(LocalDateTime.of(2000, 3, 20, 23, 32, 50, 999), 1));
        Assert.assertEquals(LocalDateTime.of(2000, 3, 13, 23, 32, 50, 999), DateTimeUtil.plusWeeks(LocalDateTime.of(2000, 3, 20, 23, 32, 50, 999), -1));
        Assert.assertEquals(LocalDateTime.of(2023, 3, 6, 23, 32, 50, 999), DateTimeUtil.plusWeeks(LocalDateTime.of(2023, 3, 20, 23, 32, 50, 999), -2));

        // boundary：跨年、月、日、极限值
        Assert.assertEquals(LocalDateTime.of(2000, 4, 20, 23, 59, 59, 999), DateTimeUtil.plusWeeks(LocalDateTime.of(2000, 3, 30, 23, 59, 59, 999), 3));
        Assert.assertEquals(LocalDateTime.of(2000, 3, 30, 23, 59, 59, 999), DateTimeUtil.plusWeeks(LocalDateTime.of(2000, 3, 30, 23, 59, 59, 999), 0));
        Assert.assertEquals(LocalDateTime.of(2000, 3, 9, 23, 59, 59, 999), DateTimeUtil.plusWeeks(LocalDateTime.of(2000, 3, 30, 23, 59, 59, 999), -3));
        Assert.assertEquals(LocalDateTime.of(2000, 3, 21, 00, 00, 00, 000), DateTimeUtil.plusWeeks(LocalDateTime.of(2000, 2, 29, 00, 00, 00, 000), 3));
        Assert.assertEquals(LocalDateTime.of(2001, 1, 19, 00, 00, 00, 000), DateTimeUtil.plusWeeks(LocalDateTime.of(2000, 12, 29, 00, 00, 00, 000), 3));
        Assert.assertNotEquals(LocalDateTime.now(), DateTimeUtil.plusWeeks(LocalDateTime.MAX, -1));
        Assert.assertNotEquals(LocalDateTime.now(), DateTimeUtil.plusWeeks(LocalDateTime.MIN, 1));

        // exception：参数为null、超过极限值
        Assert.assertThrows(CommonException.class, () -> {DateTimeUtil.plusWeeks(null, 3);});
        Assert.assertThrows(DateTimeException.class, () -> {DateTimeUtil.plusWeeks(LocalDateTime.MAX, 3);});
        Assert.assertThrows(DateTimeException.class, () -> {DateTimeUtil.plusWeeks(LocalDateTime.MIN, -3);});
    }

    @Test
    public void testPlusMonths() {
        // normal：正常时间
        Assert.assertEquals(LocalDateTime.of(2000, 4, 20, 23, 32, 50, 999), DateTimeUtil.plusMonths(LocalDateTime.of(2000, 3, 20, 23, 32, 50, 999), 1));
        Assert.assertEquals(LocalDateTime.of(2000, 2, 20, 23, 32, 50, 999), DateTimeUtil.plusMonths(LocalDateTime.of(2000, 3, 20, 23, 32, 50, 999), -1));
        Assert.assertEquals(LocalDateTime.of(2023, 9, 20, 23, 32, 50, 999), DateTimeUtil.plusMonths(LocalDateTime.of(2023, 3, 20, 23, 32, 50, 999), 6));

        // boundary：跨年、加0月、极限值
        Assert.assertEquals(LocalDateTime.of(2000, 2, 29, 23, 59, 59, 999), DateTimeUtil.plusMonths(LocalDateTime.of(2000, 3, 30, 23, 59, 59, 999), -1));
        Assert.assertEquals(LocalDateTime.of(2000, 3, 30, 23, 59, 59, 999), DateTimeUtil.plusMonths(LocalDateTime.of(2000, 3, 30, 23, 59, 59, 999), 0));
        Assert.assertEquals(LocalDateTime.of(1999, 12, 30, 23, 59, 59, 999), DateTimeUtil.plusMonths(LocalDateTime.of(2000, 3, 30, 23, 59, 59, 999), -3));
        Assert.assertEquals(LocalDateTime.of(2000, 5, 29, 00, 00, 00, 000), DateTimeUtil.plusMonths(LocalDateTime.of(2000, 2, 29, 00, 00, 00, 000), 3));
        Assert.assertEquals(LocalDateTime.of(2001, 3, 29, 00, 00, 00, 000), DateTimeUtil.plusMonths(LocalDateTime.of(2000, 12, 29, 00, 00, 00, 000), 3));
        Assert.assertNotEquals(LocalDateTime.now(), DateTimeUtil.plusMonths(LocalDateTime.MAX, -1));
        Assert.assertNotEquals(LocalDateTime.now(), DateTimeUtil.plusMonths(LocalDateTime.MIN, 1));

        // exception：参数为null、超过极限值
        Assert.assertThrows(CommonException.class, () -> {DateTimeUtil.plusMonths(null, 3);});
        Assert.assertThrows(DateTimeException.class, () -> {DateTimeUtil.plusMonths(LocalDateTime.MAX, 3);});
        Assert.assertThrows(DateTimeException.class, () -> {DateTimeUtil.plusMonths(LocalDateTime.MIN, -3);});
    }

    @Test
    public void testBetween() {
        // normal：正常的时分秒年月日
        LocalDateTime[] dateTime_01 = {LocalDateTime.of(2000,2,3,12,00,00,000), LocalDateTime.of(2000,2,4,12,00,00,000)};
        LocalDateTime[] dateTime_02 = {LocalDateTime.of(2020,2,3,12,00,00,000), LocalDateTime.of(2000,2,3,12,00,00,000)};
        LocalDateTime[] dateTime_03 = {LocalDateTime.of(2000,5,3,12,00,00,000), LocalDateTime.of(2000,2,3,12,00,00,000)};
        LocalDateTime[] dateTime_04 = {LocalDateTime.of(2000,2,3,14,00,00,000), LocalDateTime.of(2000,2,3,12,00,00,000)};
        LocalDateTime[] dateTime_05 = {LocalDateTime.of(2000,2,3,12,20,00,000), LocalDateTime.of(2000,2,3,12,00,00,000)};
        LocalDateTime[] dateTime_06 = {LocalDateTime.of(2030,4,6,12,20,10,100), LocalDateTime.of(2000,2,3,12,00,00,000)};
        Assert.assertEquals(Duration.between(dateTime_01[0], dateTime_01[1]), DateTimeUtil.between(dateTime_01[0], dateTime_01[1]));
        Assert.assertEquals(Duration.between(dateTime_02[0], dateTime_02[1]), DateTimeUtil.between(dateTime_02[0], dateTime_02[1]));
        Assert.assertEquals(Duration.between(dateTime_03[0], dateTime_03[1]), DateTimeUtil.between(dateTime_03[0], dateTime_03[1]));
        Assert.assertEquals(Duration.between(dateTime_04[0], dateTime_04[1]), DateTimeUtil.between(dateTime_04[0], dateTime_04[1]));
        Assert.assertEquals(Duration.between(dateTime_05[0], dateTime_05[1]), DateTimeUtil.between(dateTime_05[0], dateTime_05[1]));
        Assert.assertEquals(Duration.between(dateTime_06[0], dateTime_06[1]), DateTimeUtil.between(dateTime_06[0], dateTime_06[1]));

        // boundary：极限值、闰年
        LocalDateTime[] dateTime_07 = {LocalDateTime.MIN, LocalDateTime.MAX,
                LocalDateTime.of(2000,2,29,12,20,10,100),
                LocalDateTime.of(2000,3,10,12,00,00,000),
                LocalDateTime.of(2001,1,1,00,00,10,100)};
        Assert.assertEquals(Duration.between(dateTime_07[0], dateTime_07[0]), DateTimeUtil.between(dateTime_07[0], dateTime_07[0]));
        Assert.assertEquals(Duration.between(dateTime_07[0], dateTime_07[1]), DateTimeUtil.between(dateTime_07[0], dateTime_07[1]));
        Assert.assertEquals(Duration.between(dateTime_07[2], dateTime_07[1]), DateTimeUtil.between(dateTime_07[2], dateTime_07[1]));
        Assert.assertEquals(Duration.between(dateTime_07[0], dateTime_07[2]), DateTimeUtil.between(dateTime_07[0], dateTime_07[2]));
        Assert.assertEquals(Duration.between(dateTime_07[3], dateTime_07[2]), DateTimeUtil.between(dateTime_07[3], dateTime_07[2]));
        Assert.assertEquals(Duration.between(dateTime_07[4], dateTime_07[2]), DateTimeUtil.between(dateTime_07[4], dateTime_07[2]));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.between(LocalDateTime.MIN, null);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.between(null, LocalDateTime.MIN);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.between(null, null);});

    }

    @Test
    public void testBetweenDays() {
        // normal：正常的时分秒年月日
        LocalDateTime[] dateTime_01 = {LocalDateTime.of(2000,2,3,12,00,00,000),
                LocalDateTime.of(2000,2,4,12,00,00,000)};
        LocalDateTime[] dateTime_02 = {LocalDateTime.of(2001,2,3,12,00,00,000),
                LocalDateTime.of(2000,2,3,12,00,00,000)};
        LocalDateTime[] dateTime_03 = {LocalDateTime.of(2000,5,3,12,00,00,000),
                LocalDateTime.of(2000,2,3,12,00,00,000)};
        LocalDateTime[] dateTime_04 = {LocalDateTime.of(2000,2,3,14,00,00,000),
                LocalDateTime.of(2000,2,3,12,00,00,000)};
        LocalDateTime[] dateTime_05 = {LocalDateTime.of(2000,2,3,12,20,00,000),
                LocalDateTime.of(2000,2,3,12,00,00,000)};
        LocalDateTime[] dateTime_06 = {LocalDateTime.of(2000,2,6,12,20,10,100),
                LocalDateTime.of(2000,2,3,12,00,00,000)};
        Assert.assertEquals(Long.valueOf(1), DateTimeUtil.betweenDays(dateTime_01[0], dateTime_01[1]));
        Assert.assertEquals(Long.valueOf(-366), DateTimeUtil.betweenDays(dateTime_02[0], dateTime_02[1]));
        Assert.assertEquals(Long.valueOf(-90), DateTimeUtil.betweenDays(dateTime_03[0], dateTime_03[1]));
        Assert.assertEquals(Long.valueOf(0), DateTimeUtil.betweenDays(dateTime_04[0], dateTime_04[1]));
        Assert.assertEquals(Long.valueOf(0), DateTimeUtil.betweenDays(dateTime_05[0], dateTime_05[1]));
        Assert.assertEquals(Long.valueOf(-3), DateTimeUtil.betweenDays(dateTime_06[0], dateTime_06[1]));

        // boundary：极限值、闰年
        LocalDateTime[] dateTime_07 = {LocalDateTime.MIN, LocalDateTime.MAX,
                LocalDateTime.of(2000,2,28,12,00,00,000),
                LocalDateTime.of(2000,3,1,12,00,00,000),
                LocalDateTime.of(2000,12,31,00,00,00,000),
                LocalDateTime.of(2000,12,31,00,00,00,100),
                LocalDateTime.of(2000,12,31,00,00,01,000),
                LocalDateTime.of(2001,1,1,00,00,00,000),
                LocalDateTime.of(2001,1,1,00,00,00,001)};
        Assert.assertEquals(Long.valueOf(Duration.between(dateTime_07[0], dateTime_07[1]).toDays()), DateTimeUtil.betweenDays(dateTime_07[0], dateTime_07[1]));
        Assert.assertEquals(Long.valueOf(Duration.between(dateTime_07[2], dateTime_07[1]).toDays()), DateTimeUtil.betweenDays(dateTime_07[2], dateTime_07[1]));
        Assert.assertEquals(Long.valueOf(Duration.between(dateTime_07[0], dateTime_07[2]).toDays()), DateTimeUtil.betweenDays(dateTime_07[0], dateTime_07[2]));
        Assert.assertEquals(Long.valueOf(-2), DateTimeUtil.betweenDays(dateTime_07[3], dateTime_07[2]));
        Assert.assertEquals(Long.valueOf(0), DateTimeUtil.betweenDays(dateTime_07[4], dateTime_07[5]));
        Assert.assertEquals(Long.valueOf(0), DateTimeUtil.betweenDays(dateTime_07[4], dateTime_07[6]));
        Assert.assertEquals(Long.valueOf(1), DateTimeUtil.betweenDays(dateTime_07[4], dateTime_07[7]));
        Assert.assertEquals(Long.valueOf(1), DateTimeUtil.betweenDays(dateTime_07[4], dateTime_07[8]));
        Assert.assertEquals(Long.valueOf(0), DateTimeUtil.betweenDays(dateTime_07[5], dateTime_07[7]));
        Assert.assertEquals(Long.valueOf(0), DateTimeUtil.betweenDays(dateTime_07[5], dateTime_07[8]));

        // exception：参数为null
        LocalDateTime dateTime01 = null;
        LocalDateTime dateTime02 = null;
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.betweenDays(LocalDateTime.MIN, null);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.betweenDays(null, LocalDateTime.MIN);});
        Assert.assertThrows(CommonException.class, ()->{DateTimeUtil.betweenDays(dateTime01, dateTime02);});
    }
}
