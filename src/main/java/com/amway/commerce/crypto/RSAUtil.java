package com.amway.commerce.crypto;

import com.amway.commerce.exception.CommonError;
import com.amway.commerce.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Jason.Hu
 * @date: 2023-08-15
 */
@Slf4j
public class RSAUtil {

    /**
     * 密码器算法类型，RSA算法/ECB加密模式/填充模式
     */
    private static final String CIPHER_RSA_ECB_PKCS1 = "RSA/ECB/PKCS1Padding";
    /**
     * RSA签名算法类型
     */
    private static final String ALG_SIGNATURE = "SHA256withRSA";
    /**
     * 密钥算法类型
     */
    private static final String KEY_ALGORITHM = "RSA";
    /**
     * 公钥键名
     */
    public static final String PUBLIC_KEY = "rsaPublicKey";
    /**
     * 私钥键名
     */
    public static final String PRIVATE_KEY = "rsaPrivateKey";
    /**
     * 默认字符编码格式
     */
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    /**
     * 密钥最小长度
     */
    private static final int KEY_MIN_LEN = 512;
    /**
     * 密钥最大长度
     */
    private static final int KEY_MAX_LEN = 16384;

    /**
     * 生成指定密钥长度 keySize的 RSA密钥对，<b>密钥的长度会影响加密数据的长度，加密数据最大字节数 =（密钥长度(以位为单位)/8）-11</b>。
     * 该方法需要对参数值进行校验，若 keySize超过指定范围，则抛出“参数值超过指定范围”异常。
     *
     * @param keyLen 密钥长度，不小于 512且不超过 16384
     * @return 密钥 map集合，公钥键名为 PUBLIC_KEY，私钥键名为 PRIVATE_KEY
     * @throws NoSuchAlgorithmException
     *
     * <p>
     * <b>例：</b><br>
     * keySize=511，抛出“参数值超过指定范围”异常提示信息；<p>
     * keySize=512，返回 map集合，publicKey=map.get(PUBLIC_KEY)，privateKey=map.get(PRIVATE_KEY)。
     */
    public static Map<String, Key> initKey(int keyLen) throws NoSuchAlgorithmException {
        // 密钥长度校验
        if (keyLen < KEY_MIN_LEN || keyLen > KEY_MAX_LEN) {
            throw new CommonException(CommonError.ParamValueExceedRange.getMessage());
        }
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        // 设置密钥对的 bit数
        keyPairGen.initialize(keyLen);
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
     * 将 Key类型的密钥转换为 Base64编码后的密钥字符串，默认使用 UTF_8的字符编码格式。
     * 该方法需要对参数进行校验，若 key为空，则抛出参数不能为空异常。
     *
     * @param key Key类型的密钥，不可为空
     * @return Base64编码的密钥字符串
     *
     * <p>
     * <b>例：</b><br>
     * key=null，抛出“参数不能为空”异常提示信息；<p>
     * key=公钥/私钥，返回 Base64编码的密钥字符串。
     */
    public static String keyToStr(Key key) {
        isNotNull(key);
        return new String(Base64Util.encode(key.getEncoded()).getBytes(UTF_8));
    }

    /**
     * 将 Base64编码的公钥字符串转换成 PublicKey类型的公钥对象。
     * 该方法需要对参数进行非空判断，若 publicKey为空，则抛出参数不能为空异常。
     *
     * @param publicKey Base64编码的公钥字符串，不可为空
     * @return 公钥对象，PublicKey类型
     *
     * <p>
     * <b>例：</b><br>
     * publicKey=null，抛出“参数不能为空”异常提示信息；<p>
     * publicKey="Base64编码的公钥字符串"，返回 PublicKey类型公钥对象。
     */
    public static PublicKey generatePublic(String publicKey) throws Exception {
        // Base64解码
        byte[] keyBytes = Base64Util.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // 获取 RSA算法的密钥工厂对象
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 生成公钥对象并返回
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 将 Base64编码的私钥字符串转换成 PrivateKey类型的私钥对象。
     * 该方法需要对参数进行非空判断，若 privateKey为空，则抛出参数不能为空异常。
     *
     * @param privateKey Base64编码的私钥字符串，不可为空
     * @return 私钥对象，PrivateKey类型
     *
     * <p>
     * <b>例：</b><br>
     * privateKey=null，抛出“参数不能为空”异常提示信息；<p>
     * privateKey="Base64编码的私钥字符串"，返回 PrivateKey类型私钥对象。
     */
    public static PrivateKey generatePrivate(String privateKey) throws Exception {
        // Base64解码
        byte[] keyBytes = Base64Util.decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        // 获取 RSA算法的密钥工厂对象
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 生成私钥对象并返回
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * RSA签名算法，通过 Base64编码的私钥字符串将待签名字符串生成 RSA签名信息，默认使用 UTF_8的字符编码格式。
     * 该方法需要对参数进行非空判断，若 content或 privateKey为空，则抛出参数不能为空异常。
     *
     * @param content    待签名字符串，不可为空
     * @param privateKey Base64编码的私钥字符串，不可为空
     * @return RSA签名信息
     *
     * <p>
     * <b>例：</b><br>
     * content=null，privateKey="Base64编码的私钥字符串"，抛出“参数不能为空”异常提示信息；<p>
     * content="待签名字符串"，privateKey=null，抛出“参数不能为空”异常提示信息；<p>
     * content="待签名字符串"，privateKey="Base64编码的私钥字符串"，返回签名值。
     */
    public static String sign(String content, String privateKey) throws Exception {
        isNotNull(content);
        // 创建 RSA签名算法对象
        Signature signature = Signature.getInstance(ALG_SIGNATURE);
        // 使用私钥对象完成算法初始化
        signature.initSign(generatePrivate(privateKey));
        // 注入数据
        signature.update(content.getBytes(UTF_8));
        // 对签名结果进行 Base64编码并返回
        return Base64Util.encode(signature.sign());
    }

    /**
     * RSA验签算法，通过 Base64编码的公钥字符串将待签名数据与 RSA签名信息进行验证，默认使用 UTF_8的字符编码格式。
     * 该方法需要对参数进行非空判断，若 content或 sign或 privateKey为空，则抛出参数不能为空异常。
     *
     * @param content   待签名字符串，不可为空
     * @param sign      RSA签名字符串，不可为空
     * @param publicKey RSA公钥字符串，不可为空
     * @return 布尔值，true表示验签通过
     *
     * <p>
     * <b>例：</b><br>
     * content=null，sign="签名值"，publicKey="Base64编码后的公钥字符串"，抛出“参数不能为空”异常提示信息；<p>
     * content="待签名字符串"，sign=null，publicKey="Base64编码后的公钥字符串"，抛出“参数不能为空”异常提示信息；<p>
     * content="待签名字符串"，sign="签名值"，publicKey=null，抛出“参数不能为空”异常提示信息；<p>
     * content="待签名字符串"，sign="签名值"，publicKey="Base64编码后的公钥字符串"，返回验签结果。
     */
    public static boolean verify(String content, String sign, String publicKey) throws Exception {
        isNotNull(content);
        // 创建 RSA签名算法对象
        Signature signature = Signature.getInstance(ALG_SIGNATURE);
        // 使用公钥对象完成算法初始化
        signature.initVerify(generatePublic(publicKey));
        // 注入数据
        signature.update(content.getBytes(UTF_8));
        // 将签名数据进行 Base64解密并验签
        return signature.verify(Base64Util.decode(sign));
    }

    /**
     * RSA加密算法，使用公钥对象将明文字符串进行 RSA加密，默认使用 UTF_8的字符编码格式。
     * 该方法需要对参数进行非空判断，若 plainText或 publicKey为空，则抛出参数不能为空异常。
     *
     * @param plainText 明文字符串
     * @param publicKey RSA公钥对象，PublicKey类型
     * @return 经过 Base64编码的 RSA密文字符串
     *
     * <p>
     * <b>例：</b><br>
     * plainText=null，publicKey=公钥，抛出“参数不能为空”异常提示信息；<p>
     * plainText="hello world"，publicKey=null，抛出“参数不能为空”异常提示信息；<p>
     * plainText="hello world"，publicKey=公钥，返回加密后经过 Base64编码的密文字符串。
     */
    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        isNotNull(plainText, publicKey);
        // 获取密码器类型
        Cipher cipher = Cipher.getInstance(CIPHER_RSA_ECB_PKCS1);
        // 初始化加密模式，将公钥注入到算法中
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 执行加密算法
        byte[] bytes = cipher.doFinal(plainText.getBytes(UTF_8));
        return Base64Util.encode(bytes);
    }

    /**
     * RSA解密算法，使用私钥对象对密文字符串进行 RSA解密。
     * 该方法需要对参数进行非空判断，若 cipherText或 privateKey为空，则抛出参数不能为空异常。
     *
     * @param cipherText 密文字符串
     * @param privateKey RSA私钥对象，PrivateKey类型
     * @return 明文字符串
     *
     * <p>
     * <b>例：</b><br>
     * cipherText=null，privateKey=私钥，抛出“参数不能为空”异常提示信息；<p>
     * cipherText=密文字符串，privateKey=null，抛出“参数不能为空”异常提示信息；<p>
     * cipherText=密文字符串，privateKey=私钥，返回解密后的明文字符串。
     */
    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        isNotNull(cipherText, privateKey);
        // 获取密码器类型
        Cipher cipher = Cipher.getInstance(CIPHER_RSA_ECB_PKCS1);
        // 初始化加密模式，将私钥注入到算法中
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decode = Base64Util.decode(cipherText);
        // 执行解密算法
        return new String(cipher.doFinal(decode));
    }

    private static void isNotNull(Object... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                throw new CommonException(CommonError.NotNull.getMessage());
            }
        }
    }

}
