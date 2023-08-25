package com.amway.commerce.calculation;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

/**
 * @author: Jason.Hu
 * @date: 2023-08-08
 * @desc:
 */
public class MathUtilTest {

    /**
     * 两数相加，num1 + num2
     * 两数相加，num1 + num2，默认保留小数点后两位，默认 BigDecimal.ROUND_HALF_UP（四舍五入）
     */
    @Test
    public void addScale() {
        // 7.9
        System.out.println(MathUtil.addScale(1, RoundingMode.HALF_UP, new BigDecimal("4.0"), new BigDecimal("3.9")));
        // 1099999999999.1
        System.out.println(MathUtil.addScale(1, RoundingMode.HALF_UP, new BigDecimal("999999999999.09"), new BigDecimal("99999999999.99999")));
        // 7.90
        System.out.println(MathUtil.addScale(new BigDecimal("4.0"), new BigDecimal("3.9")));
        // 1099999999999.09
        System.out.println(MathUtil.addScale(new BigDecimal("999999999999.09"), new BigDecimal("99999999999.99999")));
    }

    /**
     * 两数相加，num1 + num2
     */
    @Test
    public void add() {
        // 7.9
        System.out.println(MathUtil.add(new BigDecimal("4.0"), new BigDecimal("3.9")));
        // 1099999999999.08999
        System.out.println(MathUtil.add(new BigDecimal("999999999999.09"), new BigDecimal("99999999999.99999")));
        // 7.9
        System.out.println(MathUtil.add(new BigDecimal("4.0"), new BigDecimal("3.9")));
        // 1099999999999.08999
        System.out.println(MathUtil.add(new BigDecimal("999999999999.09"), new BigDecimal("99999999999.99999")));
    }

    /**
     * 数组求和，num1 + nums
     * 数组求和，num1 + nums，默认保留小数点后两位，默认 BigDecimal.ROUND_UP（四舍五入）
     */
    @Test
    public void addAll() {
        // 7.9000
        System.out.println(MathUtil.addScale(4, RoundingMode.HALF_UP, new BigDecimal("4.0"), new BigDecimal("3.9")));
        // 11.8000
        System.out.println(MathUtil.addScale(4, RoundingMode.HALF_UP, new BigDecimal("4.0"), new BigDecimal("3.9"), new BigDecimal("3.9")));
        // 1000003.900
        System.out.println(MathUtil.addScale(3, RoundingMode.HALF_UP, new BigDecimal("999999.99999"), new BigDecimal("3.9")));
        // 7.90
        System.out.println(MathUtil.addScale(new BigDecimal("4.0"), new BigDecimal("3.9")));
        // 1000003.90
        System.out.println(MathUtil.addScale(new BigDecimal("999999.99999"), new BigDecimal("3.9")));
    }

    /**
     * 两数相减
     * 两数相减，默认保留小数点后两位，默认 BigDecimal.ROUND_UP（四舍五入）
     */
    @Test
    public void subScale() {
        // 0.100
        System.out.println(MathUtil.subScale(3, RoundingMode.HALF_UP, new BigDecimal("4.0"), new BigDecimal("3.9")));
        // -0.900
        System.out.println(MathUtil.subScale(3, RoundingMode.HALF_UP, new BigDecimal("3.0"), new BigDecimal("3.9")));
        // 9999999999999999.01
        System.out.println(MathUtil.subScale(new BigDecimal("9999999999999999.099"), new BigDecimal("0.091")));
        // 999999999.01
        System.out.println(MathUtil.subScale(BigDecimal.valueOf(999999999.099), new BigDecimal("0.091")));
    }

    /**
     * 两数相减，num1 - num2
     */
    @Test
    public void subtract() {
        // 9999999999999999.008
        System.out.println(MathUtil.subtract(new BigDecimal("9999999999999999.099"), new BigDecimal("0.091")));
        // -0.001
        System.out.println(MathUtil.subtract(new BigDecimal("0"), new BigDecimal("0.001")));
    }

