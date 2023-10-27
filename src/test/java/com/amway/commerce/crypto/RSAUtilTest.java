package com.amway.commerce.crypto;

import com.amway.commerce.exception.CommonException;
import com.amway.commerce.exception.CommonException;
import com.amway.commerce.string.StringUtil;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Map;

/**
 * @author: Jason.Hu
 * @date: 2023-09-19
 * @desc:
 */
public class RSAUtilTest {

    @Test
    public void testInitKey() throws NoSuchAlgorithmException {
        // normal：512, 1024, 2048的密钥长度，非常规的密钥长度 513, 1025, 2047, 10000, 16384，java层面最大支持16384bit，最小512
        int[] keySize_01 = {512, 1024, 2048, 3072, 4096,
                513, 1025, 2047, 10000, 16384};
        for (int n : keySize_01) {
            Map<String, Key> keyMap = RSAUtil.initKey(n);
            Assert.assertNotNull(keyMap);
            Assert.assertTrue(keyMap.containsKey(RSAUtil.PUBLIC_KEY));
            Assert.assertNotNull(keyMap.get(RSAUtil.PUBLIC_KEY));
            Assert.assertTrue(keyMap.containsKey(RSAUtil.PRIVATE_KEY));
            Assert.assertNotNull(keyMap.get(RSAUtil.PRIVATE_KEY));
        }

        // exception：小于最低密钥长度，超过密钥长度范围，负数
        int[] keySize_03 = {0, 100, 500, 511, 16385, 1000000, -123};
        for (int n : keySize_03) {
            Assert.assertThrows(CommonException.class, ()->{RSAUtil.initKey(n);});
        }

    }

