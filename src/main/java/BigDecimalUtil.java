/*************************************************************
 *  BigDecimal操作的工具类，包含精度计算
 *
 *  DecimalUtils.java
 *
 *   2015年8月17日/下午3:56:21
 *   mailto:"cuixiang"<cuixiang@corp.netease.com>
 ************************************************************/

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * BigDecimal工具类
 */
public class BigDecimalUtil {
    public static final BigDecimal BIGDECIMAL_100 = new BigDecimal("100");
    public static final BigDecimal BIGDECIMAL_200 = new BigDecimal("200");
    public static final BigDecimal BIGDECIMAL_1000 = new BigDecimal("1000");
    public static final BigDecimal BIGDECIMAL_10000 = new BigDecimal("10000");

    private BigDecimalUtil() {
    }

    /**
     * 比较两个bigDecimal四舍五入保留两位小数后的结果是否相等
     * @param comp1
     * @param comp2
     * @return
     */
    public static boolean equalByHalf(BigDecimal comp1, BigDecimal comp2) {
        if (null == comp1 || null == comp2) {
            return false;
        }
        comp1 = BigDecimalUtil.bigDecimal2Half(comp1);
        comp2 = BigDecimalUtil.bigDecimal2Half(comp2);
        return (comp1.compareTo(comp2) == 0);
    }

    /**
     * 比较两个bigDecimal四舍五入保留两位小数后的结果，comp1是否大于等于comp2
     * @param comp1
     * @param comp2
     * @return
     */
    public static boolean greatAndEqualByHalf(BigDecimal comp1, BigDecimal comp2) {
        if (null == comp1 || null == comp2) {
            return false;
        }
        comp1 = BigDecimalUtil.bigDecimal2Half(comp1);
        comp2 = BigDecimalUtil.bigDecimal2Half(comp2);
        return (comp1.compareTo(comp2) >= 0);
    }

    /**
     * 比较两个bigDecimal四舍五入保留两位小数后的结果，comp1是否大于comp2
     * @param comp1
     * @param comp2
     * @return
     */
    public static boolean greatByHalf(BigDecimal comp1, BigDecimal comp2) {
        if (null == comp1 || null == comp2) {
            return false;
        }
        comp1 = BigDecimalUtil.bigDecimal2Half(comp1);
        comp2 = BigDecimalUtil.bigDecimal2Half(comp2);
        return (comp1.compareTo(comp2) > 0);
    }

    /**
     * bigdecimal是否大于0
     * @param bigDecimal
     * @return
     */
    public static boolean greateZero(BigDecimal bigDecimal) {
        return null != bigDecimal && bigDecimal.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * bigdecimal是否大于等于0
     * @param bigDecimal
     * @return
     */
    public static boolean greateAndEqualZero(BigDecimal bigDecimal) {
        return null != bigDecimal && bigDecimal.compareTo(BigDecimal.ZERO) >= 0;
    }

    /**
     * bigdecimal是否小于0
     * @param bigDecimal
     * @return
     */
    public static boolean lessZero(BigDecimal bigDecimal) {
        return null != bigDecimal && !greateAndEqualZero(bigDecimal);
    }

    /**
     * bigdecimal是否小于等于0
     * @param bigDecimal
     * @return
     */
    public static boolean lessAndEqualZero(BigDecimal bigDecimal) {
        return null != bigDecimal && !greateZero(bigDecimal);
    }

    /**
     * bigdecimal是否不等于0
     * @param bigDecimal
     * @return
     */
    public static boolean notEqualZero(BigDecimal bigDecimal) {
        return null != bigDecimal && bigDecimal.compareTo(BigDecimal.ZERO) != 0;
    }

    /**
     * 
     *  把bigdecimal转为保留2位小数并且四舍五入的字符串
     *
     *   @param bigDecimal
     *   @return
     *
     *   2015年8月17日/下午4:03:04
     *   mailto:"cuixiang"<cuixiang@corp.netease.com>
     */
    public static String bigDecimal2String(BigDecimal bigDecimal) {
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        DecimalFormat df = new DecimalFormat("###,##0.00");
        return df.format(bigDecimal);
    }

    /**
     * 四舍五入保留两位小数
     * @param bigDecimal
     * @return
     */
    public static BigDecimal bigDecimal2Half(BigDecimal bigDecimal) {
        if (null == bigDecimal) {
            return BigDecimal.ZERO;
        }
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 小数点左移两位，保留三位小数
     * @param bigDecimal
     * @return
     */
    public static BigDecimal divide2Down(BigDecimal bigDecimal) {
        return bigDecimal.divide(BIGDECIMAL_100, 3, BigDecimal.ROUND_DOWN);
    }

    /**
     * 小数点左移两位，保留三位小数
     * @param bigDecimalStr
     * @return
     */
    public static BigDecimal divide2Down(String bigDecimalStr) {
        BigDecimal bigDecimal = new BigDecimal(bigDecimalStr);
        return bigDecimal.divide(BIGDECIMAL_100, 3, BigDecimal.ROUND_DOWN);
    }

    /**
     * 保留两位小数，舍弃末尾
     * @param bigDecimal
     * @return
     */
    public static BigDecimal bigDecimal2Down(BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
    }

    public static String pointToPercent(BigDecimal bigDecimal) {
        if (null == bigDecimal) {
            return null;
        }
        BigDecimal point = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
        return String.valueOf(point.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN));
    }

    public static String pointToPercent(String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        BigDecimal point = new BigDecimal(string).setScale(2, BigDecimal.ROUND_DOWN);
        return String.valueOf(point.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN));
    }

    public static String pointToPercentWithTwoScale(BigDecimal bigDecimal) {
        if (null == bigDecimal) {
            return null;
        }
        return String.valueOf(bigDecimal.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN));
    }

    public static String percentToPoint(Object thisObj) {
        if (null == thisObj) {
            return null;
        }
        Double point = 0D;
        if (thisObj instanceof Double) {
            point = (Double) thisObj;
        }
        else if (thisObj instanceof String) {
            point = Double.parseDouble((String) thisObj);
        }
        return String.valueOf(point / 100);
    }

    public static BigDecimal convertStringToBigDecimal(String string) {
        if (StringUtils.isEmpty(string)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(string);
    }
}
