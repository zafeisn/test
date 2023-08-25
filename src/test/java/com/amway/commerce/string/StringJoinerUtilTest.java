package com.amway.commerce.string;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author: Jason.Hu
 * @date: 2023-08-07
 * @desc:
 */
public class StringJoinerUtilTest {

    /**
     * 字符串拼接
     */
    @Test
    public void joinStr() {
        // key:value
        System.out.println(StringJoinerUtil.joinStr(":", "key", "value"));
    }
}