    @Test
    public void testKey() throws Exception {
        // normal：512, 1024, 2048的密钥长度，非常规的密钥长度 513, 1025, 2047, 10000, 16384，java层面最大支持16384bit，最小512
        int[] keySize_01 = {512, 1024, 2048, 3072, 4096,
                513, 1025, 2047, 10000, 16384};
        for (int n : keySize_01) {
            Map<String, Key> keyMap = RSAUtil.initKey(n);
            Key privateKey = keyMap.get(RSAUtil.PRIVATE_KEY);
            Key publicKey = keyMap.get(RSAUtil.PUBLIC_KEY);
            String privateKeyToStr = RSAUtil.keyToStr(privateKey);
            String publicKeyToStr = RSAUtil.keyToStr(publicKey);
            Assert.assertEquals(publicKeyToStr, new String(Base64.getEncoder().encode(publicKey.getEncoded())));
            Assert.assertEquals(privateKeyToStr, new String(Base64.getEncoder().encode(privateKey.getEncoded())));
            Assert.assertEquals(privateKey, RSAUtil.generatePrivate(privateKeyToStr));
            Assert.assertEquals(publicKey, RSAUtil.generatePublic(publicKeyToStr));
        }

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{RSAUtil.generatePrivate(null);});
        Assert.assertThrows(CommonException.class, ()->{RSAUtil.generatePublic(null);});
        Assert.assertThrows(CommonException.class, ()->{RSAUtil.keyToStr(null);});

    }

    @Test
    public void testSignAndVerify() throws Exception {
        // normal：英文、中文、字符、数字；512, 1024, 2048, 3072, 4096的密钥长度
        String[] contents_01 = {"hello world", "你好世界", "HELLO WORLD", ",./！@#", "123456"};
        int[] keySize_01 = {512, 1024, 2048, 3072, 4096};
        for (int n : keySize_01) {
            Map<String, Key> keyMap = RSAUtil.initKey(n);
            Key privateKey = keyMap.get(RSAUtil.PRIVATE_KEY);
            Key publicKey = keyMap.get(RSAUtil.PUBLIC_KEY);
            String privateKeyToStr = RSAUtil.keyToStr(privateKey);
            String publicKeyToStr = RSAUtil.keyToStr(publicKey);
            for (String content : contents_01) {
                String sign = RSAUtil.sign(content, privateKeyToStr);
                Assert.assertTrue(RSAUtil.verify(content, sign, publicKeyToStr));
            }
        }

        // boundary：空值、空格、特殊字符、单个字符、长文本；非常规的密钥长度 513, 1025, 2047, 10000, 16384
        String longStr = StringUtil.streamToStr(RSAUtilTest.class.getResourceAsStream("/DDD.txt"));
        String[] contents_02 = {"", " ", "\n", "a", "中", longStr};
        int[] keySize_02 = {513, 1025, 2047, 10000, 16384};
        for (int n : keySize_02) {
            Map<String, Key> keyMap = RSAUtil.initKey(n);
            Key privateKey = keyMap.get(RSAUtil.PRIVATE_KEY);
            Key publicKey = keyMap.get(RSAUtil.PUBLIC_KEY);
            String privateKeyToStr = RSAUtil.keyToStr(privateKey);
            String publicKeyToStr = RSAUtil.keyToStr(publicKey);
            for (String content : contents_02) {
                String sign = RSAUtil.sign(content, privateKeyToStr);
                Assert.assertTrue(RSAUtil.verify(content, sign, publicKeyToStr));
            }
        }

        // exception：参数为null，非法参数
        // 参数为null
        Assert.assertThrows(CommonException.class, ()->{RSAUtil.sign("null", null);});
        Assert.assertThrows(CommonException.class, ()->{RSAUtil.sign(null, null);});
        Assert.assertThrows(CommonException.class, ()->{RSAUtil.verify("null", "null", null);});
        Assert.assertThrows(CommonException.class, ()->{RSAUtil.sign(null, "null");});
        Assert.assertThrows(CommonException.class, ()->{RSAUtil.verify(null, "", "");});
        // 非法参数
        Assert.assertThrows(InvalidKeySpecException.class, ()->{RSAUtil.verify("null", null, "");});

    }

    @Test
    public void testEncryptAndDecrypt() throws Exception {
        // normal：英文、中文、字符、数字；512, 1024, 2048, 3072, 4096的密钥长度
        String[] contents_01 = {"hello world", "你好世界", "HELLO WORLD", ",./！@#", "123456"};
        int[] keySize_01 = {512, 1024, 2048, 3072, 4096};
        for (int n : keySize_01) {
            Map<String, Key> keyMap = RSAUtil.initKey(n);
            Key privateKey = keyMap.get(RSAUtil.PRIVATE_KEY);
            Key publicKey = keyMap.get(RSAUtil.PUBLIC_KEY);
            for (String content : contents_01) {
                String encrypt = RSAUtil.encrypt(content, (PublicKey) publicKey);
                Assert.assertEquals(content, RSAUtil.decrypt(encrypt, (PrivateKey) privateKey));
            }
        }

        // boundary：空值、空格、特殊字符、单个字符、长文本；非常规的密钥长度 513, 1025, 2047, 10000, 16384
        String longStr = StringUtil.streamToStr(RSAUtilTest.class.getResourceAsStream("/long.txt"));
        String[] contents_02 = {"", " ", "\n", "a", "中", longStr};
        int[] keySize_02 = {513, 1025, 2047, 10000, 16384};
        for (int n : keySize_02) {
            Map<String, Key> keyMap = RSAUtil.initKey(n);
            Key privateKey = keyMap.get(RSAUtil.PRIVATE_KEY);
            Key publicKey = keyMap.get(RSAUtil.PUBLIC_KEY);
            for (String content : contents_02) {
                if (n < 2048 && longStr.equals(content)) {
                    continue;
                }
                String encrypt = RSAUtil.encrypt(content, (PublicKey) publicKey);
                Assert.assertEquals(content, RSAUtil.decrypt(encrypt, (PrivateKey) privateKey));
            }
        }

        // exception：参数为null，加解密密钥不匹配，加密明文过长
        String longStrs = StringUtil.streamToStr(RSAUtilTest.class.getResourceAsStream("/DDD.txt"));
        Assert.assertThrows(CommonException.class, ()->{RSAUtil.decrypt(null, (PrivateKey) RSAUtil.initKey(512).get(RSAUtil.PRIVATE_KEY));});
        Assert.assertThrows(CommonException.class, ()->{RSAUtil.encrypt(null, null);});
        Assert.assertThrows(CommonException.class, ()->{RSAUtil.encrypt(null, (PublicKey) RSAUtil.initKey(512).get(RSAUtil.PUBLIC_KEY));});
        Assert.assertThrows(CommonException.class, ()->{RSAUtil.decrypt(null, null);});
        // 加解密密钥不匹配
        Assert.assertThrows(BadPaddingException.class, ()->{RSAUtil.decrypt("null", (PrivateKey) RSAUtil.initKey(512).get(RSAUtil.PRIVATE_KEY));});
        // 加密明文过长
        Assert.assertThrows(IllegalBlockSizeException.class, ()->{RSAUtil.encrypt(longStrs,  (PublicKey) RSAUtil.initKey(512).get(RSAUtil.PUBLIC_KEY));});


    }
}