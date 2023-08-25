package com.amway.commerce.ip;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @author: Jason.Hu
 * @date: 2023-08-11
 * @desc: ip工具类
 */
public class IpUtil {

    /**
     * 获取本机地址
     *
     * @return InetAddress类型的本机地址，如果没有获取到，则返回 InetAddress.getLocalHost()获取主机名查询本机地址，读取/etc/hosts文件（Linux）
     */
    public static InetAddress getLocalHostExactAddress() throws SocketException, UnknownHostException {
        InetAddress candidateAddress = null;
        // 获取所有的网络接口
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = networkInterfaces.nextElement();
            // 该网卡接口下的 ip会有多个，需要一个个的遍历，找到所需要的
            for (Enumeration<InetAddress> inetAddress = netInterface.getInetAddresses(); inetAddress.hasMoreElements(); ) {
                InetAddress inetAddr = inetAddress.nextElement();
                /*
                 * 排除 loopback回环地址，本地链路地址以及通配符地址
                 * loopback 127.xxx.xxx.xxx
                 * link local 169.254.xxx.xxx
                 */
                if (!inetAddr.isLoopbackAddress() || !inetAddr.isLinkLocalAddress() || !inetAddr.isAnyLocalAddress()) {
                    if (inetAddr.isSiteLocalAddress()) {
                        /*
                         * site-local地址
                         * 10/8 前缀
                         * 172.16/12 前缀
                         * 192.168/16 前缀
                         */
                        return inetAddr;
                    } else if (candidateAddress == null) {
                        // site-local类型的地址未被发现，先记录候选地址
                        candidateAddress = inetAddr;
                    }
                }
            }
        }
        // 如果没有获取到，则返回 InetAddress.getLocalHost()获取主机名查询本机地址，读取/etc/hosts文件（Linux）
        return candidateAddress == null ? InetAddress.getLocalHost() : candidateAddress;
    }

    /**
     * 获取本机地址
     *
     * @return 字符串格式的本机地址
     */
    public static String getHostAddress() throws SocketException, UnknownHostException {
        return getLocalHostExactAddress().getHostAddress();
    }

}
