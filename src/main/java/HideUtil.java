import org.apache.commons.lang3.StringUtils;

public class HideUtil {

    /**
     * 隐藏邮箱账号的字符。前面保留headCount个明文字符
     *
     * @param value
     * @param headCount 保留开头的几个字符
     * @return
     */
    public static String hideWithStarInMail(String value, int headCount) {
        int index = value.indexOf('@');
        if (index > 0) {
            String half1 = value.substring(0, index);
            if (half1.length() < headCount) {
                headCount = half1.length() - 1;
            }
            half1 = hideWithStar(half1, headCount, 0);
            return half1 + value.substring(index);
        }
        else {
            return value;
        }
    }

    /**
     * 隐藏邮箱账号的字符。前面保留tailCount个明文字符
     *
     * @param value
     * @param tailCount 保留结尾的几个字符
     * @return
     */
    public static String hideTailWithStarInMail(String value, int tailCount) {
        int index = value.indexOf('@');
        if (index > 0) {
            String half1 = value.substring(0, index);
            if (half1.length() < tailCount) {
                tailCount = half1.length() - 1;
            }
            half1 = hideWithStar(half1, 0, tailCount);
            return half1 + value.substring(index);
        }
        else {
            return value;
        }
    }

    /**
     * 隐藏邮箱账号的字符。隐藏前面headCount个字符
     *
     * @param value
     * @param headCount 隐藏开头的几个字符
     * @return
     */
    public static String hideHeadInMail(String value, int headCount) {
        int index = value.indexOf('@');
        if (index > 0) {
            String half1 = value.substring(0, index);
            if (half1.length() <= headCount) {
                headCount = half1.length() - 1;
            }
            half1 = hideWithStar(half1, 0, half1.length() - headCount);
            return half1 + value.substring(index);
        }
        else {
            return value;
        }
    }

    /**
     * 隐藏邮箱账号的字符。隐藏前面headCount个字符
     *
     * @param value
     * @param tailCount 隐藏结尾的几个字符
     * @return
     */
    public static String hideTailInMail(String value, int tailCount) {
        int index = value.indexOf('@');
        if (index > 0) {
            String half1 = value.substring(0, index);
            if (half1.length() <= tailCount) {
                tailCount = half1.length() - 1;
            }
            half1 = hideWithStar(half1, half1.length() - tailCount, 0);
            return half1 + value.substring(index);
        }
        else {
            return value;
        }
    }

    /**
     * 隐藏字符串中的文字为*，几个字符用几个*代替，开头至少保留一位
     *
     * @param value
     * @param headCount 开头展示几个字符, 至少会在开头保留1位
     * @param tailCount 结尾展示几个字符
     * @return
     */
    public static String hideWithStarLeftOne(String value, int headCount, int tailCount) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        else {
            String[] arr = value.split("");
            StringBuffer sb = new StringBuffer(arr[0]);
            int length = value.length();
            if (length <= 2) {
                sb.append("*");
            }
            else {
                int i = 1;
                for (; i < headCount; i++) {
                    sb.append(arr[i]);
                }
                for (; i < length - tailCount; i++) {
                    sb.append("*");
                }
                for (; i < length; i++) {
                    sb.append(arr[i]);
                }
            }
            return sb.toString();
        }
    }

    /**
     * 隐藏字符串中的文字为*，几个字符用几个*代替
     *
     * @param value
     * @param headCount 开头展示几个字符
     * @param tailCount 结尾展示几个字符
     * @return
     */
    public static String hideWithStar(String value, int headCount, int tailCount) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        else {
            String[] arr = value.split("");
            StringBuffer sb = new StringBuffer();
            int length = value.length();

            int i = 0;
            for (; i < headCount; i++) {
                sb.append(arr[i]);
            }
            for (; i < length - tailCount; i++) {
                sb.append("*");
            }
            for (; i < length; i++) {
                sb.append(arr[i]);
            }
            return sb.toString();
        }
    }

    /**
     * 隐藏字符串中的文字为*，只用一个*
     *
     * @param value
     * @param headCount 开头展示几个字符
     * @param tailCount 结尾展示几个字符
     * @return
     */
    public static final String hideWithSingleStar(String value, int headCount, int tailCount) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        else {
            String[] arr = value.split("");
            StringBuffer sb = new StringBuffer(arr[0]);
            int length = value.length();
            if (length <= 2) {
                sb.append("*");
            }
            else {
                int i = 1;
                for (; i < headCount; i++) {
                    sb.append(arr[i]);
                }
                sb.append("*");
                for (i = length - tailCount; i < length; i++) {
                    sb.append(arr[i]);
                }
            }
            return sb.toString();
        }
    }
}
