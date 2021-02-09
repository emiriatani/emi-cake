package com.myf.myproject.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName com.myf.myproject.utils NetUtils
 * @Description  获取客户端IP地址工具类
 * @Author Afengis
 * @Date 2021/2/6 14:07
 * @Version V1.0
 **/
public class NetUtils {



    private static final String UNKNOWN = "unknown";
    private static final String[] HEAD_INFO = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR", "PROXY_FORWARDED_FOR", "X-Real-IP"};
    /**
     * getClientIpAddress:(获取用户ip，可穿透代理).
     * @param request HttpServletRequest
     * @return String
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : HEAD_INFO) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                if (ip.contains(",")) {
                    String[] ips = ip.split(",");
                    for (String s : ips) {
                        if (!(UNKNOWN.equalsIgnoreCase(s))) {
                            ip = s;
                            break;
                        }
                    }
                }
                return ip;
            }
        }
        return request.getRemoteAddr();
    }


    /**
     * IPv4地址转换为int类型数字
     * @param ipv4Addr
     * @return
     */
    public static int ipToInt(String ipv4Addr) {
        // 判断是否是ip格式的
        if (!isIPv4Address(ipv4Addr))
            throw new RuntimeException("Invalid ip address");

        // 匹配数字
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(ipv4Addr);
        int result = 0;
        int counter = 0;
        while (matcher.find()) {
            int value = Integer.parseInt(matcher.group());
            result = (value << 8 * (3 - counter++)) | result;
        }
        return result;
    }

    /**
     * 判断是否为ipv4地址
     * @param ipv4Addr
     * @return
     */
    private static boolean isIPv4Address(String ipv4Addr) {
        String lower = "(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])"; // 0-255的数字
        String regex = lower + "(\\." + lower + "){3}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ipv4Addr);
        return matcher.matches();
    }

    /**
     * 将int数字转换成ipv4地址
     * @param ip
     * @return
     */
    public static String intToIp(int ip) {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        boolean needPoint = false; // 是否需要加入'.'
        for (int i = 0; i < 4; i++) {
            if (needPoint) {
                sb.append('.');
            }
            needPoint = true;
            int offset = 8 * (3 - i);
            num = (ip >> offset) & 0xff;
            sb.append(num);
        }
        return sb.toString();
    }
}
