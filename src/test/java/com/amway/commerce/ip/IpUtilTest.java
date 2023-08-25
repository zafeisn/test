package com.amway.commerce.ip;

import org.junit.Test;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author: Jason.Hu
 * @date: 2023-08-11
 * @desc:
 */
public class IpUtilTest {

    /**
     * 获取本机地址
     */
    @Test
    public void getLocalHostExactAddress() throws SocketException, UnknownHostException {
        // /10.140.159.62
        System.out.println(IpUtil.getLocalHostExactAddress());
        // 10.140.159.62
        System.out.println(IpUtil.getHostAddress());
    }
}