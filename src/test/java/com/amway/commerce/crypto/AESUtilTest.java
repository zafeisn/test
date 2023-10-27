package com.amway.commerce.crypto;

import com.amway.commerce.exception.CommonException;
import com.amway.commerce.string.StringUtil;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @author: Jason.Hu
 * @date: 2023-09-19
 */
public class AESUtilTest {

    @Test
    public void testAesOfCbc() throws  IllegalBlockSizeException, BadPaddingException {
        // normal：中文、英文、数字、字符，单个字符、长文本；各种长度的密钥；各种长度的IV向量
        // 1、判断加密后的字符串是否为Base64编码过的
        // 2、判断加密后是否能成功解密
        String longStr = StringUtil.streamToStr(AESUtilTest.class.getResourceAsStream("/DDD.txt"));
        String plainText_01[] = {"中文你好世界！", "hello world HELLO WORLD", "123456", "!@#$%^&*()\n\r", "123中文hello!@#$%\n\r",
                "你", "i", "", " ", "\n\r", longStr};
        String aesKey_01[] = {"1234567890123456", "abcdefghijklmnop", "中你好世界a", "!@#$%^&*()\n\r-=+_", "中a123abc!@#\n\r(",
                "1234567890123456", "abcdefghijklmnop", "中你好世界a", "!@#$%^&*()\n\r-=+_", "中a123abc!@#\n\r(",
                "123456789012345678901234","12345678901234567890123456789012"};
        String aesIv_01[] = {"中a123abc!@#\n\r(", "!@#$%^&*()\n\r-=+_", "中你好世界a","abcdefghijklmnop","1234567890123456",
                "中a123abc!@#\n\r(", "!@#$%^&*()\n\r-=+_", "中你好世界a","abcdefghijklmnop","1234567890123456",
                "1234567890abcdef", "ABCD中文\n\r!@#$"};
        Base64.Decoder decoder = Base64.getDecoder();
        for (int i = 0; i < plainText_01.length; i++) {
            for (int j = 0; j < aesKey_01.length; j++) {
                for (int k = 0; k < aesIv_01.length; k++) {
                    String encrypt01 = AESUtil.encrypt(plainText_01[i], aesKey_01[j], aesIv_01[k]);
                    Assert.assertNotNull((decoder.decode(encrypt01)));
                    String decrypt01 = AESUtil.decrypt(encrypt01, aesKey_01[j], aesIv_01[k]);
                    Assert.assertEquals(plainText_01[i],decrypt01);
                }
            }
        }

        // exception：超过规定长度的密钥和IV向量，加解密密钥不匹配、加解密IV向量不一致，解密的字符串为非Base64编码的
        String plainText_03[] = {"hello wolrd"};
        String aesKey_03[] = {"1234567890123456333", "abcdefghijklmnopp", "中你好世界aa", "!@#$%^&*()\n\r-=+_@",
                "中aa123abc!@#\n\r(", "12345678901234567890123456789012"};
        String aesIv_03[] = {"1234567890123456333", "abcdefghijklmnopp", "中你好世界aa", "!@#$%^&*()\n\r-=+_@", "中aa123abc!@#\n\r("};
        // 超过规定长度的密钥和IV向量
        for (int i = 0; i < aesKey_03.length; i++) {
            for (int k = 0; k < aesIv_03.length; k++) {
                int i_ = i;
                int k_ = k;
                Assert.assertThrows(CommonException.class, () -> {
                    AESUtil.encrypt(plainText_03[0], aesKey_03[i_], aesIv_03[k_]);
                });
                Assert.assertThrows(CommonException.class, () -> {
                    AESUtil.encrypt(plainText_03[0], aesKey_01[i_], aesIv_03[k_]);
                });
            }
        }
        for (int i = 0; i < aesKey_03.length; i++) {
            for (int k = 0; k < aesIv_03.length; k++) {
                int i_ = i;
                int k_ = k;
                Assert.assertThrows(CommonException.class, () -> {
                    AESUtil.decrypt(plainText_03[0], aesKey_03[i_], aesIv_03[k_]);
                });
                Assert.assertThrows(CommonException.class, () -> {
                    AESUtil.decrypt(plainText_03[0], aesKey_01[i_], aesIv_03[k_]);
                });
            }
        }
        // 加解密密钥不匹配、加解密IV向量不一致
        for (int i = 0; i < aesKey_01.length; i++) {
            int i_ = i;
            if (i_ == aesKey_01.length - 1) {
                Assert.assertThrows(BadPaddingException.class, ()->{
                    AESUtil.decrypt(AESUtil.encrypt(plainText_03[0], aesKey_01[i_], aesIv_01[i_]), aesKey_01[0], aesIv_01[i_]);
                });
                Assert.assertThrows(BadPaddingException.class, ()->{
                    AESUtil.decrypt(AESUtil.encrypt(plainText_03[0], aesKey_01[i_], aesIv_01[i_]), aesKey_01[i_], aesIv_01[0]);
                });
            } else {
                Assert.assertThrows(BadPaddingException.class, ()->{
                    AESUtil.decrypt(AESUtil.encrypt(plainText_03[0], aesKey_01[i_], aesIv_01[i_]), aesKey_01[i_+1], aesIv_01[i_]);
                });
                Assert.assertThrows(BadPaddingException.class, ()->{
                    AESUtil.decrypt(AESUtil.encrypt(plainText_03[0], aesKey_01[i_], aesIv_01[i_]), aesKey_01[i_], aesIv_01[i_+1]);
                });
            }
        }
        // 解密的字符串为非Base64编码的
        Assert.assertThrows(IllegalArgumentException.class, ()->{AESUtil.decrypt(plainText_03[0], aesKey_01[0], aesIv_01[0]);});
    }

