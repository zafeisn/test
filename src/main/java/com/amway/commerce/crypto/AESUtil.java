package com.amway.commerce.crypto;

import com.amway.commerce.string.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import javax.crypto.Cipher;

/**
 * @author: Jason.Hu
 * @date: 2023-08-15
 * @desc: AES算法工具类（对称加密算法，采用块加密），CBC（有向量），EBC（无向量）
 */
@Slf4j
public class AESUtil extends CipherUtil {

    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 密码器类型，AES算法/CBC加密模式/填充模式，默认值 AES/CBC/PKCS5Padding
     */
    private static final String CIPHER_AES_CBC_PKCS5 = "AES/CBC/PKCS5Padding";

    /**
     * 密钥类型
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * AES加密算法，CBC模式下需要初始化一个16字节向量与第一个分组明文块进行异或
     *
     * @param plainText  明文字符串
     * @param aesKey     AES密钥
     * @param aesIv      IV向量
     * @param charset    密钥和IV向量的编码格式
     * @param cipherType 密码器类型，若该参数为空，则默认 CIPHER_AES_CBC_PKCS5
     * @return Base64编码的密文
     */
    public static String encrypt(String plainText, String aesKey, String aesIv, String charset, @Nullable String cipherType) {
        // 密钥和 iv向量长度检查
        paramsCheck(aesKey, aesIv);
        byte[] result = null;
        try {
            Cipher cipher = doCipher(Cipher.ENCRYPT_MODE, aesKey, aesIv, charset, cipherType, KEY_ALGORITHM, CIPHER_AES_CBC_PKCS5);
            result = cipher.doFinal(plainText.getBytes(charset));
        } catch (Exception e) {
            log.error("encrypt error, error message is [{}]", e.getMessage());
            throw new RuntimeException(e);
        }
        return Base64Util.encode(result);
    }

    /**
     * AES加密算法，非CBC模式，编码格式默认采用UTF-8，且密码器类型默认采用AES
     *
     * @param plainText 明文字符串
     * @param aesKey    AES密钥
     * @return Base64编码的密文
     */
    public static String encrypt(String plainText, String aesKey) {
        // 密钥和 iv向量长度检查
        keyCheck(aesKey);
        byte[] result = null;
        try {
            Cipher cipher = doCipher(Cipher.ENCRYPT_MODE, aesKey, KEY_ALGORITHM, KEY_ALGORITHM);
            result = cipher.doFinal(plainText.getBytes(DEFAULT_CHARSET));
        } catch (Exception e) {
            log.error("encrypt error, error message is [{}]", e.getMessage());
            throw new RuntimeException(e);
        }
        return Base64Util.encode(result);
    }

    /**
     * AES解密算法，CBC模式下需要初始化一个16字节向量与第一个分组明文块进行异或
     *
     * @param cipherText 密文字符串
     * @param aesKey     AES密钥
     * @param aesIv      IV向量
     * @param charset    密钥和IV向量的编码格式
     * @param cipherType 密码器类型，若该参数为空，则默认 CIPHER_AES_CBC_PKCS5
     * @return 解密后的字符串
     */
    public static String decrypt(String cipherText, String aesKey, String aesIv, String charset, @Nullable String cipherType) {
        // 密钥和 iv向量长度检查
        paramsCheck(aesKey, aesIv);
        byte[] result = null;
        try {
            Cipher cipher = doCipher(Cipher.DECRYPT_MODE, aesKey, aesIv, charset, cipherType, KEY_ALGORITHM, CIPHER_AES_CBC_PKCS5);
            result = cipher.doFinal(Base64Util.decode(cipherText));
        } catch (Exception e) {
            log.error("decrypt error, error message is [{}]", e.getMessage());
            throw new RuntimeException(e);
        }
        return new String(result);
    }

    /**
     * AES解密算法，非CBC模式，编码格式默认采用UTF-8，且密码器类型默认采用AES
     *
     * @param cipherText 密文字符串
     * @param aesKey     AES密钥
     * @return 解密后的字符串
     */
    public static String decrypt(String cipherText, String aesKey) {
        byte[] result = null;
        keyCheck(aesKey);
        try {
            Cipher cipher = doCipher(Cipher.DECRYPT_MODE, aesKey, KEY_ALGORITHM, KEY_ALGORITHM);
            result = cipher.doFinal(Base64Util.decode(cipherText.getBytes(DEFAULT_CHARSET)));
        } catch (Exception e) {
            log.error("decrypt error, error message is [{}]", e.getMessage());
            throw new RuntimeException(e);
        }
        return new String(result);
    }

    /**
     * 密钥长度检查
     *
     * @param key 密钥
     */
    protected static void keyCheck(String key) {
        int keyLength = StringUtil.length(key);
        if (keyLength != 16 && keyLength != 24 && keyLength != 32) {
            log.info("the length of the key is invalid, it should be 16, 24, or 32 bytes");
        }
    }

    /**
     * iv向量长度检查
     *
     * @param iv iv向量
     */
    protected static void paramsCheck(String key, String iv) {
        keyCheck(key);
        if (StringUtil.length(iv) != 16) {
            log.info("the length of ivParameter is invalid, it should be 16 bytes");
        }
    }

}
