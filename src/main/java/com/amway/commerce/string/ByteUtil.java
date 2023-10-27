package com.amway.commerce.string;

import java.nio.charset.StandardCharsets;

/**
 * @author: Jason.Hu
 * @date: 2023-08-15
 */
public class ByteUtil {

    /**
     * 将字节数组转换十六进制字符串。
     * 该方法需要对参数进行非空判断，若 bytes为空，则抛出参数不能为空异常。
     *
     * @param bytes 字节数组，不可为空
     * @return 十六进制字符串
     *
     * <p>
     * <b>例：</b><br>
     * bytes=null，抛出“参数不能为空”异常提示信息；<p>
     * bytes={0x0}，返回 "00"；<p>
     * bytes={(byte)0xAB, (byte)0xCD, (byte)0xEF}，返回 "abcdef"。
     */
    public static String bytesToHexString(byte[] bytes) {
        StringUtil.isNotNull(bytes);
        StringBuilder stringBuilder = new StringBuilder("");
        // 依次遍历取出
        for (byte b : bytes) {
            String temp = Integer.toHexString(b & 0xFF);
            // 若转换后的十六进制数字只有一位，则在前补 0
            if (temp.length() == 1) {
                stringBuilder.append(0);
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }

    /**
     * 将字节数组转换为字符串，默认采用 StandardCharsets.UTF_8的字符集编码格式。
     * 该方法需要对参数进行非空判断，若 bytes为空，则抛出参数不能为空异常。
     *
     * @param bytes   字节数组，不可为空
     * @return 字符串
     *
     * <p>
     * <b>例：</b><br>
     * bytes=null，抛出“参数不能为空”异常提示信息；
     * bytes={'a', 'b', 'c', 'd', 'e'}，返回 "abcde"。
     */
    public static String bytesToString(byte[] bytes) {
        StringUtil.isNotNull(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
