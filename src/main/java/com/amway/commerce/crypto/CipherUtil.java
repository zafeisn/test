package com.amway.commerce.crypto;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author: Jason.Hu
 * @date: 2023-08-18
 * @desc: 密码工具类 - 父类
 */
@Slf4j
public class CipherUtil {

    /**
     * 默认编码格式
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 生成一个加密器
     *
     * @param decryptMode 加密/解密模式
     * @param key         密钥
     * @param ivParameter iv向量（CBC模式）
     * @param charset     key的编码格式
     * @param cipherType  密码器类型
     * @return 密码器
     */
    protected static Cipher doCipher(int decryptMode, String key, String ivParameter, String charset, String cipherType, String keyAlgorithm, String cipherAlgorithm) {
        try {
            // 构造密钥
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(charset), keyAlgorithm);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(cipherType == null ? cipherAlgorithm : cipherType);
            // CBC模式必须传入16字节的 iv向量
            // 初始化加密模式，并将密钥注入到算法中
            cipher.init(decryptMode, keySpec, new IvParameterSpec(ivParameter.getBytes(charset)));
            return cipher;
        } catch (Exception e) {
            log.error("cipher create failed, error message is [{}]", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成一个加密器
     *
     * @param decryptMode 加密/解密模式
     * @param key         密钥
     * @param cipherType  密码器类型
     * @return 密码器
     */
    protected static Cipher doCipher(int decryptMode, String key, String keyAlgorithm, String cipherType) {
        try {
            // 构造密钥
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(DEFAULT_CHARSET), keyAlgorithm);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(cipherType);
            // 初始化加密模式，并将密钥注入到算法中
            cipher.init(decryptMode, keySpec);
            return cipher;
        } catch (Exception e) {
            log.error("cipher create failed, error message is [{}]", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
