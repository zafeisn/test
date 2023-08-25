package com.amway.commerce.crypto;

import com.amway.commerce.string.ByteUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: Jason.Hu
 * @date: 2023-08-14
 * @desc:
 */
public class Base64UtilTest {

    byte[] a = {'a', 'b', 'c'};

    String str = "中文==@#$$%^&*()_+!2\n\r";

    String charset = "UTF-8";

    /**
     * Base64编码算法
     */
    @Test
    public void encode() throws Exception {
        byte[] bytes = str.getBytes("UTF-8");
        byte[] data = new byte[a.length + bytes.length];
        System.arraycopy(a, 0, data, 0, a.length);
        System.arraycopy(bytes, 0, data, a.length, bytes.length);
        // YWJj
        System.out.println(Base64Util.encode(a));
        // YWJj5Lit5paHPT1AIyQkJV4mKigpXyshMgoN
        System.out.println(Base64Util.encode(data));
        // 5Lit5paHPT1AIyQkJV4mKigpXyshMgoN
        System.out.println(Base64Util.encode(str));
    }

    /**
     * Base64解码算法
     */
    @Test
    public void decode() throws Exception {
        // abc
        System.out.println(ByteUtil.bytesToString(Base64Util.decode(Base64Util.encode(a)), "UTF-8"));
        byte[] bytes = str.getBytes("UTF-8");
        byte[] data = new byte[a.length + bytes.length];
        System.arraycopy(a, 0, data, 0, a.length);
        System.arraycopy(bytes, 0, data, a.length, bytes.length);
        // abc中文==@#$$%^&*()_+!2
        System.out.println(ByteUtil.bytesToString(Base64Util.decode(Base64Util.encode(data)), "UTF-8"));
        // abc
        System.out.println(ByteUtil.bytesToString(Base64Util.decode(Base64.getMimeEncoder().encode(a)), charset));
    }

}