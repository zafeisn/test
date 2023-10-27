package com.amway.commerce.crypto;

import lombok.extern.slf4j.Slf4j;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author: Jason.Hu
 * @date: 2023-08-15
 */
@Slf4j
public class AESUtil extends CipherUtil {

    /**
     * 密码器算法类型，AES算法/CBC加密模式/填充模式，默认值 AES/CBC/PKCS5Padding
     */
    private static final String CIPHER_AES_CBC_PKCS5 = "AES/CBC/PKCS5Padding";
    /**
     * 密码器算法类型，AES算法/ECB加密模式/填充模式，默认值 AES/ECB/PKCS5Padding，该类型为 Java默认的加密模式
     */
    private static final String CIPHER_AES_ECB_PKCS5 = "AES/ECB/PKCS5Padding";
    /**
     * 密钥算法类型
     */
    private static final String KEY_ALGORITHM = "AES";
    /**
     * 默认字符编码格式
     */
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    /**
     * AES密钥长度
     */
    private static final int KEY_LEN1 = 16, KEY_LEN2 = 24, KEY_LEN3 = 32;
    /**
     * 在 CBC模式下，AES加解密时所需 IV向量的长度
     */
    private static final int IV_LEN = 16;

    /**
     * AES算法加密，使用 CBC(Cipher Block Chaining)密码分组链接模式和 PKCS5Padding(Public Key Cryptography Standards #5 Padding)明文填充模式，默认使用 UTF_8的字符编码格式。
     * 该方法需要对参数进行长度校验，若 aesKey或 aesIv长度不符合要求，则抛出参数长度不正确异常。
     *
     * @param plainText 明文字符串
     * @param aesKey    AES密钥字符串，长度只能为 16/24/32个字节
     * @param aesIv     IV向量，长度只能为 16个字节
     * @return Base64编码的密文字符串
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     *
     * <p>
     * <b>例：</b><br>
     * plainText="hello world"，aesKey="abcde"，aesIv="abcdefghijklmnop"，抛出“参数长度不正确”异常提示信息；<p>
     * plainText="hello world"，aesKey="abcdefghijklmnop"，aesIv="abcde"，抛出“参数长度不正确”异常提示信息；<p>
     * plainText="hello world"，aesKey="abcdefghijklmnop"，aesIv="abcdefghijklmnop"，返回 1dGGFwB4VKhqD6jGCIwT7Q==。
     */
    public static String encrypt(String plainText, String aesKey, String aesIv) throws IllegalBlockSizeException, BadPaddingException {
        // 密钥和 iv向量长度检查
        keyCheck(aesKey, KEY_LEN1, KEY_LEN2, KEY_LEN3);
        ivCheck(aesIv, IV_LEN);
        // 创建密码器
        Cipher cipher = doCipher(Cipher.ENCRYPT_MODE, aesKey, aesIv, KEY_ALGORITHM, CIPHER_AES_CBC_PKCS5);
        // 执行加密算法
        byte[] result = cipher.doFinal(plainText.getBytes(UTF_8));
        return Base64Util.encode(result);
    }

