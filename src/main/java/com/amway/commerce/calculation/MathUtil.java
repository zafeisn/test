package com.amway.commerce.calculation;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author: Jason.Hu
 * @date: 2023-08-08
 * @desc: Math工具类
 */
public class MathUtil {

    /**
     * 默认保留 2位小数
     */
    private final static Integer DEFAULT_SCALE = 2;

    /**
     * 默认四舍五入
     */
    private final static RoundingMode DEFAULT_ROUNDINGMODE = RoundingMode.HALF_UP;

    /**
     * 保留几位小数以及舍入模式设置，默认为 2和 RoundingMode.HALF_UP
     *
     * @param bigDecimal
     * @param scale        保留几位小数，Integer类型
     * @param roundingMode 舍入模式，RoundingMode
     * @return 设置结果
     */
    private static BigDecimal setScale(BigDecimal bigDecimal, Integer scale, RoundingMode roundingMode) {
        return bigDecimal.setScale(scale == null ? DEFAULT_SCALE : scale, roundingMode == null ? DEFAULT_ROUNDINGMODE : roundingMode);
    }

    /**
     * 两数相加，num1 + num2，没有设置保留小数点后几位以及舍入模式
     *
     * @param num1
     * @param num2
     * @return 相加结果
     */
    public static BigDecimal add(BigDecimal num1, BigDecimal num2) {
        return num1.add(num2);
    }

    /**
     * num1 + nums
     *
     * @param scale        保留小数点后几位
     * @param roundingMode 舍入模式
     * @param num1
     * @param nums         BigDecimal数组
     * @return 如果 nums不为空，则返回求和结果，否则返回 num1
     */
    public static BigDecimal addScale(Integer scale, RoundingMode roundingMode, BigDecimal num1, BigDecimal... nums) {
        return sum(scale, roundingMode, num1, nums);
    }

    /**
     * num1 + nums
     *
     * @param num1
     * @param nums BigDecimal数组
     * @return 如果 nums不为空，则返回求和结果，否则返回 num1。默认保留小数点后两位，默认 BigDecimal.ROUND_UP（四舍五入）
     */
    public static BigDecimal addScale(BigDecimal num1, BigDecimal... nums) {
        return sum(null, null, num1, nums);
    }

    /**
     * 相加，包括两数相加和数组求和，方法私有
     *
     * @param scale        保留小数点后几位
     * @param roundingMode 舍入模式
     * @param num1
     * @param nums
     * @return 相加结果
     */
    private static BigDecimal sum(Integer scale, RoundingMode roundingMode, BigDecimal num1, BigDecimal... nums) {
        BigDecimal resultValue = num1;
        if (nums != null && nums.length > 0) {
            for (BigDecimal num : nums) {
                resultValue = add(num, resultValue);
            }
            // 保留小数点后几位和设置舍入模式
            resultValue = setScale(resultValue, scale, roundingMode);
        }
        return resultValue;
    }

    /**
     * num1 - nums
     *
     * @param scale        保留小数点后几位
     * @param roundingMode 舍入模式
     * @param num1
     * @param nums         BigDecimal数组
     * @return 如果 nums不为空，则返回计算结果，否则返回 num1
     */
    public static BigDecimal subScale(Integer scale, RoundingMode roundingMode, BigDecimal num1, BigDecimal... nums) {
        return subtract(scale, roundingMode, num1, nums);
    }

    /**
     * num1 - nums
     *
     * @param num1
     * @param nums BigDecimal数组
     * @return 如果 nums不为空，则返回计算结果，否则返回 num1。默认保留小数点后两位，默认 BigDecimal.ROUND_UP（四舍五入）
     */
    public static BigDecimal subScale(BigDecimal num1, BigDecimal... nums) {
        return subtract(null, null, num1, nums);
    }

    /**
     * 两数相减，num1 - num2，没有设置保留小数点后几位以及舍入模式
     *
     * @param num1
     * @param num2
     * @return 相减结果
     */
    public static BigDecimal subtract(BigDecimal num1, BigDecimal num2) {
        return num1.subtract(num2);
    }

    /**
     * 相减，num1 - nums，包括两数相减和累计减，方法私有
     *
     * @param scale        保留小数点后几位
     * @param roundingMode 舍入模式
     * @param num1
     * @param nums
     * @return 相减结果
     */
    private static BigDecimal subtract(Integer scale, RoundingMode roundingMode, BigDecimal num1, BigDecimal... nums) {
        BigDecimal resultValue = num1;
        if (nums != null && nums.length > 0) {
            for (BigDecimal num : nums) {
                resultValue = subtract(resultValue, num);
            }
            // 保留小数点后几位和设置舍入模式
            resultValue = setScale(resultValue, scale, roundingMode);
        }
        return resultValue;
    }

    /**
     * 两数相乘（BigDecimal * BigDecimal）
     *
     * @param num1
     * @param num2
     * @param scale        保留小数点后几位
     * @param roundingMode 舍入模式
     * @return 相乘结果
     */
    public static BigDecimal mulScale(BigDecimal num1, BigDecimal num2, Integer scale, RoundingMode roundingMode) {
        return multiply(num1, num2, scale, roundingMode);
    }

