package com.amway.commerce.string;

import java.io.UnsupportedEncodingException;

/**
 * @author: Jason.Hu
 * @date: 2023-08-14
 * @desc: Byte工具类
 */
public class ByteUtil {

    /**
     * 将字节数组转换十六进制为字符串
     *
     * @param bytes 字节数组
     * @return 转换后的字符串
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        // 以此遍历取出
        for (byte b : bytes) {
            String temp = Integer.toHexString(b & 0xFF);
            // 若转换后的十六进制数字只有一位，则在前补0
            if (temp.length() == 1) {
                stringBuilder.append(0);
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }

    /**
     * 字节数组转换为字符串
     *
     * @param bytes   字节数组
     * @param charset 编码类型
     * @return 可读字符串
     * @throws UnsupportedEncodingException
     */
    public static String bytesToString(byte[] bytes, String charset) throws UnsupportedEncodingException {
        return new String(bytes, charset);
    }
}
