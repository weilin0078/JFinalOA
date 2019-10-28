package com.pointlion.mvc.common.utils;

import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 丶Lion
 * @mail 439645473@qq.com
 * @qq 439635374
 * @date 2019/3/30 17:52
 */
public class IPUtil {
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if(StrKit.notBlank(ip)) {
            ip = ip.split(",")[0];
        }
        return ip;

    }
}
