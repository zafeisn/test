package com.amway.commerce.crypto;

import com.amway.commerce.exception.CommonException;
import com.amway.commerce.string.StringUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Random;

/**
 * @author: Jason.Hu
 * @date: 2023-09-19
 */
public class Base64UtilTest {

    @Test
    public void testBase64OfByte() {
        // normal：-127~128的随机字节
        byte[] buf_01 = new byte[1024];
        new Random().nextBytes(buf_01);
        Assert.assertArrayEquals(buf_01, Base64Util.decode(Base64Util.encode(buf_01)));

        // boundary：空格、换行符、转义字符、极限值
        byte[] buf_02 = {' ', '\n', '\r', 0, Byte.MIN_VALUE, Byte.MAX_VALUE};
        byte[] buf_03 = new byte[0];
        byte[] buf_04 = new byte[1];

        Assert.assertArrayEquals(buf_02, Base64Util.decode(Base64Util.encode(buf_02)));
        Assert.assertArrayEquals(buf_03, Base64Util.decode(Base64Util.encode(buf_03)));
        Assert.assertArrayEquals(buf_04, Base64Util.decode(Base64Util.encode(buf_04)));

        // exception：参数为null
        byte[] buf_05 = null;
        Assert.assertThrows(CommonException.class, () -> {Base64Util.decode(Base64Util.encode(buf_05));});
    }

    @Test
    public void testBase64OfString() throws UnsupportedEncodingException {
        // normal：数字、中文、字符、英文，单个字符、空值、空格、换行转义字符、长文本
        String longStr = StringUtil.streamToStr(Base64UtilTest.class.getResourceAsStream("/DDD.txt"));
        String[] str_01 = {"123", "中文", "!@#$%", "abc",
                "a", ""," ", "\n\r", longStr};
        for (String s : str_01) {
            Assert.assertEquals(s, new String(Base64Util.decode(Base64Util.encode(s))));
        }

        // exception：参数为null
        String[] str_03 = {null};
        for (String s : str_03) {
            Assert.assertThrows(CommonException.class, ()->{Base64Util.decode(Base64Util.encode(s));});
        }
    }

}