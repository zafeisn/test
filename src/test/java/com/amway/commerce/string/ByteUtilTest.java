package com.amway.commerce.string;

import com.amway.commerce.exception.CommonException;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;


/**
 * @author: Jason.Hu
 * @date: 2023-09-21
 */
public class ByteUtilTest {

    @Test
    public void testBytesToHexString() {
        // normal：字节数组长度大于1
        byte[] normalBytes = {0x12, (byte)0xAB, (byte)0xCD, (byte)0xEF};
        Assert.assertEquals("12abcdef", ByteUtil.bytesToHexString(normalBytes));

        // boundary：单个16进制的字节，空格
        byte[] boundaryBytes_01 = {0x0};
        byte[] boundaryBytes_02 = {0xa};
        byte[] boundaryBytes_03 = {0xb};
        byte[] boundaryBytes_04 = {' '};
        // 单个16进制的字节
        Assert.assertEquals("00", ByteUtil.bytesToHexString(boundaryBytes_01));
        Assert.assertEquals("0a", ByteUtil.bytesToHexString(boundaryBytes_02));
        Assert.assertEquals("0b", ByteUtil.bytesToHexString(boundaryBytes_03));
        // 空格
        Assert.assertEquals("20", ByteUtil.bytesToHexString(boundaryBytes_04));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{ByteUtil.bytesToHexString(null);});

    }

    @Test
    public void testBytesToString() throws UnsupportedEncodingException {
        // normal
        byte[] normalBytes = {'a', 'b', 'c', 'd', 'e', '1', '2', '3', '.'};
        Assert.assertEquals("abcde123.", ByteUtil.bytesToString(normalBytes));
        Assert.assertEquals("abcde123.", ByteUtil.bytesToString("中文".getBytes("UTF-8")));

        // boundary：空格，单个字节
        byte[] boundaryBytes_01 = {'a'};
        byte[] boundaryBytes_02 = {'0'};
        byte[] boundaryBytes_03 = {' '};
        // 单个字节
        Assert.assertEquals("a", ByteUtil.bytesToString(boundaryBytes_01));
        Assert.assertEquals("0", ByteUtil.bytesToString(boundaryBytes_02));
        // 空格
        Assert.assertEquals(" ", ByteUtil.bytesToString(boundaryBytes_03));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{ByteUtil.bytesToString(null);});
    }
}