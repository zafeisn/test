package com.amway.commerce.crypto;


import com.amway.commerce.exception.CommonException;
import com.amway.commerce.string.StringUtil;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

/**
 * @author: Jason.Hu
 * @date: 2023-09-19
 * @desc:
 */
public class MD5UtilTest {

    @Test
    public void testEncryptAndVerify() throws NoSuchAlgorithmException {
        // normal：中文、英文、字符
        String plainText_01[] = {"你好世界", "hello worldHELLO WORLD", ",.!", "你好世界hello world,.!"};
        for (int i = 0; i < plainText_01.length; i++) {
            Assert.assertTrue(MD5Util.verify(plainText_01[i], MD5Util.encrypt(plainText_01[i])));
        }

        // boundary：空值、空格、特殊字符、单个字符、长文本
        String longStr = StringUtil.streamToStr(AESUtilTest.class.getResourceAsStream("/DDD.txt"));
        String plainText_02[] = {"", " ", "\n\r", longStr, "a", "1", "你"};
        for (int i = 0; i < plainText_02.length; i++) {
            Assert.assertTrue(MD5Util.verify(plainText_02[i], MD5Util.encrypt(plainText_02[i])));
        }

        // exception：参数为null
        String plainText_03[] = {null};
        Assert.assertFalse(MD5Util.verify("你好世界", MD5Util.encrypt(plainText_01[1])));
        for (int i = 0; i < plainText_03.length; i++) {
            int i_ = i;
            Assert.assertThrows(CommonException.class, ()->{MD5Util.verify(plainText_03[i_], MD5Util.encrypt(plainText_03[i_]));});
        }
    }
}