    /**
     * 批量减法，num1 - nums
     */
    @Test
    public void subAll() {
        // 9999999999999999.008
        System.out.println(MathUtil.subScale(3, RoundingMode.HALF_UP, new BigDecimal("9999999999999999.099"), new BigDecimal("0.091")));
        // -0.001
        System.out.println(MathUtil.subScale(3, RoundingMode.HALF_UP, new BigDecimal("0"), new BigDecimal("0.001")));
        // -0.002
        System.out.println(MathUtil.subScale(3, RoundingMode.HALF_UP, new BigDecimal("0"), new BigDecimal("0.001"), new BigDecimal("0.001")));
        // 9999999999999999.01
        System.out.println(MathUtil.subScale(new BigDecimal("9999999999999999.099"), new BigDecimal("0.091")));
        // -0.01
        System.out.println(MathUtil.subScale(new BigDecimal("0"), new BigDecimal("0.01")));
        // 0.00
        System.out.println(MathUtil.subScale(new BigDecimal("0"), null));
        // 0.00
        System.out.println(MathUtil.subScale(new BigDecimal("0"), new BigDecimal("0.001"), new BigDecimal("0.001")));
    }

    /**
     * 两数相乘（BigDecimal * BigDecimal）
     * 两数相乘（BigDecimal * BigDecimal），默认保留小数点后两位，默认 BigDecimal.ROUND_UP（四舍五入）
     * 两数相乘（BigDecimal * Long）
     * 两数相乘（BigDecimal * Long），默认保留小数点后两位，默认 BigDecimal.ROUND_UP（四舍五入）
     * 两数相乘（BigDecimal * Integer）
     * 两数相乘（BigDecimal * Integer），默认保留小数点后两位，默认 BigDecimal.ROUND_UP（四舍五入）
     */
    @Test
    public void multiply() {
        // 0.000
        System.out.println(MathUtil.mulScale(new BigDecimal("0"), new BigDecimal("0.001"), 3, RoundingMode.HALF_UP));
        // 999998999999999900.000100
        System.out.println(MathUtil.mulScale(new BigDecimal("9999999999999999"), new BigDecimal("99.9999"), 6, RoundingMode.HALF_UP));
        // 0.00
        System.out.println(MathUtil.mulScale(new BigDecimal("0"), new BigDecimal("0.001")));
        // 999998999999999900.00
        System.out.println(MathUtil.mulScale(new BigDecimal("9999999999999999"), new BigDecimal("99.9999")));

        // 10.000
        System.out.println(MathUtil.mulScale(new BigDecimal("0.001"), 10000L, 3, RoundingMode.HALF_UP));
        // 9999989999999999000001.000000
        System.out.println(MathUtil.mulScale(new BigDecimal("9999999999999999"), 999999L, 6, RoundingMode.HALF_UP));
        // 0.00
        System.out.println(MathUtil.mulScale(new BigDecimal("0"), 10000L));
        // 9999989999999999000001.00
        System.out.println(MathUtil.mulScale(new BigDecimal("9999999999999999"), 999999L));

        // 0.000
        System.out.println(MathUtil.mulScale(new BigDecimal("0.001"), 0, 3, RoundingMode.HALF_UP));
        // 9999989999999999000001.000000
        System.out.println(MathUtil.mulScale(new BigDecimal("9999999999999999"), 999999, 6, RoundingMode.HALF_UP));
        // 0.00
        System.out.println(MathUtil.mulScale(new BigDecimal("0"), 10000));
        // 9999989999999999000001.00
        System.out.println(MathUtil.mulScale(new BigDecimal("9999999999999999"), 999999));
    }

    /**
     * 两数相除
     * 两数相除，默认保留小数点后两位，默认 BigDecimal.ROUND_UP（四舍五入）
     */
    @Test
    public void divide() {
        // 1.951
        System.out.println(MathUtil.divScale(new BigDecimal("8.000"), new BigDecimal("4.1"), 3, RoundingMode.HALF_UP));
        // 1.99
        System.out.println(MathUtil.divScale(new BigDecimal("5"), new BigDecimal("2.5100")));
    }

    /**
     * 计算占比
     */
    @Test
    public void ratioCalculation() {
        // 7.500 = 3 * 5 / 2
        System.out.println(MathUtil.ratioCalculation(BigDecimal.valueOf(5), 2, 3, 3, RoundingMode.DOWN));
        // 0.000
        System.out.println(MathUtil.ratioCalculation(BigDecimal.valueOf(0), 1, 2, 3, RoundingMode.HALF_UP));
        // 0
        System.out.println(MathUtil.ratioCalculation(BigDecimal.valueOf(0), 0, 2, 3, RoundingMode.HALF_UP));
    }
}