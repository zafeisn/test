package com.amway.commerce.crypto;

import com.amway.commerce.string.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import javax.crypto.Cipher;

/**
 * @author: Jason.Hu
 * @date: 2023-08-18
 * @desc: DES工具类
 */
@Slf4j
public class DESUtil extends CipherUtil {

    /**
     * 密码器类型，DES算法/ECB加密模式/填充模式
     */
    private static final String CIPHER_DES_CBC_PKCS5 = "DES/CBC/PKCS5Padding";

    /**
     * 密钥类型
     */
    private static final String KEY_ALGORITHM = "DES";

    /**
     * DES加密，CBC模式需要提供一个16字节的IV向量
     *
     * @param plainText  明文数据
     * @param desKey     DES密钥，长度为 8的倍数
     * @param desIv      IV向量
     * @param charset    密钥的字符编码格式
     * @param cipherType 密码器类型
     * @return 加密后的数据
     */
    public static byte[] encrypt(byte[] plainText, String desKey, String desIv, String charset, @Nullable String cipherType) {
        // key和 IV向量长度检查
        paramsCheck(desKey, desIv);
        byte[] result = null;
        try {
            Cipher cipher = doCipher(Cipher.ENCRYPT_MODE, desKey, desIv, charset, cipherType, KEY_ALGORITHM, CIPHER_DES_CBC_PKCS5);
            result = cipher.doFinal(plainText);
        } catch (Exception e) {
            log.error("encrypt error, error message is [{}]", e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * DES加密，非 CBC模式，字符编码格式默认采用UTF-8,
     *
     * @param plainText 明文数据
     * @param desKey    DES密钥，长度为 8的倍数
     * @return 加密后的数据
     */
    public static byte[] encrypt(byte[] plainText, String desKey) {
        keyCheck(desKey);
        byte[] result = null;
        try {
            Cipher cipher = doCipher(Cipher.ENCRYPT_MODE, desKey, KEY_ALGORITHM, KEY_ALGORITHM);
            result = cipher.doFinal(plainText);
        } catch (Exception e) {
            log.error("encrypt error, error message is [{}]", e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * DES解密，CBC模式需要提供一个16字节的IV向量
     *
     * @param cipherText 密文数据
     * @param desKey     DES密钥，长度为 8的倍数
     * @param desIv      IV向量
     * @param charset    密钥的字符编码格式
     * @param cipherType 密码器类型
     * @return 解密后的数据
     */
    public static byte[] decrypt(byte[] cipherText, String desKey, String desIv, String charset, @Nullable String cipherType) {
        paramsCheck(desKey, desIv);
        byte[] result = null;
        try {
            Cipher cipher = doCipher(Cipher.DECRYPT_MODE, desKey, desIv, charset, cipherType, KEY_ALGORITHM, CIPHER_DES_CBC_PKCS5);
            result = cipher.doFinal(cipherText);
        } catch (Exception e) {
            log.error("encrypt error, error message is [{}]", e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * DES解密，非 CBC模式
     *
     * @param cipherText 密文数据
     * @param desKey     DES密钥，长度为 8的倍数
     * @return 解密后的数据
     */
    public static byte[] decrypt(byte[] cipherText, String desKey) {
        keyCheck(desKey);
        byte[] result = null;
        try {
            Cipher cipher = doCipher(Cipher.DECRYPT_MODE, desKey, KEY_ALGORITHM, KEY_ALGORITHM);
            result = cipher.doFinal(cipherText);
        } catch (Exception e) {
            log.error("encrypt error, error message is [{}]", e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 密钥长度检查
     *
     * @param key 密钥
     */
    protected static void keyCheck(String key) {
        int keyLength = StringUtil.length(key);
        if (keyLength != 8) {
            log.info("the length of the key is invalid, it should be 8 bytes");
        }
    }

    /**
     * iv向量长度检查
     *
     * @param iv iv向量
     */
    protected static void paramsCheck(String key, String iv) {
        keyCheck(key);
        if (StringUtil.length(iv) != 8) {
            log.info("the length of ivParameter is invalid, it should be 8 bytes");
        }
    }


}
