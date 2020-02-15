import javax.servlet.http.HttpServletRequest;

/**
 *
 *Created by wb.zhaozhiqian on 2019/11/6 14:36
 */
public class RequestUtil {

    public static String getDomain(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
//        log.info("[RequestUtil][getDomain] url={}", url);
        String[] urls = url.split("//");
        if (urls.length < 2) {
//            log.warn("[RequestUtil][getDomain] 域名无法解析 url={}", url);
            return null;
        }

        String[] domains = urls[1].split("/");
//        log.info("[RequestUtil][getDomain] domain={}", domains[0]);

        return domains[0];
    }

    public static String getDomain(String url) {
//        log.info("[RequestUtil][getDomain] url={}", url);
        String[] domains;
        if (url.contains("//")) {
            String[] urls = url.split("//");
            domains = urls[1].split("/");
        }
        else {
            domains = url.split("/");
        }

//        log.info("[RequestUtil][getDomain] domain={}", domains[0]);

        return domains[0];
    }

    public static String getDomainWithScheme(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
//        log.info("[RequestUtil][getDomainWithScheme] url={}", url);
        String[] urls = url.split("//");
        if (urls.length < 2) {
//            log.warn("[RequestUtil][getDomainWithScheme] 域名无法解析 url={}", url);
            return null;
        }

        String[] domains = urls[1].split("/");
        String result = urls[0] + "//" + domains[0];
//        log.info("[RequestUtil][getDomainWithScheme] domain={}", result);

        return result;
    }
}
