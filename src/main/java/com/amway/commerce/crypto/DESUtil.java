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
 * @date: 2023-08-18
 */
@Slf4j
public class DESUtil extends CipherUtil {

    /**
     * 密码器算法类型，DES算法/CBC加密模式/填充模式
     */
    private static final String CIPHER_DES_CBC_PKCS5 = "DES/CBC/PKCS5Padding";
    /**
     * 密码器算法类型，DES算法/ECB加密模式/填充模式，该类型也是 Java默认的加密模式
     */
    private static final String CIPHER_DES_EBC_PKCS5 = "DES/ECB/PKCS5Padding";
    /**
     * 密钥算法类型
     */
    private static final String KEY_ALGORITHM = "DES";
    /**
     * 默认字符编码格式
     */
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    /**
     * DES密钥长度
     */
    private static final int KEY_LEN = 8;
    /**
     * 在 CBC模式下，DES加解密时所需 IV向量的长度
     */
    private static final int IV_LEN = 8;



    /**
     * DES算法加密，使用 CBC(Cipher Block Chaining)密码分组链接模式和 PKCS5Padding(Public Key Cryptography Standards #5 Padding)明文填充模式，默认使用 UTF_8的字符编码格式。
     * 该方法需要对参数进行长度校验，若 desKey或 desIv长度不符合要求，则抛出参数长度不正确异常。
     *
     * @param plainText 明文字符串
     * @param desKey    DES密钥字符串，长度只能为 8个字节
     * @param desIv     IV向量，长度只能为 8个字节
     * @return Base64编码的密文字符串
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     *
     * <p>
     * <b>例：</b><br>
     * plainText="hello world"，desKey="abcde"，desIv="abcdefgh"，抛出“参数长度不正确”异常提示信息；<p>
     * plainText="hello world"，desKey="abcdefgh"，desIv="abcde"，抛出“参数长度不正确”异常提示信息；<p>
     * plainText="hello world"，desKey="abcdefgh"，desIv="abcdefgh"，返回 WGV4vfylUSWoKHlSWvOFPw==。
     */
    public static String encrypt(String plainText, String desKey, String desIv) throws IllegalBlockSizeException, BadPaddingException {
        // 密钥和 IV向量长度检查
        keyCheck(desKey, KEY_LEN);
        ivCheck(desIv,  IV_LEN);
        // 创建密码器
        Cipher cipher = doCipher(Cipher.ENCRYPT_MODE, desKey, desIv, KEY_ALGORITHM, CIPHER_DES_CBC_PKCS5);
        // 执行加密算法
        byte[] result = cipher.doFinal(plainText.getBytes(UTF_8));
        return Base64Util.encode(result);
    }

    /**
     * DES算法解密，使用 CBC(Cipher Block Chaining)密码分组链接模式和 PKCS5Padding(Public Key Cryptography Standards #5 Padding)明文填充模式，默认使用 UTF_8的字符编码格式。
     * 该方法需要对参数进行长度校验，若 desKey或 desIv长度不符合要求，则抛出参数长度不正确异常。
     *
     * @param cipherText Base64编码的密文字符串
     * @param desKey     DES密钥字符串，长度只能为 8个字节，且需与加密时使用的密钥保持一致
     * @param desIv      IV向量，长度只能为 8个字节，且需与加密时使用的 IV向量保持一致
     * @return 明文字符串
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     *
     * <p>
     * <b>例：</b><br>
     * cipherText="WGV4vfylUSWoKHlSWvOFPw=="，aesKey="abcde"，aesIv="abcdefgh"，抛出“参数长度不正确”异常提示信息；<p>
     * cipherText="WGV4vfylUSWoKHlSWvOFPw=="，aesKey="abcdefgh"，aesIv="abcde"，抛出“参数长度不正确”异常提示信息；<p>
     * cipherText="WGV4vfylUSWoKHlSWvOFPw=="，aesKey="abcdefgh"，aesIv="abcdefgh"，返回 hello world。
     */
    public static String decrypt(String cipherText, String desKey, String desIv) throws IllegalBlockSizeException, BadPaddingException {
        // 密钥和 IV向量长度检查
        keyCheck(desKey, KEY_LEN);
        ivCheck(desIv, IV_LEN);
        // 创建密码器
        Cipher cipher = doCipher(Cipher.DECRYPT_MODE, desKey, desIv, KEY_ALGORITHM, CIPHER_DES_CBC_PKCS5);
        // 执行解密算法
        byte[] result = cipher.doFinal(Base64Util.decode(cipherText));
        return new String(result);
    }

    /**
     * DES算法加密，使用 ECB(Electronic Codebook Book)电码本模式和 PKCS5Padding(Public Key Cryptography Standards #5 Padding)明文填充模式，默认使用 UTF_8的字符编码格式。
     * 该方法需要对参数进行长度校验，若 desKey不符合要求，则抛出参数长度不正确异常。
     *
     * @param plainText 明文字符串
     * @param desKey    DES密钥字符串，长度只能为 8个字节
     * @return Base64编码的密文字符串
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     *
     * <p>
     * <b>例：</b><br>
     * plainText="hello world"，desKey="abcde"，抛出“参数长度不正确”异常提示信息；<p>
     * plainText="hello world"，desKey="abcdefgh"，返回 fGLCPlyz00PCrsA==。
     */
    public static String encrypt(String plainText, String desKey) throws IllegalBlockSizeException, BadPaddingException {
        // 密钥长度检查
        keyCheck(desKey, KEY_LEN);
        // 创建密码器
        Cipher cipher = doCipher(Cipher.ENCRYPT_MODE, desKey, KEY_ALGORITHM, CIPHER_DES_EBC_PKCS5);
        // 执行加密算法
        byte[] result = cipher.doFinal(plainText.getBytes(UTF_8));
        return Base64Util.encode(result);
    }

    /**
     * DES算法解密，使用 ECB(Electronic Codebook Book)电码本模式和 PKCS5Padding(Public Key Cryptography Standards #5 Padding)明文填充模式，默认使用 UTF_8的字符编码格式。
     * 该方法需要对参数进行长度校验，若 desKey或 desIv长度不符合要求，则抛出参数长度不正确异常。
     *
     * @param cipherText Base64编码的密文字符串
     * @param desKey     DES密钥字符串，长度只能为 8个字节，需与加密时使用的密钥保持一致
     * @return 明文字符串
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     *
     * <p>
     * <b>例：</b><br>
     * cipherText="xyDmrL+fGLCPlyz00PCrsA=="，aesKey="abcde"，抛出“参数长度不正确”异常提示信息；<p>
     * cipherText="xyDmrL+fGLCPlyz00PCrsA=="，aesKey="abcdefgh"，返回 hello world。
     */
    public static String decrypt(String cipherText, String desKey) throws IllegalBlockSizeException, BadPaddingException {
        // 密钥长度检查
        keyCheck(desKey, KEY_LEN);
        // 创建密码器
        Cipher cipher = doCipher(Cipher.DECRYPT_MODE, desKey, KEY_ALGORITHM, CIPHER_DES_EBC_PKCS5);
        // 执行解密算法
        byte[] result = cipher.doFinal(Base64Util.decode(cipherText));
        return new String(result);
    }

}
