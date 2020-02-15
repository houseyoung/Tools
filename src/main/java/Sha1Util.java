import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 */
public class Sha1Util {
    /**
     * 串接arr参数，生成sha1 digest
     */
    public static String gen(String... arr) {
        if (StringUtils.isAnyEmpty(arr)) {
            throw new IllegalArgumentException("非法请求参数，有部分参数为空 : " + Arrays.toString(arr));
        }

        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        for (String a : arr) {
            sb.append(a);
        }
        String sign = Hashing.sha1().hashString(sb.toString(), Charsets.UTF_8).toString().toLowerCase();
        return sign;
    }

    /**
     * 用&串接arr参数，生成sha1 digest
     */
    public static String genSignature(String jsApiTicket, String nonStr, Long timestamp, String url) {
        checkNotNullAndEmpty(jsApiTicket, "jsApiTicket");
        checkNotNullAndEmpty(nonStr, "nonStr");
        checkNotNull(timestamp, "timestamp can't be null");

        String signStr = "jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s";
        signStr = String.format(signStr, jsApiTicket, nonStr, timestamp, url);
        String signature = Hashing.sha1().hashString(signStr, Charsets.UTF_8).toString().toLowerCase();
        return signature;
    }

    private static void checkNotNullAndEmpty(String checking, String field) {
        if (checking == null || "".equals(checking)) {
            throw new IllegalArgumentException(field + " can't be null or empty");
        }
    }

    private static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        } else {
            return reference;
        }
    }
}
