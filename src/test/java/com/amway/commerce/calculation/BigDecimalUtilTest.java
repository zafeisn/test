package com.amway.commerce.calculation;

import com.amway.commerce.exception.CommonException;
import com.amway.commerce.exception.CommonException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author: Jason.Hu
 * @date: 2023-09-19
 */
public class BigDecimalUtilTest {

    @Test
    public void testIsGreaterThan() {
        // normal：正数、负数
        // 正数和正数
        Assert.assertTrue(BigDecimalUtil.isGreaterThan(new BigDecimal("12345"), new BigDecimal("1234")));
        Assert.assertFalse(BigDecimalUtil.isGreaterThan(new BigDecimal("123"), new BigDecimal("123.0")));
        Assert.assertTrue(BigDecimalUtil.isGreaterThan(new BigDecimal("123.4"), new BigDecimal("12.3")));
        Assert.assertFalse(BigDecimalUtil.isGreaterThan(new BigDecimal("123.0000"), new BigDecimal("123.0")));
        // 正数和负数
        Assert.assertTrue(BigDecimalUtil.isGreaterThan(new BigDecimal("123"), new BigDecimal("-123")));
        Assert.assertTrue(BigDecimalUtil.isGreaterThan(new BigDecimal("123"), new BigDecimal("-123.0")));
        Assert.assertFalse(BigDecimalUtil.isGreaterThan(new BigDecimal("-1234"), new BigDecimal("123")));
        Assert.assertFalse(BigDecimalUtil.isGreaterThan(new BigDecimal("-1234"), new BigDecimal("12.3")));
        Assert.assertTrue(BigDecimalUtil.isGreaterThan(new BigDecimal(234), new BigDecimal(-1234)));
        Assert.assertTrue(BigDecimalUtil.isGreaterThan(new BigDecimal("123.0000"), new BigDecimal("-123.0")));
        // 负数和负数
        Assert.assertFalse(BigDecimalUtil.isGreaterThan(new BigDecimal("-1234"), new BigDecimal("-123")));
        Assert.assertFalse(BigDecimalUtil.isGreaterThan(new BigDecimal("-1234"), new BigDecimal("-123")));
        Assert.assertFalse(BigDecimalUtil.isGreaterThan(new BigDecimal(-1234), new BigDecimal(-1234)));
        Assert.assertFalse(BigDecimalUtil.isGreaterThan(new BigDecimal("-123.0000"), new BigDecimal("-123.0")));

        // boundary：0，0.000000000000000000001，极限值
        // 0和 0
        Assert.assertFalse(BigDecimalUtil.isGreaterThan(new BigDecimal("0"), new BigDecimal("0")));
        Assert.assertFalse(BigDecimalUtil.isGreaterThan(new BigDecimal("0"), BigDecimal.ZERO));
        Assert.assertFalse(BigDecimalUtil.isGreaterThan(new BigDecimal("-0"), new BigDecimal("0")));
        Assert.assertFalse(BigDecimalUtil.isGreaterThan(new BigDecimal("0"), new BigDecimal("0.0000000000000000000")));
        Assert.assertFalse(BigDecimalUtil.isGreaterThan(new BigDecimal("-0.00000000"), new BigDecimal("0.0000000000000000000")));
        // 0和 0.000000000000000000001
        Assert.assertTrue(BigDecimalUtil.isGreaterThan(new BigDecimal("0.000000000000000000001"), new BigDecimal("0.0000000000000000000")));
        // 0和极限值
        Assert.assertTrue(BigDecimalUtil.isGreaterThan(new BigDecimal("10E300"), new BigDecimal("0.0000000000000000000")));
        // 0.000000000000000000001和极限值
        Assert.assertFalse(BigDecimalUtil.isGreaterThan(new BigDecimal("-10E300"), new BigDecimal("0.0000000000000000000")));
        // 极限值和极限值
        Assert.assertTrue(BigDecimalUtil.isGreaterThan(new BigDecimal("10E300"), new BigDecimal("12345")));
        Assert.assertFalse(BigDecimalUtil.isGreaterThan(new BigDecimal("-10E300"), new BigDecimal("-12345")));
        Assert.assertTrue(BigDecimalUtil.isGreaterThan(new BigDecimal("10E300"), new BigDecimal("-10E300")));

        // exception：参数为 null
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.isGreaterThan(null, new BigDecimal("22"));});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.isGreaterThan(new BigDecimal("22"), null);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.isGreaterThan(null, null);});
    }

    @Test
    public void testIsGreaterEqual() {
        // normal：正数、负数
        // 正数和正数
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal("12345"), new BigDecimal("1234")));
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal("123"), new BigDecimal("123.0")));
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal("123.4"), new BigDecimal("12.3")));
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal("123.0000"), new BigDecimal("123.0")));
        // 正数和负数
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal("123"), new BigDecimal("-123")));
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal("123"), new BigDecimal("-123.0")));
        Assert.assertFalse(BigDecimalUtil.isGreaterEqual(new BigDecimal("-1234"), new BigDecimal("123")));
        Assert.assertFalse(BigDecimalUtil.isGreaterEqual(new BigDecimal("-1234"), new BigDecimal("12.3")));
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal(234), new BigDecimal(-1234)));
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal("123.0000"), new BigDecimal("-123.0")));
        // 负数和负数
        Assert.assertFalse(BigDecimalUtil.isGreaterEqual(new BigDecimal("-1234"), new BigDecimal("-123")));
        Assert.assertFalse(BigDecimalUtil.isGreaterEqual(new BigDecimal("-1234"), new BigDecimal("-123")));
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal(-1234), new BigDecimal(-1234)));
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal(-123.4), new BigDecimal(-1234.4)));
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal("-123.0000"), new BigDecimal("-123.0")));

        // boundary：0，0.000000000000000000001，极限值
        // 0和 0
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal("0"), new BigDecimal("0")));
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal("0"), BigDecimal.ZERO));
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal("-0"), new BigDecimal("0")));
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal("0"), new BigDecimal("0.0000000000000000000")));
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal("-0.00000000"), new BigDecimal("0.0000000000000000000")));
        // 0和 0.000000000000000000001
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal("0.000000000000000000001"), new BigDecimal("0.0000000000000000000")));
        // 0和极限值
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal("10E300"), new BigDecimal("0.0000000000000000000")));
        Assert.assertFalse(BigDecimalUtil.isGreaterEqual(new BigDecimal("-10E300"), new BigDecimal("0.0000000000000000000")));
        // 0.000000000000000000001和极限值
        Assert.assertFalse(BigDecimalUtil.isGreaterEqual(new BigDecimal("-10E300"), new BigDecimal("0.0000000000000000001")));
        // 极限值和极限值
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal("10E300"), new BigDecimal("12345890")));
        Assert.assertFalse(BigDecimalUtil.isGreaterEqual(new BigDecimal("-10E300"), new BigDecimal("-123457890")));
        Assert.assertTrue(BigDecimalUtil.isGreaterEqual(new BigDecimal("10E300"), new BigDecimal("-10E300")));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.isGreaterEqual(null, new BigDecimal("22"));});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.isGreaterEqual(new BigDecimal("22"), null);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.isGreaterEqual(null, null);});
    }

    @Test
    public void testIsEqual() {
        // normal：正数、负数
        // 正数和正数
        Assert.assertFalse(BigDecimalUtil.isEqual(new BigDecimal("12345"), new BigDecimal("1234")));
        Assert.assertTrue(BigDecimalUtil.isEqual(new BigDecimal("123"), new BigDecimal("123.0")));
        Assert.assertFalse(BigDecimalUtil.isEqual(new BigDecimal("123"), new BigDecimal("123.001")));
        Assert.assertFalse(BigDecimalUtil.isEqual(new BigDecimal("123.4"), new BigDecimal("12.3")));
        Assert.assertTrue(BigDecimalUtil.isEqual(new BigDecimal("123.0000"), new BigDecimal("123.0")));
        // 正数和负数
        Assert.assertFalse(BigDecimalUtil.isEqual(new BigDecimal("123"), new BigDecimal("-123")));
        Assert.assertFalse(BigDecimalUtil.isEqual(new BigDecimal("123"), new BigDecimal("-123.0")));
        Assert.assertFalse(BigDecimalUtil.isEqual(new BigDecimal("-1234"), new BigDecimal("123")));
        Assert.assertFalse(BigDecimalUtil.isEqual(new BigDecimal("-1234"), new BigDecimal("12.3")));
        Assert.assertFalse(BigDecimalUtil.isEqual(new BigDecimal(1234.989), new BigDecimal(-1234.989)));
        Assert.assertFalse(BigDecimalUtil.isEqual(new BigDecimal("123.0000"), new BigDecimal("-123.0")));
        // 负数和负数
        Assert.assertFalse(BigDecimalUtil.isEqual(new BigDecimal("-1234"), new BigDecimal("-123")));
        Assert.assertFalse(BigDecimalUtil.isEqual(new BigDecimal("-1234"), new BigDecimal("-123")));
        Assert.assertTrue(BigDecimalUtil.isEqual(new BigDecimal(-1234.989), new BigDecimal(-1234.989)));
        Assert.assertTrue(BigDecimalUtil.isEqual(new BigDecimal("-123.0000"), new BigDecimal("-123.0")));

        // boundary：0，0.000000000000000000001，极限值
        // 0和 0
        Assert.assertTrue(BigDecimalUtil.isEqual(new BigDecimal("0"), new BigDecimal("0")));
        Assert.assertTrue(BigDecimalUtil.isEqual(new BigDecimal("0"), BigDecimal.ZERO));
        Assert.assertTrue(BigDecimalUtil.isEqual(new BigDecimal("-0"), new BigDecimal("0")));
        Assert.assertTrue(BigDecimalUtil.isEqual(new BigDecimal("0"), new BigDecimal("0.0000000000000000000")));
        Assert.assertTrue(BigDecimalUtil.isEqual(new BigDecimal("-0.00000000"), new BigDecimal("0.0000000000000000000")));
        // 0和 0.000000000000000000001
        Assert.assertFalse(BigDecimalUtil.isEqual(new BigDecimal("0.000000000000000000001"), new BigDecimal("0.0000000000000000000")));
        // 0和极限值
        Assert.assertFalse(BigDecimalUtil.isEqual(new BigDecimal("-10E300"), new BigDecimal("0.0000000000000000000")));
        // 0.000000000000000000001和极限值
        Assert.assertFalse(BigDecimalUtil.isEqual(new BigDecimal("10E300"), new BigDecimal("0.0000000000000000000")));
        // 极限值和极限值
        Assert.assertFalse(BigDecimalUtil.isEqual(new BigDecimal("10E300"), new BigDecimal("123457890")));
        Assert.assertFalse(BigDecimalUtil.isEqual(new BigDecimal("-10E300"), new BigDecimal("-123457890")));
        Assert.assertFalse(BigDecimalUtil.isEqual(new BigDecimal("10E300"), new BigDecimal("-10E300")));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.isEqual(null, new BigDecimal("22"));});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.isEqual(new BigDecimal("22"), null);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.isEqual(null, null);});
    }

    @Test
    public void testIsLessThan() {
        // normal：正数、负数
        // 正数和正数
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal("12345"), new BigDecimal("1234")));
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal("123"), new BigDecimal("123.0")));
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal("123.4"), new BigDecimal("12.3")));
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal("123.0000"), new BigDecimal("123.0")));
        // 正数和负数
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal("123"), new BigDecimal("-123")));
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal("123"), new BigDecimal("-123.0")));
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal(1234.989), new BigDecimal(-1234.989)));
        Assert.assertTrue(BigDecimalUtil.isLessThan(new BigDecimal("-1234"), new BigDecimal("12.3")));
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal("123.0000"), new BigDecimal("-123.0")));
        // 负数和负数
        Assert.assertTrue(BigDecimalUtil.isLessThan(new BigDecimal("-1234"), new BigDecimal("123")));
        Assert.assertTrue(BigDecimalUtil.isLessThan(new BigDecimal("-1234"), new BigDecimal("-123")));
        Assert.assertTrue(BigDecimalUtil.isLessThan(new BigDecimal("-1234"), new BigDecimal("-123")));
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal(-1234.989), new BigDecimal(-1234.989)));
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal("-123.0000"), new BigDecimal("-123.0")));

        // boundary：0，0.000000000000000000001，极限值
        // 0和 0
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal("0"), new BigDecimal("0")));
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal("0"), BigDecimal.ZERO));
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal("-0"), new BigDecimal("0")));
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal("0"), new BigDecimal("0.0000000000000000000")));
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal("-0.00000000"), new BigDecimal("0.0000000000000000000")));
        // 0和 0.000000000000000000001
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal("0.000000000000000000001"), new BigDecimal("0.0000000000000000000")));
        // 0和极限值
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal("10E300"), new BigDecimal("0.0000000000000000000")));
        Assert.assertTrue(BigDecimalUtil.isLessThan(new BigDecimal("-10E300"), new BigDecimal("0.0000000000000000000")));
        // 0.000000000000000000001和极限值
        Assert.assertTrue(BigDecimalUtil.isLessThan(new BigDecimal("-10E300"), new BigDecimal("0.0000000000000000001")));
        // 极限值和极限值
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal("10E300"), new BigDecimal("1234567890")));
        Assert.assertTrue(BigDecimalUtil.isLessThan(new BigDecimal("-10E300"), new BigDecimal("-1234567890")));
        Assert.assertFalse(BigDecimalUtil.isLessThan(new BigDecimal("10E300"), new BigDecimal("-10E300")));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.isLessThan(null, new BigDecimal("22"));});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.isLessThan(new BigDecimal("22"), null);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.isLessThan(null, null);});
    }

    @Test
    public void testIsLessEqual() {
        // normal：正数、负数
        // 正数和正数
        Assert.assertFalse(BigDecimalUtil.isLessEqual(new BigDecimal("12345"), new BigDecimal("1234")));
        Assert.assertTrue(BigDecimalUtil.isLessEqual(new BigDecimal("123"), new BigDecimal("123.0")));
        Assert.assertFalse(BigDecimalUtil.isLessEqual(new BigDecimal("123.4"), new BigDecimal("12.3")));
        Assert.assertTrue(BigDecimalUtil.isLessEqual(new BigDecimal("123.0000"), new BigDecimal("123.0")));
        // 正数和负数
        Assert.assertFalse(BigDecimalUtil.isLessEqual(new BigDecimal("123"), new BigDecimal("-123")));
        Assert.assertFalse(BigDecimalUtil.isLessEqual(new BigDecimal("123"), new BigDecimal("-123.0")));
        Assert.assertTrue(BigDecimalUtil.isLessEqual(new BigDecimal("-1234"), new BigDecimal("12.3")));
        Assert.assertTrue(BigDecimalUtil.isLessEqual(new BigDecimal("-1234"), new BigDecimal("123")));
        Assert.assertFalse(BigDecimalUtil.isLessEqual(new BigDecimal("123.0000"), new BigDecimal("-123.0")));
        Assert.assertFalse(BigDecimalUtil.isLessEqual(new BigDecimal(1234.989), new BigDecimal(-1234.989)));
        // 负数和负数
        Assert.assertTrue(BigDecimalUtil.isLessEqual(new BigDecimal("-1234"), new BigDecimal("-123")));
        Assert.assertTrue(BigDecimalUtil.isLessEqual(new BigDecimal("-1234"), new BigDecimal("-123")));
        Assert.assertTrue(BigDecimalUtil.isLessEqual(new BigDecimal("-123.0000"), new BigDecimal("-123.0")));
        Assert.assertTrue(BigDecimalUtil.isLessEqual(new BigDecimal(-1234.989), new BigDecimal(-1234.989)));

        // boundary：0，0.000000000000000000001，极限值
        // 0和 0
        Assert.assertTrue(BigDecimalUtil.isLessEqual(new BigDecimal("0"), new BigDecimal("0")));
        Assert.assertTrue(BigDecimalUtil.isLessEqual(new BigDecimal("0"), BigDecimal.ZERO));
        Assert.assertTrue(BigDecimalUtil.isLessEqual(new BigDecimal("-0"), new BigDecimal("0")));
        Assert.assertTrue(BigDecimalUtil.isLessEqual(new BigDecimal("0"), new BigDecimal("0.0000000000000000000")));
        Assert.assertTrue(BigDecimalUtil.isLessEqual(new BigDecimal("-0.00000000"), new BigDecimal("0.0000000000000000000")));
        // 0和 0.000000000000000000001
        Assert.assertFalse(BigDecimalUtil.isLessEqual(new BigDecimal("0.000000000000000000001"), new BigDecimal("0.0000000000000000000")));
        // 0和 极限值
        Assert.assertFalse(BigDecimalUtil.isLessEqual(new BigDecimal("10E300"), new BigDecimal("0.0000000000000000000")));
        Assert.assertTrue(BigDecimalUtil.isLessEqual(new BigDecimal("-10E300"), new BigDecimal("0.0000000000000000000")));
        // 0.000000000000000000001和极限值
        Assert.assertTrue(BigDecimalUtil.isLessEqual(new BigDecimal("-10E300"), new BigDecimal("0.0000000000000000001")));
        // 极限值和极限值
        Assert.assertFalse(BigDecimalUtil.isLessEqual(new BigDecimal("10E300"), new BigDecimal("1234567890")));
        Assert.assertTrue(BigDecimalUtil.isLessEqual(new BigDecimal("-10E300"), new BigDecimal("-1234567890")));
        Assert.assertFalse(BigDecimalUtil.isLessEqual(new BigDecimal("10E300"), new BigDecimal("-10E300")));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.isLessEqual(null, new BigDecimal("22"));});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.isLessEqual(new BigDecimal("22"), null);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.isLessEqual(null, null);});
    }

    @Test
    public void testBitComparison() {
        // normal：正数、负数
        // 正数和正数
        Assert.assertEquals(-1, BigDecimalUtil.bitComparison(new BigDecimal("1234567890"), new BigDecimal("9876543210"), 3));
        Assert.assertEquals(0, BigDecimalUtil.bitComparison(new BigDecimal("1234567890"), new BigDecimal("9876543210"), 4));
        Assert.assertEquals(-1, BigDecimalUtil.bitComparison(new BigDecimal("1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), 3));
        Assert.assertEquals(0, BigDecimalUtil.bitComparison(new BigDecimal("1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), 4));
        Assert.assertEquals(1, BigDecimalUtil.bitComparison(new BigDecimal("1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), 13));
        Assert.assertEquals(0, BigDecimalUtil.bitComparison(new BigDecimal("1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), 14));
        // 正数和负数
        Assert.assertEquals(-1, BigDecimalUtil.bitComparison(new BigDecimal("-1234567890"), new BigDecimal("9876543210"), 3));
        Assert.assertEquals(0, BigDecimalUtil.bitComparison(new BigDecimal("-1234567890"), new BigDecimal("9876543210"), 4));
        Assert.assertEquals(0, BigDecimalUtil.bitComparison(new BigDecimal("-1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), 4));
        Assert.assertEquals(0, BigDecimalUtil.bitComparison(new BigDecimal("-1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), 14));
        // 负数和负数
        Assert.assertEquals(-1, BigDecimalUtil.bitComparison(new BigDecimal("-1234567890"), new BigDecimal("-9876543210"), 3));
        Assert.assertEquals(0, BigDecimalUtil.bitComparison(new BigDecimal("-1234567890"), new BigDecimal("-9876543210"), 4));
        Assert.assertEquals(-1, BigDecimalUtil.bitComparison(new BigDecimal("-1234567890.9876543210"), new BigDecimal("-9876543210.1234567890"), 3));
        Assert.assertEquals(1, BigDecimalUtil.bitComparison(new BigDecimal("-1234567890.9876543210"), new BigDecimal("-9876543210.1234567890"), 13));

        // boundary：数字字符串的首位，末尾
        // 首位
        Assert.assertEquals(0, BigDecimalUtil.bitComparison(new BigDecimal("0.0"), new BigDecimal("0"), 0));
        Assert.assertEquals(-1, BigDecimalUtil.bitComparison(new BigDecimal("1234567890"), new BigDecimal("9876543210"), 0));
        Assert.assertEquals(-1, BigDecimalUtil.bitComparison(new BigDecimal("-1234567890"), new BigDecimal("9876543210"), 0));
        Assert.assertEquals(-1, BigDecimalUtil.bitComparison(new BigDecimal("-1234567890"), new BigDecimal("-9876543210"), 0));
        Assert.assertEquals(-1, BigDecimalUtil.bitComparison(new BigDecimal("1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), 0));
        Assert.assertEquals(-1, BigDecimalUtil.bitComparison(new BigDecimal("-1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), 0));
        Assert.assertEquals(-1, BigDecimalUtil.bitComparison(new BigDecimal("1234E32"), new BigDecimal("987E32"), 0));
        Assert.assertEquals(1, BigDecimalUtil.bitComparison(new BigDecimal("1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), 10));
        Assert.assertEquals(1, BigDecimalUtil.bitComparison(new BigDecimal("-1234567890.9876543210"), new BigDecimal("-9876543210.1234567890"), 10));
        // 末尾
        Assert.assertEquals(0, BigDecimalUtil.bitComparison(new BigDecimal("0.0"), new BigDecimal("0.0"), 1));
        Assert.assertEquals(0, BigDecimalUtil.bitComparison(new BigDecimal("0.0"), new BigDecimal("-0.0"), 1));
        Assert.assertEquals(0, BigDecimalUtil.bitComparison(new BigDecimal("1234567890"), new BigDecimal("9876543210"), 9));
        Assert.assertEquals(0, BigDecimalUtil.bitComparison(new BigDecimal("-1234567890"), new BigDecimal("9876543210"), 9));
        Assert.assertEquals(0, BigDecimalUtil.bitComparison(new BigDecimal("-1234567890"), new BigDecimal("-9876543210"), 9));
        Assert.assertEquals(0, BigDecimalUtil.bitComparison(new BigDecimal("1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), 9));
        Assert.assertEquals(0, BigDecimalUtil.bitComparison(new BigDecimal("1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), 19));
        Assert.assertEquals(0, BigDecimalUtil.bitComparison(new BigDecimal("-1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), 19));
        Assert.assertEquals(0, BigDecimalUtil.bitComparison(new BigDecimal("-1234567890.9876543210"), new BigDecimal("-9876543210.1234567890"), 9));

        // exception：参数为 null，超过数字字符串长度，位数为负数
        // 参数为 null
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(null, new BigDecimal("22"), 1);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("22"), null, 1);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(null, null, 1);});
        // 超过数字字符串长度
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("1234567890"), new BigDecimal("9876543210"), -1);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("1234567890"), new BigDecimal("9876543210"), 10);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("-1234567890"), new BigDecimal("9876543210"), 10);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("-1234567890"), new BigDecimal("-9876543210"), 10);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), 20);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), 20);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("-1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), 20);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("-1234567890.9876543210"), new BigDecimal("-9876543210.1234567890"), 20);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("-1234567890.9876543210"), new BigDecimal("-9876543210.1234567890"), 20);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("-1234567890.9876543210"), new BigDecimal("-987654320"), 20);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("-1234567890"), new BigDecimal("-9876543210.1234567890"), 20);});
        // 位数为负数
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("-1234567890"), new BigDecimal("9876543210"), -1);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("-1234567890"), new BigDecimal("-9876543210"), -6);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), -1);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), -9);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("-1234567890.9876543210"), new BigDecimal("9876543210.1234567890"), -100);});
        Assert.assertThrows(CommonException.class, () -> {BigDecimalUtil.bitComparison(new BigDecimal("-1234567890.9876543210"), new BigDecimal("-9876543210.1234567890"), -1);});
    }

    @Test
    public void testDoubleToBigDecimalWithScale() {
        // normal：正数，负数，scale
        // 正数，scale大于 0
        Assert.assertEquals(new BigDecimal("123.00"), BigDecimalUtil.toBigDecimal(new Double("123"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("123.012"), BigDecimalUtil.toBigDecimal(new Double("123.012345"), 3, RoundingMode.HALF_UP));
        // 正数，scale小于 0
        Assert.assertEquals(new BigDecimal("1E+2"), BigDecimalUtil.toBigDecimal(new Double("123"), -2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0E+5"), BigDecimalUtil.toBigDecimal(new Double("123.0123456789"), -5, RoundingMode.HALF_UP));
        // 正数，scale等于 0
        Assert.assertEquals(new BigDecimal("123"), BigDecimalUtil.toBigDecimal(new Double("123.0123456789"), 0, RoundingMode.HALF_UP));
        // 负数，scale大于 0
        Assert.assertEquals(new BigDecimal("-123.00"), BigDecimalUtil.toBigDecimal(new Double("-123"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-123.012"), BigDecimalUtil.toBigDecimal(new Double("-123.012345"), 3, RoundingMode.HALF_UP));
        // 负数，scale小于 0
        Assert.assertEquals(new BigDecimal("-1.2300E+7"), BigDecimalUtil.toBigDecimal(new Double("-123E5"), -3, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0E+3"), BigDecimalUtil.toBigDecimal(new Double("-123"), -3, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-1E+3"), BigDecimalUtil.toBigDecimal(new Double("-567"), -3, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-6E+2"), BigDecimalUtil.toBigDecimal(new Double("-567"), -2, RoundingMode.HALF_UP));
        // 负数，scale等于 0
        Assert.assertEquals(new BigDecimal("-567"), BigDecimalUtil.toBigDecimal(new Double("-567"), 0, RoundingMode.HALF_UP));

        // boundary：0，scale，有进位
        // 0，scale大于 0
        Assert.assertEquals(new BigDecimal("0.0"), BigDecimalUtil.toBigDecimal(new Double("0.00000000"), 1, RoundingMode.HALF_DOWN));
        Assert.assertEquals(new BigDecimal("0.00"), BigDecimalUtil.toBigDecimal(new Double("0.00000000"), 2, RoundingMode.HALF_DOWN));
        // 0，scale小于 0
        Assert.assertEquals(new BigDecimal("0E+1"), BigDecimalUtil.toBigDecimal(new Double("0.00000000"), -1, RoundingMode.HALF_DOWN));
        Assert.assertEquals(new BigDecimal("0E+2"), BigDecimalUtil.toBigDecimal(new Double("0.00000000"), -2, RoundingMode.HALF_DOWN));
        // 0，scale等于 0
        Assert.assertEquals(new BigDecimal("0"), BigDecimalUtil.toBigDecimal(new Double("0"), 0, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0"), BigDecimalUtil.toBigDecimal(new Double("0.000"), 0, RoundingMode.HALF_UP));
        // 有进位
        Assert.assertEquals(new BigDecimal("1234567890.01235"), BigDecimalUtil.toBigDecimal(new Double("1234567890.012345"), 5, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-1234567890.01235"), BigDecimalUtil.toBigDecimal(new Double("-1234567890.012345"), 5, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-1234567890.01235"), BigDecimalUtil.toBigDecimal(new Double("-1234567890.012345"), 5, RoundingMode.FLOOR));
        Assert.assertEquals(new BigDecimal("-1234567891"), BigDecimalUtil.toBigDecimal(new Double("-1234567890.9"), 0, RoundingMode.UP));

        // exception：参数为 null
        Assert.assertThrows(CommonException.class, ()->{BigDecimalUtil.toBigDecimal(null, 1, RoundingMode.HALF_UP);});
        Assert.assertThrows(CommonException.class, ()->{BigDecimalUtil.toBigDecimal(null, null, RoundingMode.HALF_UP);});
        Assert.assertThrows(CommonException.class, ()->{BigDecimalUtil.toBigDecimal(null, null, null);});
        Assert.assertThrows(CommonException.class, ()->{BigDecimalUtil.toBigDecimal(new Double("123"), null, RoundingMode.HALF_UP);});
        Assert.assertThrows(CommonException.class, ()->{BigDecimalUtil.toBigDecimal(new Double("123"), null, null);});
    }

    @Test
    public void testDoubleToBigDecimalWithDefaultScale() {
        // normal：正数、负数
        // 正数
        Assert.assertEquals(new BigDecimal("123.00"), BigDecimalUtil.toBigDecimal(new Double("123"), null));
        Assert.assertEquals(new BigDecimal("123.01"), BigDecimalUtil.toBigDecimal(new Double("123.012345"), null));
        Assert.assertEquals(new BigDecimal("12300000.00"), BigDecimalUtil.toBigDecimal(new Double("123E5"), null));
        // 负数
        Assert.assertEquals(new BigDecimal("-123.00"), BigDecimalUtil.toBigDecimal(new Double("-123"), null));
        Assert.assertEquals(new BigDecimal("-123.12"), BigDecimalUtil.toBigDecimal(new Double("-123.123"), null));
        Assert.assertEquals(new BigDecimal("-12300000.00"), BigDecimalUtil.toBigDecimal(new Double("-123E5"), null));

        // boundary：0，有进位
        // 0
        Assert.assertEquals(new BigDecimal("0.00"), BigDecimalUtil.toBigDecimal(new Double("0"), null));
        Assert.assertEquals(new BigDecimal("0.00"), BigDecimalUtil.toBigDecimal(new Double("0.0"), null));
        Assert.assertEquals(new BigDecimal("0.00"), BigDecimalUtil.toBigDecimal(new Double("0.00000000000000"), null));
        // 有进位
        Assert.assertEquals(new BigDecimal("123.57"), BigDecimalUtil.toBigDecimal(new Double("123.567"), null));
        Assert.assertEquals(new BigDecimal("123.04"), BigDecimalUtil.toBigDecimal(new Double("123.036345"), null));
        Assert.assertEquals(new BigDecimal("1235.91"), BigDecimalUtil.toBigDecimal(new Double("1235.908"), null));
        Assert.assertEquals(new BigDecimal("-123.55"), BigDecimalUtil.toBigDecimal(new Double("-123.545"), null));

        // exception：参数为 null
        Assert.assertThrows(CommonException.class, ()->{BigDecimalUtil.toBigDecimal(null, null);});
    }

    @Test
    public void testDoubleToBigDecimalWithNoScale() {
        // normal：正数，负数
        // 正数
        Assert.assertEquals(new BigDecimal("123.012345"), BigDecimalUtil.toBigDecimal(new Double("123.012345")));
        Assert.assertEquals(new BigDecimal("1.23E7"), BigDecimalUtil.toBigDecimal(new Double("123E5")));
        Assert.assertEquals(new BigDecimal("123.0"), BigDecimalUtil.toBigDecimal(new Double("123")));
        // 负数
        Assert.assertEquals(new BigDecimal("-123.0"), BigDecimalUtil.toBigDecimal(new Double("-123")));
        Assert.assertEquals(new BigDecimal("-123.012345"), BigDecimalUtil.toBigDecimal(new Double("-123.012345")));
        Assert.assertEquals(new BigDecimal("-1.23E7"), BigDecimalUtil.toBigDecimal(new Double("-123E5")));

        // boundary：0，极限值
        // 0
        Assert.assertEquals(new BigDecimal("0.0"), BigDecimalUtil.toBigDecimal(new Double("0")));
        Assert.assertEquals(new BigDecimal("0.0"), BigDecimalUtil.toBigDecimal(new Double("-0")));
        Assert.assertEquals(new BigDecimal("0.0"), BigDecimalUtil.toBigDecimal(new Double("0.0")));
        Assert.assertEquals(new BigDecimal("0.0"), BigDecimalUtil.toBigDecimal(new Double("0.0000000000")));
        Assert.assertEquals(new BigDecimal("0.0"), BigDecimalUtil.toBigDecimal(new Double("-0.0000000000")));
        // 极限值
        Assert.assertEquals(new BigDecimal("999999999999999"), BigDecimalUtil.toBigDecimal(new Double("999999999999999")));
        Assert.assertEquals(new BigDecimal("9999999999.99999"), BigDecimalUtil.toBigDecimal(new Double("9999999999.99999")));
        Assert.assertEquals(new BigDecimal("-999999999999999"), BigDecimalUtil.toBigDecimal(new Double("-999999999999999")));
        Assert.assertEquals(new BigDecimal("-9999999999.99999"), BigDecimalUtil.toBigDecimal(new Double("-9999999999.99999")));

        // exception：参数为null
        Double num = null;
        Assert.assertThrows(CommonException.class, ()->{BigDecimalUtil.toBigDecimal(num);});
    }

    @Test
    public void testStringToBigDecimal() {
        // normal：正数、负数
        // 正数
        Assert.assertEquals(new BigDecimal("123"), BigDecimalUtil.toBigDecimal("123"));
        Assert.assertEquals(new BigDecimal("123.012345"), BigDecimalUtil.toBigDecimal("123.012345"));
        Assert.assertEquals(new BigDecimal("123E5"), BigDecimalUtil.toBigDecimal("123E5"));
        // 负数
        Assert.assertEquals(new BigDecimal("-123"), BigDecimalUtil.toBigDecimal("-123"));
        Assert.assertEquals(new BigDecimal("-123.012345"), BigDecimalUtil.toBigDecimal("-123.012345"));
        Assert.assertEquals(new BigDecimal("-123E5"), BigDecimalUtil.toBigDecimal("-123E5"));

        // boundary：0，极限值
        // 0
        Assert.assertEquals(new BigDecimal("0"), BigDecimalUtil.toBigDecimal("0"));
        Assert.assertEquals(new BigDecimal("0"), BigDecimalUtil.toBigDecimal("-0"));
        Assert.assertEquals(new BigDecimal("0.0"), BigDecimalUtil.toBigDecimal("0.0"));
        Assert.assertEquals(new BigDecimal("0E-10"), BigDecimalUtil.toBigDecimal("0.0000000000"));
        Assert.assertEquals(new BigDecimal("0E-10"), BigDecimalUtil.toBigDecimal("-0.0000000000"));
        // 极限值
        Assert.assertEquals(new BigDecimal("999999999999999"), BigDecimalUtil.toBigDecimal("999999999999999"));
        Assert.assertEquals(new BigDecimal("9999999999.99999"), BigDecimalUtil.toBigDecimal("9999999999.99999"));
        Assert.assertEquals(new BigDecimal("-999999999999999"), BigDecimalUtil.toBigDecimal("-999999999999999"));
        Assert.assertEquals(new BigDecimal("-9999999999.99999"), BigDecimalUtil.toBigDecimal("-9999999999.99999"));

        // exception：参数为null
        String num = null;
        Assert.assertThrows(CommonException.class, ()->{BigDecimalUtil.toBigDecimal(num);});
        Assert.assertThrows(CommonException.class, ()->{BigDecimalUtil.toBigDecimal("");});
        Assert.assertThrows(CommonException.class, ()->{BigDecimalUtil.toBigDecimal("abc");});
        Assert.assertThrows(CommonException.class, ()->{BigDecimalUtil.toBigDecimal("abc123");});
    }

    @Test
    public void testBigDecimalToDouble() {
        // normal：正数、负数
        // 正数
        Assert.assertEquals(new Double("123").doubleValue(), BigDecimalUtil.toDouble(new BigDecimal("123")), 0);
        Assert.assertEquals(new Double("123.012345").doubleValue(), BigDecimalUtil.toDouble(new BigDecimal("123.012345")),0);
        Assert.assertEquals(new Double("123E5").doubleValue(), BigDecimalUtil.toDouble(new BigDecimal("123E5")),0);
        // 负数
        Assert.assertEquals(new Double("-123").doubleValue(), BigDecimalUtil.toDouble(new BigDecimal("-123")),0);
        Assert.assertEquals(new Double("-123.012345").doubleValue(), BigDecimalUtil.toDouble(new BigDecimal("-123.012345")),0);
        Assert.assertEquals(new Double("-123E5").doubleValue(), BigDecimalUtil.toDouble(new BigDecimal("-123E5")),0);

        // boundary：0，极限值
        // 0
        Assert.assertEquals(new Double("0").doubleValue(), BigDecimalUtil.toDouble(new BigDecimal("0")),0);
        Assert.assertEquals(new Double("0").doubleValue(), BigDecimalUtil.toDouble(new BigDecimal("-0")),0);
        Assert.assertEquals(new Double("0.0").doubleValue(), BigDecimalUtil.toDouble(new BigDecimal("0.0")),0);
        Assert.assertEquals(new Double("0E-10").doubleValue(), BigDecimalUtil.toDouble(new BigDecimal("0.000000000000000")),0);
        Assert.assertEquals(new Double("0E-10").doubleValue(), BigDecimalUtil.toDouble(new BigDecimal("-0.000000000000000")),0);
        // 极限值
        Assert.assertEquals(new Double("1.23E16").doubleValue(), BigDecimalUtil.toDouble(new BigDecimal("123E14")),0);
        Assert.assertEquals(new Double("-1.23E16").doubleValue(), BigDecimalUtil.toDouble(new BigDecimal("-123E14")),0);

        // exception：参数为null
        BigDecimal num = null;
        Assert.assertThrows(CommonException.class, ()->{BigDecimalUtil.toDouble(num);});
    }
}