package com.amway.commerce.calculation;

import com.amway.commerce.string.StringUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author: Jason.Hu
 * @date: 2023-08-09
 * @desc: 金额工具类
 */
public class MoneyUtil {

    /**
     * 默认金额显示格式，默认保留小数点后两位（元角分）
     */
    public static final String DEFAULT_PATTERN = "###,##0.00";

    /**
     * 金额格式化转换，例如 123456789.0 ###,##0.0 -> 123,456,789.0
     *
     * @param pattern 显示格式
     * @param money   金额数字
     * @return 如果 money参数为纯数字，则返回格式化后的字符串，否则返回 null
     */
    public static String format(String pattern, BigDecimal money) {
        if (!StringUtil.isNumeric(money.toPlainString())) {
            return null;
        }
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance();
        formatter.applyPattern(pattern);
        return formatter.format(money);
    }
}
