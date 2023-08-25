package com.amway.commerce.calculation;

import com.amway.commerce.string.StringUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author: Jason.Hu
 * @date: 2023-08-07
 * @desc: BigDecimal工具类
 */
public class BigDecimalUtil {

    /**
     * 0.00，参数为空的默认返回结果
     */
    public static final BigDecimal BIG_DECIMAL_ZERO = new BigDecimal("0.00");

    /**
     * 默认转换的数值格式，保留小数点后两位
     */
    private static final String DEFAULT_PATTERN = "#.00";

    /**
     * 用于格式化操作
     */
    private static final DecimalFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat(DEFAULT_PATTERN);


    /**
     * 判断 num1是否大于 num2
     *
     * @param num1
     * @param num2
     * @return 比较结果，true表示 num1大于 num2
     */
    public static boolean isGreaterThan(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) > 0;
    }

    /**
     * 判断 num1是否大于等于 num2
     *
     * @param num1
     * @param num2
     * @return 比较结果，true表示 num1大于等于 num2
     */
    public static boolean isGreaterEqual(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) >= 0;
    }

    /**
     * 判断 num1是否等于 num2
     *
     * @param num1
     * @param num2
     * @return 比较结果，true表示 num1等于 num2
     */
    public static boolean isEqual(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) == 0;
    }

    /**
     * Double转换为 BigDecimal
     *
     * @param value
     * @param scale        保留小数点后几位
     * @param roundingMode 舍入模式
     * @return 如果 value不为 null，则返回转换结果，否则返回 BIG_DECIMAL_ZERO
     */
    public static BigDecimal toBigDecimal(Double value, Integer scale, RoundingMode roundingMode) {
        if (value == null) {
            return BIG_DECIMAL_ZERO;
        }
        return BigDecimal.valueOf(value).setScale(scale == null ? 2 : scale, roundingMode == null ? RoundingMode.HALF_UP : roundingMode);
    }

    /**
     * Double转换为 BigDecimal，默认保留小数点后两位，ROUND_HALF_UP四舍五入
     *
     * @param value
     * @return 如果 value不为 null，则返回转换结果，否则返回 BIG_DECIMAL_ZERO
     */
    public static BigDecimal toBigDecimal(Double value) {
        return toBigDecimal(value, null, null);
    }

    /**
     * String转换为 BigDecimal
     *
     * @param value
     * @return 如果 value不为 null，则返回转换结果，否则返回 BIG_DECIMAL_ZERO
     */
    public static BigDecimal toBigDecimal(String value) {
        if (StringUtil.isBlank(value)) {
            return BIG_DECIMAL_ZERO;
        }
        return new BigDecimal(value);
    }

    /**
     * BigDecimal转换为 Double
     *
     * @param bigDecimal
     * @returnc 如果 value不为 null，则返回转换结果，否则返回 BIG_DECIMAL_ZERO.doubleValue()
     */
    public static double toDouble(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return BIG_DECIMAL_ZERO.doubleValue();
        }
        return bigDecimal.doubleValue();
    }

    /**
     * BigDecimal转换为 String
     *
     * @param bigDecimal
     * @param pattern    待转换的数值格式
     * @return 如果 value不为 null，则返回转换结果，否则返回 BigDecimal.ZERO
     */
    public static String toFormatString(BigDecimal bigDecimal, String pattern) {
        if (bigDecimal == null) {
            bigDecimal = BigDecimal.ZERO;
        }
        return new DecimalFormat(pattern).format(bigDecimal);
    }

    /**
     * BigDecimal转换为 String，默认 DEFAULT_DECIMAL_FORMAT保留小数点后两位
     *
     * @param bigDecimal
     * @return 如果 value不为 null，则返回转换结果，否则返回 BigDecimal.ZERO
     */
    public static String toFormatString(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            bigDecimal = BigDecimal.ZERO;
        }
        return DEFAULT_DECIMAL_FORMAT.format(bigDecimal);
    }

}
