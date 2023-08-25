package com.amway.commerce.crypto;

import com.amway.commerce.string.ByteUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: Jason.Hu
 * @date: 2023-08-14
 * @desc: MD5工具类
 */
public class MD5Util {

    private static final String ALGORITHM = "MD5";

    private static String DEFAULT_CHARSET = "UTF-8";

    /**
     * 计算字符串的 MD5信息摘要
     * 输入任意长度信息，输出长度固定
     *
     * @param plainText 明文字符串
     * @return 32位小写的MD5信息摘要
     */
    public static String encrypt(String plainText, String charset) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 创建 MD5算法
        MessageDigest md5 = MessageDigest.getInstance(ALGORITHM);
        // 转换为 MD5码
        byte[] digest = md5.digest(plainText.getBytes(charset == null ? DEFAULT_CHARSET : charset));
        return ByteUtil.bytesToHexString(digest);
    }
}
