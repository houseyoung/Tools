import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    /**
     * @param str
     * @return
     */
    public static String convertToMd5(String str) {
        byte passToConvertByte[] = str.getBytes(StandardCharsets.UTF_8);
        String cryptograph = null;
        try {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            byte gottenPassByte[] = messagedigest.digest(passToConvertByte);
            cryptograph = "";
            for (int i = 0; i < gottenPassByte.length; i++) {
                String temp = Integer.toHexString(gottenPassByte[i] & 0x000000ff);
                if (temp.length() < 2)
                    temp = "0" + temp;
                cryptograph += temp;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            cryptograph = null;
        }
        return cryptograph;
    }

    public static String getMD5(InputStream is) throws NoSuchAlgorithmException, IOException {
        StringBuffer md5 = new StringBuffer();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] dataBytes = new byte[1024];

        int nread = 0;
        while ((nread = is.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }
        byte[] mdbytes = md.digest();

        // convert the byte to hex format  
        for (int i = 0; i < mdbytes.length; i++) {
            md5.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return md5.toString();
    }
}
