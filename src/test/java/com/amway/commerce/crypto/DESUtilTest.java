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
public class DESUtilTest {

    @Test
    public void testDesOfCbc() throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        // normal：中文、英文、数字、字符，单个字符、长文本；各种长度的密钥；各种长度的IV向量
        // 1、判断加密后的字符串是否为Base64编码过的；
        // 2、判断加密后是否能成功解密
        String longStr = StringUtil.streamToStr(AESUtilTest.class.getResourceAsStream("/DDD.txt"));
        String plainText_01[] = {"中文你好世界！", "hello world HELLO WORLD", "123456", "!@#$%^&*()\n\r", "123中文hello!@#$%\n\r",
                "你", "i", "", " ", "\n\r", longStr};
        String desKey_01[] = {"12345678", "abcdefgh", "你好aa", "!@#$%^&*", "中a1\n\r(",
                "12345678", "abcdefgh", "你好aa", "!@#$%^&*", "中a1\n\r("};
        String desIv_01[] = {"12345678", "abcdefgh", "你好aa", "!@#$%^&*", "中a1\n\r(",
                "12345678", "abcdefgh", "你好aa", "!@#$%^&*", "中a1\n\r("};
        Base64.Decoder decoder = Base64.getDecoder();
        for (int i = 0; i < plainText_01.length; i++) {
            for (int j = 0; j < desKey_01.length; j++) {
                for (int k = 0; k < desIv_01.length; k++) {
                    String encrypt01 = DESUtil.encrypt(plainText_01[i], desKey_01[j], desIv_01[k]);
                    Assert.assertNotNull(decoder.decode(encrypt01));
                    String decrypt01 = DESUtil.decrypt(encrypt01, desKey_01[j], desIv_01[k]);
                    Assert.assertEquals(decrypt01, plainText_01[i]);
                }
            }
        }

