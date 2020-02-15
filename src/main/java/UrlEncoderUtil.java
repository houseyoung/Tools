import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlEncoderUtil {
    private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f' };

    /**
     * 将字符数组转换为16进制字符串
     * 
     * @param bcd
     * @return
     */
    public static final synchronized String bytesToHexStr(byte[] bcd) {
        StringBuffer s = new StringBuffer(bcd.length * 2);

        for (int i = 0; i < bcd.length; i++) {
            s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
            s.append(bcdLookup[bcd[i] & 0x0f]);
        }

        return s.toString();
    }

    /**
     * 将16进制字符串转换为字符数组
     * 
     * @param source
     * @return
     */
    public static final synchronized byte[] hexStrToBytes(String source) {
        byte[] bytes;

        bytes = new byte[source.length() / 2];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(source.substring(2 * i, 2 * i + 2), 16);
        }

        return bytes;
    }

    @SuppressWarnings("deprecation")
    public static String encodeUrl(String url, String charsetName) {
        String urlAfterEncode = "";
        try {
            urlAfterEncode = URLEncoder.encode(url, charsetName);
        }
        catch (UnsupportedEncodingException e) {
            urlAfterEncode = URLEncoder.encode(url);
        }
        return urlAfterEncode;
    }

    @SuppressWarnings("deprecation")
    public static String decodeUrl(String url, String charsetName) {
        String urlAfterDecode = "";
        try {
            urlAfterDecode = URLDecoder.decode(url, charsetName);
        }
        catch (UnsupportedEncodingException e) {
            urlAfterDecode = URLDecoder.decode(url);
        }
        return urlAfterDecode;
    }
}
