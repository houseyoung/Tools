import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 *
 *Created by wb.zhaozhiqian on 2019/7/18 15:34
 */
public class CookieUtil {
    public CookieUtil() {
    }

    public static String getCookie(HttpServletRequest request, String cookieFlag) {
        try {
            String cookieValue = null;
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
//                LOG.info("[CookieUtil] Cookie is null.");
                return null;
            }

            for (Cookie cookie : cookies) {
                if (null == cookie || null == cookie.getName()) {
//                    log.info("[CookieUtil] getCookie cookie null: {}", JSON.toJSONString(cookie));
                    continue;
                }

                if ((cookie.getName()).equalsIgnoreCase(cookieFlag)) {
                    cookieValue = URLDecoder.decode(cookie.getValue(), "utf-8");
                    break;
                }
            }

            return cookieValue;
        }
        catch (Exception e) {
            e.printStackTrace();
//            log.warn("[CookieUtil] getCookie exception", e);
            return null;
        }
    }

    public static String getAndSetCookie(HttpServletRequest request, HttpServletResponse response, String cookieFlag) {
        String value = getCookie(request, cookieFlag);
        if (StringUtils.isEmpty(value)) {
            value = request.getSession().getId();
            Cookie capCookie = new Cookie(cookieFlag, value);
            capCookie.setMaxAge(120);
            capCookie.setPath("/");
            response.addCookie(capCookie);
        }

        return value;
    }

    public static void setCookie(HttpServletResponse response, String flag, String value) {
//        log.info("[刷新cookie] set cookie to response");
        Cookie userCookie = new Cookie(flag, value);
        userCookie.setMaxAge(7 * 24 * 60 * 60);
        userCookie.setPath("/");
        response.addCookie(userCookie);
    }

    private static Cookie newCookie(String flag, String value) {
        Cookie cookie = new Cookie(flag, value);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");
        return cookie;
    }
}