    /**
     * 两数相乘（BigDecimal * BigDecimal）
     *
     * @param num1
     * @param num2
     * @return 相乘结果，默认保留小数点后两位，默认 BigDecimal.ROUND_UP（四舍五入）
     */
    public static BigDecimal mulScale(BigDecimal num1, BigDecimal num2) {
        return multiply(num1, num2, null, null);
    }

    /**
     * 两数相乘，没有设置保留小数点后几位以及舍入模式
     *
     * @param num1
     * @param num2
     * @return 相乘结果
     */
    public static BigDecimal multiply(BigDecimal num1, BigDecimal num2) {
        return num1.multiply(num2);
    }

    /**
     * 相乘，方法私有
     */
    private static BigDecimal multiply(BigDecimal num1, BigDecimal num2, Integer scale, RoundingMode roundingMode) {
        return setScale(multiply(num1, num2), scale, roundingMode);
    }

    /**
     * 两数相乘（BigDecimal * Long）
     *
     * @param num1
     * @param num2         Long类型
     * @param scale        保留小数点后几位
     * @param roundingMode 舍入模式
     * @return 相乘结果
     */
    public static BigDecimal mulScale(BigDecimal num1, Long num2, Integer scale, RoundingMode roundingMode) {
        return multiply(num1, BigDecimal.valueOf(num2), scale, roundingMode);
    }

    /**
     * 两数相乘（BigDecimal * Long）
     *
     * @param num1
     * @param num2 Long类型
     * @return 相乘结果，默认保留小数点后两位，默认 BigDecimal.ROUND_UP（四舍五入）
     */
    public static BigDecimal mulScale(BigDecimal num1, Long num2) {
        return multiply(num1, BigDecimal.valueOf(num2), null, null);
    }

    /**
     * 两数相乘（BigDecimal * Integer）
     *
     * @param num1
     * @param num2         Integer类型
     * @param scale        保留小数点后几位
     * @param roundingMode 舍入模式
     * @return 相乘结果
     */
    public static BigDecimal mulScale(BigDecimal num1, Integer num2, Integer scale, RoundingMode roundingMode) {
        return multiply(num1, BigDecimal.valueOf(num2), scale, roundingMode);
    }

    /**
     * 两数相乘（BigDecimal * Integer）
     *
     * @param num1
     * @param num2 Integer类型
     * @return 相乘结果，默认保留小数点后两位，默认 BigDecimal.ROUND_UP（四舍五入）
     */
    public static BigDecimal mulScale(BigDecimal num1, Integer num2) {
        return multiply(num1, BigDecimal.valueOf(num2), null, null);
    }

    /**
     * 两数相除，私有方法
     *
     * @param num1
     * @param num2
     * @param scale        保留小数点后几位
     * @param roundingMode 舍入模式
     * @return 相加结果，默认保留小数点后两位，默认 BigDecimal.ROUND_UP（四舍五入）
     */
    private static BigDecimal divide(BigDecimal num1, BigDecimal num2, Integer scale, RoundingMode roundingMode) {
        return num1.divide(num2, scale == null ? DEFAULT_SCALE : scale, roundingMode == null ? DEFAULT_ROUNDINGMODE : roundingMode);
    }

    /**
     * 两数相除
     *
     * @param num1
     * @param num2
     * @param scale        保留小数点后几位
     * @param roundingMode 舍入模式
     * @return 相除结果
     */
    public static BigDecimal divScale(BigDecimal num1, BigDecimal num2, Integer scale, RoundingMode roundingMode) {
        return divide(num1, num2, scale, roundingMode);
    }

    /**
     * 两数相除
     *
     * @param num1
     * @param num2
     * @return 相除结果，默认保留小数点后两位，默认 BigDecimal.ROUND_UP（四舍五入）
     */
    public static BigDecimal divScale(BigDecimal num1, BigDecimal num2) {
        return divide(num1, num2, null, null);
    }

    /**
     * 计算占比
     *
     * @param divisor      除数
     * @param dividend     被除数
     * @param use          使用数
     * @param scale        保留小数点后几位
     * @param roundingMode 舍入模式
     * @return 占比
     */
    public static BigDecimal ratioCalculation(BigDecimal divisor, Integer dividend, Integer use, Integer scale, RoundingMode roundingMode) {
        if (dividend == null || dividend.equals(new Integer(0))) {
            return BigDecimal.ZERO;
        }
        return divScale(multiply(divisor, BigDecimal.valueOf(use)), BigDecimal.valueOf(dividend), scale, roundingMode);
    }

    /**
     * 计算占比
     *
     * @param divisor  除数
     * @param dividend 被除数
     * @param use      使用数
     * @return 占比，默认保留小数点后两位，默认 BigDecimal.ROUND_UP（四舍五入）
     */
    public static BigDecimal ratioCalculation(BigDecimal divisor, Integer dividend, Integer use) {
        // TODO：需要问的
        return ratioCalculation(divisor, dividend, use, null, null);
    }

}
