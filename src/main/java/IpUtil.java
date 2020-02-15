/**
 * Created by yangchao2014 on 2017/1/4.
 */

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IpUtil {
    /**
     * 增加HTTP_CLIENT_IP、HTTP_X_FORWARDED_FOR两种可能
     * @author houseyoung
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String unknown = "unknown";
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 判断当前操作是否Windows.
     *
     * @return true---是Windows操作系统
     */
    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }

    /**
     * 获取本机IP地址，并自动区分Windows还是Linux操作系统
     *
     * @return String
     */
    public static String getLocalIp() {
        String sIP = "";
        InetAddress ip = null;
        List<InetAddress> innerIpList = new ArrayList<InetAddress>();
        try {
            // 如果是Windows操作系统
            if (isWindowsOS()) {// isWindowsOS()) {
                ip = InetAddress.getLocalHost();
                innerIpList.add(ip);
            }
            // 如果是Linux操作系统
            else {
                Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
                        .getNetworkInterfaces();
                while (netInterfaces.hasMoreElements()) {
                    NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                    // ----------特定情况，可以考虑用ni.getName判断
                    // 遍历所有ip
                    Enumeration<InetAddress> ips = ni.getInetAddresses();
                    while (ips.hasMoreElements()) {
                        ip = (InetAddress) ips.nextElement();
                        // log.info(ip.getHostAddress());
                        if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
                                && ip.getHostAddress().indexOf(":") == -1
                                && (ip.getHostAddress().startsWith("192.168") || ip.getHostAddress().startsWith("172.")
                                        || ip.getHostAddress().startsWith("10."))) {
                            innerIpList.add(ip);
                            // log.info("ip="+ip);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
//            log.error("get local ip error:", e);
        }
        if (innerIpList.size() > 0) {
            sIP = innerIpList.get(0).getHostAddress();
        }
        return sIP;
    }

    /**
     * 获取本机IP地址，并自动区分Windows还是Linux操作系统的int形式
     *
     * @return String
     */
    public static Long getLocalIpLongValue() {
        String sIP = getLocalIp();
        return ipToNumber(sIP);
    }

    private static Long ipToNumber(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return 1L;
        }
        Long ips = 0L;
        String[] numbers = ip.split("\\.");
        //等价上面
        for (int i = 0; i < 4; ++i) {
            ips = ips << 8 | Integer.parseInt(numbers[i]);
        }
        return ips;
    }

}
