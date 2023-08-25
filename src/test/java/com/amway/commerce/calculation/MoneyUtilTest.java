package com.amway.commerce.calculation;

import com.amway.commerce.serialization.B;
import com.amway.commerce.string.StringUtil;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author: Jason.Hu
 * @date: 2023-08-09
 * @desc:
 */
public class MoneyUtilTest {

    /**
     * 金额格式化转换，例如 123456789.0 ###,###.### -> 123,456,789.0
     * # 没有则填空，有则
     */
    @Test
    public void format() {
        // 0.001
        System.out.println(MoneyUtil.format("###,###.####", new BigDecimal("0.001000")));
        // 0.0011
        System.out.println(MoneyUtil.format("###,###.####", new BigDecimal("0.001100")));
        // 0.0010
        System.out.println(MoneyUtil.format("###,##0.0000", new BigDecimal("0.001000")));
        // 11,111,111.0010
        System.out.println(MoneyUtil.format("###,##0.0000", new BigDecimal("11111111.001000")));
        // 11,111,111.0011
        System.out.println(MoneyUtil.format("000,000.0000", new BigDecimal("11111111.001100")));
        // 11,111,111.0010
        System.out.println(MoneyUtil.format("000,000.0000", new BigDecimal("11111111.001000")));
        // 333,333,333,333,333,333.9909
        System.out.println(MoneyUtil.format("###,##0.0000", new BigDecimal("333333333333333333.9909")));
        // 22,222,222,222.33
        System.out.println(MoneyUtil.format(MoneyUtil.DEFAULT_PATTERN, new BigDecimal("22222222222.3333")));
    }
}