    @Test
    public void testAesOfEcb() throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        // normal：中文、英文、数字、字符、单个字符、长文本；各种规定长度的密钥
        // 1、判断加密后的字符串是否为Base64编码过的；
        // 2、判断加密后是否能成功解密
        String longStr = StringUtil.streamToStr(AESUtilTest.class.getResourceAsStream("/DDD.txt"));
        String plainText_01[] = {"中文你好世界！", "hello world HELLO WORLD", "123456", "!@#$%^&*()\n\r", "123中文hello!@#$%\n\r",
                "你", "i", "", " ", "\n\r", longStr};
        String aesKey_01[] = {"1234567890123456", "abcdefghijklmnop", "中你好世界a", "!@#$%^&*()\n\r-=+_", "中a123abc!@#\n\r(",
                "1234567890123456", "abcdefghijklmnop", "中你好世界a", "!@#$%^&*()\n\r-=+_", "中a123abc!@#\n\r(",
                "123456789012345678901234","12345678901234567890123456789012"};
        Base64.Decoder decoder = Base64.getDecoder();
        for (int i = 0; i < plainText_01.length; i++) {
            for (int j = 0; j < aesKey_01.length; j++) {
                String encrypt01 = AESUtil.encrypt(plainText_01[i], aesKey_01[j]);
                Assert.assertNotNull(decoder.decode(encrypt01));
                String decrypt01 = AESUtil.decrypt(encrypt01, aesKey_01[j]);
                Assert.assertEquals(decrypt01, plainText_01[i]);
            }
        }

        // exception：超过规定长度的密钥，加解密密钥不匹配，解密的字符串为非Base64编码的
        String plainText_03[] = {"hello wolrd"};
        String aesKey_03[] = {"1234567890123456333", "abcdefghijklmnopp", "中你好世界aa", "!@#$%^&*()\n\r-=+_@", "中aa123abc!@#\n\r("};
        // 超过规定长度的密钥
        for (int i = 0; i < aesKey_03.length; i++) {
            int i_ = i;
            Assert.assertThrows(CommonException.class, () -> {
                AESUtil.encrypt(plainText_03[0], aesKey_03[i_]);
            });
        }
        for (int i = 0; i < aesKey_03.length; i++) {
            int i_ = i;
            Assert.assertThrows(CommonException.class, () -> {
                AESUtil.decrypt(plainText_03[0], aesKey_03[i_]);
            });
        }
        // 加解密密钥不匹配
        for (int i = 0; i < aesKey_01.length; i++) {
            int i_ = i;
            if (i_ == aesKey_01.length - 1) {
                Assert.assertThrows(BadPaddingException.class, ()->{
                    AESUtil.decrypt(AESUtil.encrypt(plainText_03[0], aesKey_01[i_]), aesKey_01[0]);
                });
            } else {
                Assert.assertThrows(BadPaddingException.class, ()->{
                    AESUtil.decrypt(AESUtil.encrypt(plainText_03[0], aesKey_01[i_]), aesKey_01[i_+1]);
                });
            }
        }
        // 解密的字符串为非Base64编码的
        Assert.assertThrows(IllegalArgumentException.class, ()->{AESUtil.decrypt(plainText_03[0], aesKey_01[0]);});
    }
}