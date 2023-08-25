package com.amway.commerce.calculation;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author: Jason.Hu
 * @date: 2023-08-07
 * @desc:
 */
public class BigDecimalUtilTest {

    /**
     * 判断 num1是否大于 num2
     */
    @Test
    public void isGreaterThan() {
        // false
        System.out.println(BigDecimalUtil.isGreaterThan(new BigDecimal(2), new BigDecimal(2.0)));
        // false
        System.out.println(BigDecimalUtil.isGreaterThan(BigDecimal.valueOf(-2), BigDecimal.valueOf(-2)));
        // true
        System.out.println(BigDecimalUtil.isGreaterThan(BigDecimal.valueOf(4), BigDecimal.valueOf(-2)));
    }

    /**
     * 判断 num1是否大于等于 num2
     */
    @Test
    public void isGreaterEqual() {
        // true
        System.out.println(BigDecimalUtil.isGreaterEqual(new BigDecimal(2), new BigDecimal(2.0)));
        // false
        System.out.println(BigDecimalUtil.isGreaterEqual(BigDecimal.valueOf(2), BigDecimal.valueOf(4)));
        // true
        System.out.println(BigDecimalUtil.isGreaterEqual(BigDecimal.valueOf(2), BigDecimal.valueOf(2)));
    }

    /**
     * 判断 num1是否等于 num2
     */
    @Test
    public void isEqual() {
        // true
        System.out.println(BigDecimalUtil.isEqual(new BigDecimal(2), new BigDecimal(2.0)));
        // false
        System.out.println(BigDecimalUtil.isEqual(new BigDecimal("9999999999999999999.09"), new BigDecimal("9999999999999999999.08")));
        // false
        System.out.println(BigDecimalUtil.isEqual(BigDecimal.valueOf(2), BigDecimal.valueOf(4)));
        // true
        System.out.println(BigDecimalUtil.isEqual(BigDecimal.valueOf(2), BigDecimal.valueOf(2)));
    }

    /**
     * Double转换为 BigDecimal
     */
    @Test
    public void toBigDecimalScale() {
        // -1000000000000000.00
        System.out.println(BigDecimalUtil.toBigDecimal(-999999999999999.99, 2, RoundingMode.HALF_UP));
        // 1000000000000000.00
        System.out.println(BigDecimalUtil.toBigDecimal(999999999999999.99, 2, RoundingMode.HALF_UP));
        // 999999999999.99100
        System.out.println(BigDecimalUtil.toBigDecimal(999999999999.99099, 5, RoundingMode.HALF_UP));

    }

    /**
     * String转换为 BigDecimal
     * Double转换为 BigDecimal，默认保留小数点后两位，向上舍入
     */
    @Test
    public void toBigDecimal() {
        // 999999999999.99099
        System.out.println(BigDecimalUtil.toBigDecimal("999999999999.99099"));
        // 0.00
        System.out.println(BigDecimalUtil.toBigDecimal(""));
        // 999999999999.99
        System.out.println(BigDecimalUtil.toBigDecimal(999999999999.99099));
    }

    /**
     * BigDecimal转换为 Double
     */
    @Test
    public void toDouble() {
        // 4444.00998
        System.out.println(BigDecimalUtil.toDouble(BigDecimal.valueOf(4444.00998)));
        // 1.0E16
        System.out.println(BigDecimalUtil.toDouble(BigDecimal.valueOf(9999999999999999.0)));
        // 0.0
        System.out.println(BigDecimalUtil.toDouble(null));
        // 0.0
        System.out.println(BigDecimalUtil.toDouble(BigDecimal.valueOf(0)));
        // 1.0E16
        System.out.println(BigDecimalUtil.toDouble(new BigDecimal(9999999999999999.789)));
    }

    /**
     * BigDecimal转换为 String，默认 DEFAULT_DECIMAL_FORMAT保留小数点后两位
     * BigDecimal转换为 String
     */
    @Test
    public void toFormatString() {
        // 99999999.00
        System.out.println(BigDecimalUtil.toFormatString(BigDecimal.valueOf(99999999)));
        // 0
        System.out.println(BigDecimalUtil.toFormatString(BigDecimal.valueOf(000), "#.##"));
        // 999.999
        System.out.println(BigDecimalUtil.toFormatString(BigDecimal.valueOf(999.999), "#.####"));
        // 999.99
        System.out.println(BigDecimalUtil.toFormatString(BigDecimal.valueOf(999.99), "#.##"));
        // 99,999.99
        System.out.println(BigDecimalUtil.toFormatString(BigDecimal.valueOf(99999.99), "###,###.##"));
    }
}