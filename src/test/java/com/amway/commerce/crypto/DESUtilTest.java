package com.amway.commerce.crypto;

import com.amway.commerce.string.ByteUtil;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author: Jason.Hu
 * @date: 2023-08-21
 * @desc:
 */
public class DESUtilTest {

    String plainText = "123中文！@#￥%……&*（）-+=";
    String desKey = "12345678";
    String desIv = "12345678";
    String charset = "UTF-8";
    String cipherType = null;

    /**
     * DES加密
     */
    @Test
    public void encrypt() throws Exception {
        byte[] encrypt = DESUtil.encrypt(plainText.getBytes(charset), desKey, desIv, charset, null);
        String s = Base64Util.encode(encrypt);
        // xmF4oTvCx2lTuAHXtJWee9RHKCf8l619g4jvUIeJm1e2Cc7lYcKwVQ==
        System.out.println(s);

        byte[] encrypt1 = DESUtil.encrypt(plainText.getBytes(charset), desKey);
        String encode = Base64Util.encode(encrypt1);
        // 7pVY5DDvX8uT9gQ97ADSJ4opcgUWdmW7PsfK/8uWbQHQfvS+gZqaPw==
        System.out.println(encode);
    }

    /**
     * DES解密
     */
    @Test
    public void decrypt() throws Exception {
        byte[] encrypt = DESUtil.encrypt(plainText.getBytes(charset), desKey, desIv, charset, null);
        String s = Base64Util.encode(encrypt);
        // xmF4oTvCx2lTuAHXtJWee9RHKCf8l619g4jvUIeJm1e2Cc7lYcKwVQ==
        System.out.println(s);
        // 123中文！@#￥%……&*（）-+=
        System.out.println(ByteUtil.bytesToString(DESUtil.decrypt(encrypt, desKey, desIv, charset, null), charset));

        byte[] encrypt1 = DESUtil.encrypt(plainText.getBytes(charset), desKey);
        String encode = Base64Util.encode(encrypt1);
        // 7pVY5DDvX8uT9gQ97ADSJ4opcgUWdmW7PsfK/8uWbQHQfvS+gZqaPw==
        System.out.println(encode);
        // 123中文！@#￥%……&*（）-+=
        System.out.println(ByteUtil.bytesToString(DESUtil.decrypt(encrypt1, desKey), charset));

    }
}