package com.amway.commerce.crypto;

import com.amway.commerce.exception.CommonError;
import com.amway.commerce.exception.CommonException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author: Jason.Hu
 * @date: 2023-08-14
 */
public class Base64Util {

    /**
     * 默认字符编码格式
     */
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * 对字节数组进行 Base64编码。
     * 该方法需要对参数进行非空判断，若 buf为空，则抛出参数不能为空异常。
     *
     * @param buf 字节数组，不可为空
     * @return Base64编码字符串
     *
     * <p>
     * <b>例：</b><br>
     * buf=null，抛出“参数不能为空”异常提示信息；<p>
     * buf={'a','b','c','\n'}，返回 YWJjCg==。
     */
    public static String encode(byte[] buf) {
        isNotNull(buf);
        return Base64.getEncoder().encodeToString(buf);
    }

    /**
     * 对编码后的字符串进行 Base64解码。
     * 该方法需要对参数进行非空判断，若 encoded为空，则抛出参数不能为空异常。
     *
     * @param encoded Base64编码字符串，不可为空
     * @return Base64解码后的字节数组
     *
     * <p>
     * <b>例：</b><br>
     * encoded=null，抛出“参数不能为空”异常提示信息；<p>
     * encoded="YWJjCg=="，返回 {'a','b','c','\n'}。
     */
    public static byte[] decode(String encoded) {
        isNotNull(encoded);
        return Base64.getDecoder().decode(encoded);
    }

    /**
     * 对字符串进行 Base64编码，默认使用 UTF_8的字符编码格式。
     * 该方法需要对参数进行非空判断，若 str为空，则抛出参数不能为空异常。
     *
     * @param str 待编码的字符串，不可为空
     * @return Base64编码字符串
     *
     * <p>
     * <b>例：</b><br>
     * str=null，抛出“参数不能为空”异常提示信息；<p>
     * str="hello world"，返回 aGVsbG8gd29ybGQ=。
     */
    public static String encode(String str) {
        isNotNull(str);
        return encode(str.getBytes(UTF_8));
    }

    /**
     * 对编码后的字节数组进行 Base64解码。
     * 该方法需要对参数进行非空判断，若 encoded为空，则抛出参数不能为空异常。
     *
     * @param encoded Base64编码字节数组，不可为空
     * @return Base64解码后的字节数组
     *
     * <p>
     * <b>例：</b><br>
     * encoded=null，抛出“参数不能为空”异常提示信息；<p>
     * encoded={'a','G','V','s','b','G','8','g','d','2','9','y','b','G','Q','='}，返回 {'h','e','l','l','o',' ','w','o','r','l','d'}。
     */
    public static byte[] decode(byte[] encoded) {
        isNotNull(encoded);
        return decode(new String(encoded));
    }

    private static void isNotNull(Object obj) {
        if (obj == null) {
            throw new CommonException(CommonError.NotNull.getMessage());
        }
    }

}
