import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * 生成数据库主键
 */
public class IdGeneratorUtil {
    public static final String DATE_FORMAT_YYMMDDHHMMssSSS = "yyMMddHHmmssSSS";

    /**
     * 生成sequence，格式为：yyMMddHHmmssSSS+type+6位随机数
     * @param str
     * @return
     */
    public static String getEntityIdByStr(String str) {
        if (StringUtils.isNotEmpty(str)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(DateUtil.formatTime(DateUtil.getCurrentTimestamp(), DATE_FORMAT_YYMMDDHHMMssSSS));
            stringBuilder.append(str);
            stringBuilder.append(generateNumberString_6());
            return stringBuilder.toString();
        }
        return null;
    }

    /**
     * 生成sequence，格式为：yyMMddHHmmssSSS+6位随机数
     * @return
     */
    public static String getEntityIdWithoutType() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DateUtil.formatTime(DateUtil.getCurrentTimestamp(), DATE_FORMAT_YYMMDDHHMMssSSS));
        stringBuilder.append(generateNumberString_6());
        return stringBuilder.toString();
    }

    /**
     * 生成sequence，格式为：yyMMddHHmmssSSS+ str+ 8位数
     * @return
     */
    public static String getEntityIdWithoutIndex(String str, int index) {
        StringBuilder stringBuilder = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("00000000");
        stringBuilder.append(DateUtil.formatTime(DateUtil.getCurrentTimestamp(), DATE_FORMAT_YYMMDDHHMMssSSS));
        stringBuilder.append(str);
        stringBuilder.append(decimalFormat.format(index));
        return stringBuilder.toString();
    }

    /**
     * 生成sequence，格式为：yyMMddHHmmssSSS+ str+ 8位数
     * @return
     */
    public static String getEntityIdWithoutIndex(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DateUtil.formatTime(DateUtil.getCurrentTimestamp(), DATE_FORMAT_YYMMDDHHMMssSSS));
        stringBuilder.append(str);
        stringBuilder.append(generateNumberString_6());
        return stringBuilder.toString();
    }

    private static String generateNumberString_6() {
        Random random = new Random();
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        return decimalFormat.format((long)random.nextInt(1000000));
    }
}
