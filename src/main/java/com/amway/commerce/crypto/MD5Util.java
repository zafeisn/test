package com.amway.commerce.crypto;

import com.amway.commerce.exception.CommonError;
import com.amway.commerce.exception.CommonException;
import com.amway.commerce.string.ByteUtil;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: Jason.Hu
 * @date: 2023-08-14
 */
public class MD5Util {

    /**
     * MD5信息摘要算法类型
     */
    private static final String ALGORITHM = "MD5";
    /**
     * 默认字符编码格式
     */
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * MD5信息摘要算法，默认使用 UTF_8的字符编码格式。
     * 该方法需要对参数进行非空判断，若 plainText为空，则抛出参数不能为空异常。
     *
     * @param plainText 明文字符串，不可为空
     * @return MD5信息摘要，长度为 32位的 16进制小写字符串
     * @throws NoSuchAlgorithmException
     *
     * <p>
     * <b>例：</b><br>
     * plainText=null，抛出“参数不能为空”异常提示信息；<p>
     * plainText="hello world"，返回 5eb63bbbe01eeed093cb22bb8f5acdc3。
     */
    public static String encrypt(String plainText) throws NoSuchAlgorithmException {
        isNotNull(plainText);
        // 创建 MD5算法
        MessageDigest md5 = MessageDigest.getInstance(ALGORITHM);
        // 转换为 MD5码
        byte[] digest = md5.digest(plainText.getBytes(UTF_8));
        // 转换为 16进制数
        return ByteUtil.bytesToHexString(digest);
    }

    /**
     * MD5信息摘要验证方法，通过比对摘要信息来判断明文是否一致。
     * 该方法需要对参数进行非空判断，若 plainText为空，则抛出参数不能为空异常。
     *
     * @param plainText 明文字符串，不可为空
     * @param digest MD5信息摘要，长度为 32位的 16进制小写字符串
     * @return 布尔值，true表示验证成功
     * @throws NoSuchAlgorithmException
     *
     * <p>
     * <b>例：</b><br>
     * plainText=null，digest=5eb63bbbe01eeed093cb22bb8f5acdc3，抛出“参数不能为空”异常提示信息；<p>
     * plainText="hello world"，digest=5eb63bbbe01eeed093cb22bb8f5acdc3，返回 true；<p>
     * plainText="hello world"，digest=5eb63bbbe01eeed093cb22bb8f5acdc4，返回 false。
     */
    public static boolean verify(String plainText, String digest) throws NoSuchAlgorithmException {
        return encrypt(plainText).equals(digest);
    }

    private static void isNotNull(Object obj) {
        if (obj == null) {
            throw new CommonException(CommonError.NotNull.getMessage());
        }
    }

}