    /**
     * AES算法解密，使用 CBC(Cipher Block Chaining)密码分组链接模式和 PKCS5Padding(Public Key Cryptography Standards #5 Padding)明文填充模式，默认使用 UTF_8的字符编码格式。
     * 该方法需要对参数进行长度校验，若 aesKey或 aesIv长度不符合要求，则抛出参数长度不正确异常。
     *
     * @param cipherText Base64编码的密文字符串
     * @param aesKey     AES密钥字符串，长度只能为 16/24/32个字节，且需与加密时使用的密钥保持一致
     * @param aesIv      IV向量，长度只能为 16个字节，且需与加密时使用的 IV向量保持一致
     * @return 明文字符串
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     *
     * <p>
     * <b>例：</b><br>
     * cipherText="1dGGFwB4VKhqD6jGCIwT7Q=="，aesKey="abcde"，aesIv="abcdefghijklmnop"，抛出“参数长度不正确”异常提示信息；<p>
     * cipherText="1dGGFwB4VKhqD6jGCIwT7Q=="，aesKey="abcdefghijklmnop"，aesIv="abcde"，抛出“参数长度不正确”异常提示信息；<p>
     * cipherText="1dGGFwB4VKhqD6jGCIwT7Q=="，aesKey="abcdefghijklmnop"，aesIv="abcdefghijklmnop"，返回 hello world。
     */
    public static String decrypt(String cipherText, String aesKey, String aesIv) throws IllegalBlockSizeException, BadPaddingException {
        // 密钥和 iv向量长度检查
        keyCheck(aesKey, KEY_LEN1, KEY_LEN2, KEY_LEN3);
        ivCheck(aesIv, IV_LEN);
        // 创建密码器
        Cipher cipher = doCipher(Cipher.DECRYPT_MODE, aesKey, aesIv, KEY_ALGORITHM, CIPHER_AES_CBC_PKCS5);
        // 执行解密算法
        byte[] result = cipher.doFinal(Base64Util.decode(cipherText));
        return new String(result);
    }

    /**
     * AES算法加密，使用 ECB(Electronic Codebook Book)电码本模式和 PKCS5Padding(Public Key Cryptography Standards #5 Padding)明文填充模式，默认使用 UTF_8的字符编码格式。
     * 该方法需要对参数进行长度校验，若 aesKey长度不符合要求，则抛出参数长度不正确异常。
     *
     * @param plainText 明文字符串
     * @param aesKey    AES密钥字符串，长度只能为 16/24/32个字节
     * @return Base64编码的密文字符串
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     *
     * <p>
     * <b>例：</b><br>
     * plainText="hello world"，aesKey="abcde"，抛出“参数长度不正确”异常提示信息；<p>
     * plainText="hello world"，aesKey="abcdefghijklmnop"，返回 1dGGFwB4VKhqD6jGCIwT7Q==。
     */
    public static String encrypt(String plainText, String aesKey) throws IllegalBlockSizeException, BadPaddingException {
        // 密钥长度检查
        keyCheck(aesKey, KEY_LEN1, KEY_LEN2, KEY_LEN3);
        // 创建密码器
        Cipher cipher = doCipher(Cipher.ENCRYPT_MODE, aesKey, KEY_ALGORITHM, CIPHER_AES_ECB_PKCS5);
        // 执行加密算法
        byte[] result = cipher.doFinal(plainText.getBytes(UTF_8));
        return Base64Util.encode(result);
    }

    /**
     * AES算法解密，使用 ECB(Electronic Codebook Book)电码本模式和 PKCS5Padding(Public Key Cryptography Standards #5 Padding)明文填充模式，默认使用 UTF_8的字符编码格式。
     * 该方法需要对参数进行长度校验，若 aesKey长度不符合要求，则抛出参数长度不正确异常。
     *
     * @param cipherText Base64编码的密文字符串
     * @param aesKey     AES密钥字符串，长度只能为 16/24/32个字节，且需与加密时使用的密钥保持一致
     * @return 明文字符串
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     *
     * <p>
     * <b>例：</b><br>
     * cipherText="1dGGFwB4VKhqD6jGCIwT7Q=="，aesKey="abcde"，抛出“参数长度不正确”异常提示信息；<p>
     * cipherText="1dGGFwB4VKhqD6jGCIwT7Q=="，aesKey="abcdefghijklmnop"，返回 hello world。
     */
    public static String decrypt(String cipherText, String aesKey) throws IllegalBlockSizeException, BadPaddingException {
        // 密钥长度检查
        keyCheck(aesKey, KEY_LEN1, KEY_LEN2, KEY_LEN3);
        // 创建密码器
        Cipher cipher = doCipher(Cipher.DECRYPT_MODE, aesKey, KEY_ALGORITHM, CIPHER_AES_ECB_PKCS5);
        // 执行解密算法
        byte[] result = cipher.doFinal(Base64Util.decode(cipherText.getBytes(UTF_8)));
        return new String(result);
    }

}