        // exception：超过规定长度的密钥和IV向量，加解密密钥不匹配、加解密IV向量不一致，解密的字符串为非Base64编码的
        String plainText_03[] = {"hello wolrd"};
        String desKey_03[] = {"123456789000", "abcdefghff", "你好aaff", "!@#$%^&*@@@", "中a1\n\r(!!!"};
        String desIv_03[] = {"12345678900", "abcdefghff", "你好aaff", "!@#$%^&*@@@", "中a1\n\r(!!!"};
        // 超过规定长度的密钥和IV向量
        for (int i = 0; i < desKey_03.length; i++) {
            for (int k = 0; k < desIv_03.length; k++) {
                int i_ = i;
                int k_ = k;
                Assert.assertThrows(CommonException.class, () -> {
                    DESUtil.encrypt(plainText_03[0], desKey_03[i_], desIv_03[k_]);
                });
                Assert.assertThrows(CommonException.class, () -> {
                    DESUtil.encrypt(plainText_03[0], desKey_01[i_], desIv_03[k_]);
                });
            }
        }
        for (int i = 0; i < desKey_03.length; i++) {
            for (int k = 0; k < desIv_03.length; k++) {
                int i_ = i;
                int k_ = k;
                Assert.assertThrows(CommonException.class, () -> {
                    DESUtil.decrypt(plainText_03[0], desKey_03[i_], desIv_03[k_]);
                });
                Assert.assertThrows(CommonException.class, () -> {
                    DESUtil.decrypt(plainText_03[0], desKey_01[i_], desIv_03[k_]);
                });
            }
        }
        // 加解密密钥不匹配、加解密IV向量不一致
        for (int i = 0; i < desKey_01.length; i++) {
            int i_ = i;
            if (i_ == desKey_01.length - 1) {
                Assert.assertThrows(BadPaddingException.class, ()->{
                    DESUtil.decrypt(DESUtil.encrypt(plainText_03[0], desKey_01[i_], desIv_01[i_]), desKey_01[0], desIv_01[i_]);
                });
                Assert.assertThrows(BadPaddingException.class, ()->{
                    DESUtil.decrypt(DESUtil.encrypt(plainText_03[0], desKey_01[i_], desIv_01[i_]), desKey_01[0], desIv_01[0]);
                });
            } else {
                Assert.assertThrows(BadPaddingException.class, ()->{
                    DESUtil.decrypt(DESUtil.encrypt(plainText_03[0], desKey_01[i_], desIv_01[i_]), desKey_01[i_+1], desIv_01[i_]);
                });
                Assert.assertThrows(BadPaddingException.class, ()->{
                    DESUtil.decrypt(DESUtil.encrypt(plainText_03[0], desKey_01[i_], desIv_01[i_]), desKey_01[i_+1], desIv_01[i_+1]);
                });
            }
        }
        // 解密的字符串为非Base64编码的
        Assert.assertThrows(IllegalArgumentException.class, ()->{DESUtil.decrypt("not base64 encode str", desKey_01[0], desIv_01[0]);});
    }

    @Test
    public void testDesOfEcb() throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        // normal：中文、英文、数字、字符，单个字符、长文本；各种长度的密钥
        // 1、判断加密后的字符串是否为Base64编码过的；
        // 2、判断加密后是否能成功解密；
        String longStr = StringUtil.streamToStr(AESUtilTest.class.getResourceAsStream("/DDD.txt"));
        String plainText_01[] = {"中文你好世界！", "hello world HELLO WORLD", "123456", "!@#$%^&*()\n\r", "123中文hello!@#$%\n\r",
                "你", "i", "", " ", "\n\r", longStr};
        String desKey_01[] = {"12345678", "abcdefgh", "你好aa", "!@#$%^&*", "中a1\n\r(",
                "12345678", "abcdefgh", "你好aa", "!@#$%^&*", "中a1\n\r("};
        Base64.Decoder decoder = Base64.getDecoder();
        for (int i = 0; i < plainText_01.length; i++) {
            for (int j = 0; j < desKey_01.length; j++) {
                String encrypt01 = DESUtil.encrypt(plainText_01[i], desKey_01[j]);
                Assert.assertNotNull(decoder.decode(encrypt01));
                String decrypt01 = DESUtil.decrypt(encrypt01, desKey_01[j]);
                Assert.assertEquals(decrypt01, plainText_01[i]);
            }
        }

        // exception：超过规定长度的密钥，加解密密钥不匹配，解密的字符串为非Base64编码的
        String plainText_03[] = {"hello wolrd"};
        String desKey_03[] = {"1234567890123456333", "abcdefghijklmnopp", "中你好世界aa", "!@#$%^&*()\n\r-=+_@", "中aa123abc!@#\n\r("};
        // 超过规定长度的密钥
        for (int i = 0; i < desKey_03.length; i++) {
            int i_ = i;
            Assert.assertThrows(CommonException.class, () -> {
                AESUtil.encrypt(plainText_03[0], desKey_03[i_]);
            });
        }
        for (int i = 0; i < desKey_03.length; i++) {
            int i_ = i;
            Assert.assertThrows(CommonException.class, () -> {
                AESUtil.decrypt(plainText_03[0], desKey_03[i_]);
            });
        }
        // 加解密密钥不匹配
        for (int i = 0; i < desKey_01.length; i++) {
            int i_ = i;
            if (i_ == desKey_01.length - 1) {
                Assert.assertThrows(BadPaddingException.class, () -> {
                    DESUtil.decrypt(DESUtil.encrypt(plainText_03[0], desKey_01[i_]), desKey_01[0]);
                });

            } else {
                Assert.assertThrows(BadPaddingException.class, () -> {
                    DESUtil.decrypt(DESUtil.encrypt(plainText_03[0], desKey_01[i_]), desKey_01[i_ + 1]);
                });
            }
        }
        // 解密的字符串为非Base64编码的
        Assert.assertThrows(IllegalArgumentException.class, ()->{DESUtil.decrypt("not base64 encode str", desKey_01[0]);});
    }
}