package com.amway.commerce.crypto;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 * @author: Jason.Hu
 * @date: 2023-08-14
 * @desc: Base64工具类
 */
public class Base64Util {

    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * Base64编码算法
     * 根据字节数组 ASCII二进制，以 6bits为一组，进行分组
     * 使用 RFC4648标准 000000：A，011010：a
     * 当二进制数据不足 3字节的整数倍时，需要进行尾部填充
     * getMimeEncoder不包含换行符\n
     *
     * @param buf 字节数组
     * @return 编码后的字符串
     */
    public static String encode(byte buf[]) {
        return new String(Base64.getMimeEncoder().encode(buf));
    }

    /**
     * Base64编码算法
     *
     * @param str     String字符串
     * @return 编码后的字符串
     */
    public static String encode(String str) throws Exception {
        return encode(str.getBytes(DEFAULT_CHARSET));
    }

    /**
     * Base64解码算法
     *
     * @param encoded 编码后的字符串
     * @return 解码字节数组
     */
    public static byte[] decode(String encoded) throws Exception {
        return decode(encoded.getBytes(DEFAULT_CHARSET));
    }

    /**
     * Base64解码算法
     *
     * @param encoded 编码后的字节数组
     * @return 解码字节数组
     */
    public static byte[] decode(byte[] encoded) {
        return Base64.getMimeDecoder().decode(encoded);
    }

}
