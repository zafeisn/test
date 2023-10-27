package com.amway.commerce.crypto;

import com.amway.commerce.exception.CommonError;
import com.amway.commerce.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Jason.Hu
 * @date: 2023-08-18
 */
@Slf4j

public class CipherUtil {

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * 创建 CBC模式下的 Cipher密码器，默认使用 UTF_8的字符编码格式。
     *
     * @param decryptMode  密码器类型，int类型
     * @param key          密钥字符串
     * @param ivParameter  IV向量字符串
     * @param keyAlgorithm 密钥算法类型
     * @param cipherType   密码器算法类型
     * @return 初始化后的 Cipher密码器
     */
    protected static Cipher doCipher(int decryptMode, String key, String ivParameter, String keyAlgorithm, String cipherType) {
        try {
            // 构造密钥
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(UTF_8), keyAlgorithm);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(cipherType);
            // CBC模式必须传入 16字节的 iv向量
            // 初始化加密模式，并将密钥注入到算法中
            cipher.init(decryptMode, keySpec, new IvParameterSpec(ivParameter.getBytes(UTF_8)));
            return cipher;
        } catch (Exception e) {
            log.error("cipher create failed, error message is [{}]", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建 ECB模式下的 Cipher密码器，默认使用 UTF_8的字符编码格式。
     *
     * @param decryptMode  密码器类型，int类型
     * @param key          密钥字符串
     * @param keyAlgorithm 密钥算法类型
     * @param cipherType   密码器算法类型
     * @return 初始化后的 Cipher密码器
     */
    protected static Cipher doCipher(int decryptMode, String key, String keyAlgorithm, String cipherType) {
        try {
            // 构造密钥
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(UTF_8), keyAlgorithm);
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

    protected static void keyCheck(String key, Integer... keyLengths) {
        // 获取 key的字节长度
        int keyLength = key.getBytes(UTF_8).length;
        List<Integer> unAvailableKeyList = new ArrayList<>();
        // 遍历可用的密钥字节长度，判断 key的字节长度是否符合其中一个
        for (Integer len : keyLengths) {
            // 如果不符合，则存入 unAvailableKeyList
            if (keyLength != len) {
                unAvailableKeyList.add(len);
            }
        }
        // 若 unAvailableKeyList集合大小等于 keyLengths.length，则说明没有符合字节长度要求的 key
        if (unAvailableKeyList.size() == keyLengths.length) {
            // 打印错误日志
            log.error("the length of the key is invalid, it should meet one of the values in " + Arrays.toString(unAvailableKeyList.toArray()) + " list.");
            throw new CommonException(CommonError.ParamLengthInvalid.getMessage());
        }
    }

    protected static void ivCheck(String iv, int ivLen) {
        // 判断 iv的字节长度是否等于规定的长度
        if (iv.getBytes(UTF_8).length != ivLen) {
            // 打印错误日志
            log.error("the length of ivParameter is invalid, it should be " + ivLen + " bytes.");
            throw new CommonException(CommonError.ParamLengthInvalid.getMessage());
        }
    }
}
