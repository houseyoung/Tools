import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES工具类
 */
public class AesUtil {

    /**
     *
     * @param keyRule
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String createKey(String keyRule) throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance("AES"); // 获取密匙生成器
        kg.init(128, new SecureRandom(keyRule.getBytes(StandardCharsets.UTF_8))); // 初始化
        // DES算法必须是56位
        // DESede算法可以是112位或168位
        // AES算法可以是128、192、256位 
        SecretKey key = kg.generateKey(); // 生成密匙，可用多种方法来保存密匙
        byte[] keyByte = key.getEncoded();
        return new String(keyByte, StandardCharsets.UTF_8);

    }

    /**
     * 加密
     * @param src 数据源
     * @param kv 密钥，长度必须是8的倍数
     * @return  返回加密后的数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] src, byte[] kv) throws Exception {
        SecretKey key = new SecretKeySpec(kv, "AES");

        //加密：
        Cipher cp = Cipher.getInstance("AES"); // 创建密码器
        cp.init(Cipher.ENCRYPT_MODE, key); // 初始化
        byte[] ctext = cp.doFinal(src); // 加密
        return ctext;
    }

    /**
     * 解密
     * @param src 数据源
     * @param kv 密钥，长度必须是8的倍数
     * @return   返回解密后的原始数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, byte[] kv) throws Exception {
        SecretKey key = new SecretKeySpec(kv, "AES");
        Cipher cp = Cipher.getInstance("AES"); // 创建密码器 
        cp.init(Cipher.DECRYPT_MODE, key); // 初始化
        byte[] ptext = cp.doFinal(src); // 解密 
        return ptext;
    }

    /**
     * 数组转十六进制字符串
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    /**
     * 十六进制字符串转数组
     * @param s
     * @return
     */
    public static byte[] hex2byte(String s) {
        byte[] b = s.getBytes(StandardCharsets.UTF_8);
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2, StandardCharsets.UTF_8);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
}
