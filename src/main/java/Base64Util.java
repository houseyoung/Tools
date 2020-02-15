import org.apache.commons.codec.binary.Base64;

/**
 * BASE64编码解码工具包
 */
public class Base64Util {
    /**
     * BASE64字符串解码为二进制数据
     */
    public static byte[] decodeToByte(String msg) throws Exception {
        return Base64.decodeBase64(msg.getBytes("UTF-8"));
    }

    /**
     * BASE64字符串解码为字符串
     */
    public static String decodeToString(String msg) throws Exception {
        return new String(Base64.decodeBase64(msg.getBytes("UTF-8")), "UTF-8");
    }

    /**
     * 二进制数据编码为BASE64字符串
     */
    public static String encode(String msg) throws Exception {
        return new String(Base64.encodeBase64(msg.getBytes("UTF-8")), "UTF-8");
    }
}
