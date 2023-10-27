package com.amway.commerce.calculation;

import com.amway.commerce.exception.CommonException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * @author: Jason.Hu
 * @date: 2023-09-19
 * @desc:
 */
public class MoneyUtilTest {

    @Test
    public void testFormatWithPattern() {
        // normal：正数
        Assert.assertEquals("￡1,234,567,891.01", MoneyUtil.format(new BigDecimal("1234567891.012345"), MoneyUtil.POUND_PATTERN));
        Assert.assertEquals("￡1,234,567,891.00", MoneyUtil.format(new BigDecimal("1234567891"), MoneyUtil.POUND_PATTERN));
        Assert.assertEquals("€1,234,567,891.01", MoneyUtil.format(new BigDecimal("1234567891.012345"), MoneyUtil.EURO_PATTERN));
        Assert.assertEquals("￥1,234,567,891.01", MoneyUtil.format(new BigDecimal("1234567891.012345"), MoneyUtil.RMB_PATTERN));
        Assert.assertEquals("$1,234,567,891.01", MoneyUtil.format(new BigDecimal("1234567891.012345"), MoneyUtil.DOLLAR_PATTERN));
        Assert.assertEquals("$99.90", MoneyUtil.format(new BigDecimal("99.9"), MoneyUtil.DOLLAR_PATTERN));

        // boundary：0，自定义匹配模式，极限值
        // 0
        Assert.assertEquals("￡0.00", MoneyUtil.format(new BigDecimal("0"), MoneyUtil.POUND_PATTERN));
        Assert.assertEquals("€0.00", MoneyUtil.format(new BigDecimal("0.000000000000"), MoneyUtil.EURO_PATTERN));
        Assert.assertEquals("€0.01", MoneyUtil.format(new BigDecimal("0.010000000000"), MoneyUtil.EURO_PATTERN));
        Assert.assertEquals("￥0.00", MoneyUtil.format(new BigDecimal("-0.0"), MoneyUtil.RMB_PATTERN));
        // 自定义匹配模式
        Assert.assertEquals("$1,2345,6789.00", MoneyUtil.format(new BigDecimal("123456789.00020"), "$#,###,####,####.00"));
        Assert.assertEquals("$1,2345,6789,0000,0000,0000,0000,0000.00", MoneyUtil.format(new BigDecimal("123456789E20"), "$#,###,####,####.00"));
        Assert.assertEquals("$12,3456,7890.00", MoneyUtil.format(new BigDecimal("1234567890.00020"), "$#,###,####,####.00"));
        Assert.assertEquals("$123,4567,8901.00", MoneyUtil.format(new BigDecimal("12345678901.00020"), "$#,###,####,####.00"));
        // 极限值
        Assert.assertEquals("$99,999,999,999,999,999,999,999,999.00", MoneyUtil.format(new BigDecimal("99999999999999999999999999"), MoneyUtil.DOLLAR_PATTERN));
        Assert.assertEquals("-$99,999,999,999,999,999,999,999,999.00", MoneyUtil.format(new BigDecimal("-99999999999999999999999999"), MoneyUtil.DOLLAR_PATTERN));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{MoneyUtil.format(new BigDecimal("123"), null);});
        Assert.assertThrows(CommonException.class, ()->{MoneyUtil.format(null, MoneyUtil.DOLLAR_PATTERN);});
        Assert.assertThrows(CommonException.class, ()->{MoneyUtil.format(null, null);});

    }

    @Test
    public void testFormatWithDefaultPattern() {
        // normal：正数
        Assert.assertEquals("￥1,234,567,891.01", MoneyUtil.format(new BigDecimal("1234567891.012345")));
        Assert.assertEquals("￥1,234,567,891.00", MoneyUtil.format(new BigDecimal("1234567891")));
        Assert.assertEquals("￥1,234,567,891.01", MoneyUtil.format(new BigDecimal("1234567891.012345")));
        Assert.assertEquals("￥1,234,567,891.01", MoneyUtil.format(new BigDecimal("1234567891.012345")));
        Assert.assertEquals("￥1,234,567,891.01", MoneyUtil.format(new BigDecimal("1234567891.012345")));

        // boundary：0，极限值
        // 0
        Assert.assertEquals("￥0.00", MoneyUtil.format(new BigDecimal("0")));
        Assert.assertEquals("￥0.00", MoneyUtil.format(new BigDecimal("0.000000000000")));
        Assert.assertEquals("￥0.01", MoneyUtil.format(new BigDecimal("0.010000000000")));
        Assert.assertEquals("￥0.00", MoneyUtil.format(new BigDecimal("0.0")));
        // 极限值
        Assert.assertEquals("￥99,999,999,999,999,999,999,999,999.00", MoneyUtil.format(new BigDecimal("99999999999999999999999999")));
        Assert.assertEquals("￥99.90", MoneyUtil.format(new BigDecimal("99.9")));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{MoneyUtil.format(null);});

    }

    @Test
    public void testParseWithPattern() throws ParseException {
        // normal：正数
        Assert.assertEquals(new BigDecimal("1234567891.01"), MoneyUtil.parse("￡1,234,567,891.01", MoneyUtil.POUND_PATTERN));
        Assert.assertEquals(new BigDecimal("1234567891"), MoneyUtil.parse("￡1,234,567,891", MoneyUtil.POUND_PATTERN));
        Assert.assertEquals(new BigDecimal("1234567891.01"), MoneyUtil.parse("€1,234,567,891.01", MoneyUtil.EURO_PATTERN));
        Assert.assertEquals(new BigDecimal("1234567891.01"), MoneyUtil.parse("￥1,234,567,891.01", MoneyUtil.RMB_PATTERN));
        Assert.assertEquals(new BigDecimal("1234567891.01"), MoneyUtil.parse("$1,234,567,891.01", MoneyUtil.DOLLAR_PATTERN));
        Assert.assertEquals(new BigDecimal("99.9"), MoneyUtil.parse("$99.90", MoneyUtil.DOLLAR_PATTERN));

        // boundary：0，自定义匹配模式，极限值
        // 0
        Assert.assertEquals(new BigDecimal("0"), MoneyUtil.parse("￡0.00", MoneyUtil.POUND_PATTERN));
        Assert.assertEquals(new BigDecimal("0"), MoneyUtil.parse("€0.00", MoneyUtil.EURO_PATTERN));
        Assert.assertEquals(new BigDecimal("0.01"), MoneyUtil.parse("€0.01", MoneyUtil.EURO_PATTERN));
        Assert.assertEquals(new BigDecimal("0"), MoneyUtil.parse("￥0.00", MoneyUtil.RMB_PATTERN));
        // 自定义匹配模式
        Assert.assertEquals(new BigDecimal("123456789"), MoneyUtil.parse("$1,2345,6789.00", "$#,###,####,####.00"));
        Assert.assertEquals(new BigDecimal("1234567890"), MoneyUtil.parse("$12,3456,7890.00", "$#,###,####,####.00"));
        Assert.assertEquals(new BigDecimal("12345678901.01"), MoneyUtil.parse("$123,4567,8901.01", "$#,###,####,####.00"));
        // 极限值
        Assert.assertEquals(new BigDecimal("1.0E+26"), MoneyUtil.parse("$99,999,999,999,999,999,999,999,999.00", MoneyUtil.DOLLAR_PATTERN));
        Assert.assertEquals(new BigDecimal("0"), MoneyUtil.parse("$0.00", MoneyUtil.DOLLAR_PATTERN));

        // exception：参数为null，非法参数
        // 参数为null
        Assert.assertThrows(CommonException.class, ()->{MoneyUtil.parse("123", null);});
        Assert.assertThrows(CommonException.class, ()->{MoneyUtil.parse("null", null);});
        Assert.assertThrows(CommonException.class, ()->{MoneyUtil.parse(null, MoneyUtil.DOLLAR_PATTERN);});
        Assert.assertThrows(CommonException.class, ()->{MoneyUtil.parse(null, null);});
        // 非法参数
        Assert.assertThrows(ParseException.class, ()->{MoneyUtil.parse("123", "null");});
        Assert.assertThrows(ParseException.class, ()->{MoneyUtil.parse("null", MoneyUtil.DOLLAR_PATTERN);});
        Assert.assertThrows(ParseException.class, ()->{MoneyUtil.parse("$99.90", MoneyUtil.RMB_PATTERN);});
    }

    @Test
    public void testParseWithDefaultPattern() throws ParseException {
        // normal：正数
        Assert.assertEquals(new BigDecimal("1234567891.01"), MoneyUtil.parse("￥1,234,567,891.01"));
        Assert.assertEquals(new BigDecimal("1234567891"), MoneyUtil.parse("￥1,234,567,891"));
        Assert.assertEquals(new BigDecimal("1234567891.01"), MoneyUtil.parse("￥1,234,567,891.01"));
        Assert.assertEquals(new BigDecimal("1234567891.01"), MoneyUtil.parse("￥1,234,567,891.01"));
        Assert.assertEquals(new BigDecimal("1234567891.01"), MoneyUtil.parse("￥1,234,567,891.01"));
        Assert.assertEquals(new BigDecimal("99.9"), MoneyUtil.parse("￥99.90"));

        // boundary：0，极限值
        // 0
        Assert.assertEquals(new BigDecimal("0"), MoneyUtil.parse("￥0.00"));
        Assert.assertEquals(new BigDecimal("0"), MoneyUtil.parse("￥0.00"));
        Assert.assertEquals(new BigDecimal("0.01"), MoneyUtil.parse("￥0.01"));
        Assert.assertEquals(new BigDecimal("0"), MoneyUtil.parse("￥0.00"));
        // 极限值
        Assert.assertEquals(new BigDecimal("1.0E+26"), MoneyUtil.parse("￥99,999,999,999,999,999,999,999,999.00"));
        Assert.assertEquals(new BigDecimal("-1.0E+26"), MoneyUtil.parse("-￥99,999,999,999,999,999,999,999,999.00"));

        // exception：参数为null，非法参数
        // 参数为null
        Assert.assertThrows(CommonException.class, ()->{MoneyUtil.parse(null);});
        // 非法参数
        Assert.assertThrows(ParseException.class, ()->{MoneyUtil.parse("123");});
        Assert.assertThrows(ParseException.class, ()->{MoneyUtil.parse("$123");});
        Assert.assertThrows(ParseException.class, ()->{MoneyUtil.parse("null");});
    }
}