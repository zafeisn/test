package com.amway.commerce.ip;

import org.junit.Assert;
import org.junit.Test;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author: Jason.Hu
 * @date: 2023-09-20
 */
public class IpUtilTest {

    @Test
    public void testGetLocalHostExactAddress() throws SocketException, UnknownHostException {
        // 判断是否能拿到本地ip地址
        Assert.assertNotNull(IpUtil.getLocalHostExactAddress());
        // 判断是否不是回环地址、链路地址、通配符地址
        Assert.assertFalse(IpUtil.getLocalHostExactAddress().isLoopbackAddress());
        Assert.assertFalse(IpUtil.getLocalHostExactAddress().isLinkLocalAddress());
        Assert.assertFalse(IpUtil.getLocalHostExactAddress().isAnyLocalAddress());
    }

    @Test
    public void testGetHostAddress() throws SocketException, UnknownHostException {
        // 判断是否能拿到本地ip地址
        Assert.assertNotNull(IpUtil.getHostAddress());
        // 判断是否不是回环地址、链路地址、通配符地址
        Assert.assertNotEquals("127",IpUtil.getHostAddress().split("\\.")[0]);
        Assert.assertNotEquals("0",IpUtil.getHostAddress().split("\\.")[0]);
        Assert.assertNotEquals("169",IpUtil.getHostAddress().split("\\.")[0]);
    }
}