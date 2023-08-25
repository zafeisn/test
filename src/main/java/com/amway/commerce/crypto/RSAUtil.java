package com.amway.commerce.crypto;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Jason.Hu
 * @date: 2023-08-15
 * @desc: RSA算法工具类（非对称加密算法）
 */
@Slf4j
public class RSAUtil {

    /**
     * 密钥类型
     */
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 密码器类型，RSA算法/ECB加密模式/填充模式
     */
    private static final String CIPHER_RSA_ECB_PKCS1 = "RSA/ECB/PKCS1Padding";

    /**
     * RSA签名算法类型
     */
    private static final String ALG_SIGNATURE = "SHA256withRSA";

    /**
     * 公钥键名
     */
    public static final String PUBLIC_KEY = "rsaPublicKey";

    /**
     * 私钥键名
     */
    public static final String PRIVATE_KEY = "rsaPrivateKey";

    /**
     * 生成 RSA密钥对
     *
     * @param keySize 密钥长度
     * @return 密钥集合
     */
    public static Map<String, Key> initKey(int keySize) throws NoSuchAlgorithmException {
        if (keySize < 512) {
            log.error("keySize is invalid, it should be at least 512 bit. Suggest using 1024.");
        }
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        // 设置密钥对的bit数
        keyPairGen.initialize(keySize);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 获取公钥、私钥
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        // 存入 keyMap
        Map<String, Key> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 从公钥字符串中获取公钥
     *
     * @param publicKey Base64编码后的公钥字符串
     * @return 公钥
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = Base64Util.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 从私钥字符串中获取私钥
     *
     * @param privateKey Base64编码后的私钥字符串
     * @return 私钥
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = Base64Util.decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * RSA签名算法
     *
     * @param content    待签名数据
     * @param privateKey RSA私钥
     * @param charset    编码格式
     * @return 签名值
     */
    public static String sign(String content, String privateKey, String charset) throws Exception {
        Signature signature = Signature.getInstance(ALG_SIGNATURE);
        signature.initSign(getPrivateKey(privateKey));
        signature.update(content.getBytes(charset));
        return Base64Util.encode(signature.sign());
    }

    /**
     * RSA验签算法
     *
     * @param content   验签内容
     * @param sign      签名值
     * @param publicKey RSA公钥
     * @param charset   编码格式
     * @return 验签结果
     */
    public static boolean verify(String content, String sign, String publicKey, String charset) throws Exception {
        Signature signature = Signature.getInstance(ALG_SIGNATURE);
        signature.initVerify(getPublicKey(publicKey));
        signature.update(content.getBytes(charset));
        return signature.verify(Base64Util.decode(sign));
    }

    /**
     * RSA公钥加密算法
     *
     * @param plainText 明文字符串
     * @param publicKey RSA公钥
     * @param charset   编码格式
     * @return 加密后的密文字符串
     */
    public static String encrypt(String plainText, PublicKey publicKey, String charset) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_RSA_ECB_PKCS1);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(plainText.getBytes(charset));
        return Base64Util.encode(bytes);
    }

    /**
     * RSA私钥解密算法
     *
     * @param cipherText 密文字符串
     * @param privateKey RSA私钥
     * @return 解密后的明文字符串
     */
    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] decode = Base64Util.decode(cipherText);
        Cipher cipher = Cipher.getInstance(CIPHER_RSA_ECB_PKCS1);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(decode));
    }
}
