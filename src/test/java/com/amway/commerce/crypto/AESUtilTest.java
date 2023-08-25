package com.amway.commerce.crypto;

import com.sun.istack.internal.Nullable;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

/**
 * @author: Jason.Hu
 * @date: 2023-08-15
 * @desc:
 */
public class AESUtilTest {

    String plainText = "123中文！@#￥%……&*（）-+=";
    String aesKey = "1234567890098765";
    String aesIv = "1234567890098765";
    String charset = "UTF-8";
    String cipherType = null;


    /**
     * AES加密算法
     * AES加密默认
     * AES解密算法
     * AES解密默认
     */
    @Test
    public void test() throws InvalidAlgorithmParameterException, UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String encrypt = AESUtil.encrypt(plainText, aesKey, aesIv, charset, cipherType);
        // 33qn2cKSKCgqHqI5alFBKApmUC4xghdH6aqXkOUKmgmQ9fJ054Yxc0iogwT9juMQ
        System.out.println(encrypt);

        // beisfUKg9XJkxzOYLv2VkiAuzUjZAoYWo89Zio2dXtrugbm5ZUHKFb55kHHnxzNp
        String encrypt1 = AESUtil.encrypt(plainText, aesKey);
        System.out.println(encrypt1);

        String decrypt = AESUtil.decrypt(encrypt, aesKey, aesIv, charset, cipherType);
        // 123中文！@#￥%……&*（）-+=
        System.out.println(decrypt);
        // 123中文！@#￥%……&*（）-+=
        System.out.println(AESUtil.decrypt(encrypt1, aesKey));
    }
}