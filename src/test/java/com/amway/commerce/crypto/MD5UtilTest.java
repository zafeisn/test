package com.amway.commerce.crypto;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

/**
 * @author: Jason.Hu
 * @date: 2023-08-14
 * @desc:
 */
public class MD5UtilTest {

    /**
     * 计算字符串的 MD5信息摘要
     */
    @Test
    public void encrypt() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String plainText = "中文";
        // a7bac2239fcdcb3a067903d8077c4a07
        System.out.println(MD5Util.encrypt(plainText, null));
        String plainText1 = "12345678901234567890";
        // fd85e62d9beb45428771ec688418b271
        System.out.println(MD5Util.encrypt(plainText1, null));
        String plainText2 = "123456789012345678901234567890.0";
        // c32610129fd17009e0d6caac31d6ee15
        System.out.println(MD5Util.encrypt(plainText2, null));
        String plainText3 = "中文";
        // bcce109775e8e1972e9f5fcda3e12895
        System.out.println(MD5Util.encrypt(plainText3, "GBK"));
    }
}