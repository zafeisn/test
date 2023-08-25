package com.amway.commerce.validation;

import org.junit.Test;

/**
 * @author: Jason.Hu
 * @date: 2023-08-07
 * @desc:
 */

public class EnumUtilTest {

    /**
     * 判断指定字符串是否是某个枚举类型属性的值
     */
    @Test
    public void isEnumValue() {
        // false
        System.out.println(EnumValidUtil.isEnumFiled(AEnum.class, "A", "C"));
    }
}