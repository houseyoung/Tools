import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil extends org.apache.commons.lang3.time.DateUtils {
    /**
     * 默认的日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_YYMMDD = "yyMMdd";

    public static final String FMT_MMDD_C = "MM月dd日";
    public static final String FMT_MMDD_ACTIVE_C = "MM月dd号";
    public static final String FMT_MMDD_Y = "yyyy年MM月dd日";
    public static final String FMT_MMDDHH_C = "MM月dd日HH点";
    public static final String FMT_DDHH_C = "dd日HH点";
    public static final String FMT_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String FMT_YYYYMMDD_T_HHMMSS = "yyyy-MM-dd'T'HH:mm:ss";
    public static final int FMT_DATE_YYYY = 0;
    public static final int FMT_DATE_YYYYMMDD = 1;
    public static final int FMT_DATE_YYYYMMDD_HHMMSS = 2;
    public static final int FMT_DATE_HHMMSS = 3;
    public static final int FMT_DATE_HHMM = 4;
    public static final int FMT_DATE_SPECIAL = 5;
    /**
     * 日期转换格式：MM-dd
     */
    public static final int FMT_DATE_MMDD = 6;
    public static final int FMT_DATE_YYYYMMDDHHMM = 7;
    public static final int FMT_DATE_MMDD_HHMM = 8;
    public static final int FMT_DATE_MMMDDD = 9;
    public static final int FMT_DATE_YYYYMMDDHHMM_NEW = 10;
    public static final int FMT_DATE_YYYY_Y_MM_M_DD_D = 11;
    public static final int FMT_DATE_YYYYMMDDHHMMSS = 12;
    public static final int FMT_DATE_YYMMDD = 13;
    public static final int FMT_DATE_YYMMDDHH = 14;
    public static final int FMT_DATE_MMDD_HHMM_CH = 15;
    /**
     * @add by wangmeng 添加日期转换格式MMdd
     */
    public static final int FMT_DATE_MMdd = 16;
    /**@add by dujp 添加YY/MM/DD*/
    public static final int FMT_DATE_YY_MM_DD = 17;
    /**@add by haoxq 添加YYYY/MM/DD*/
    public static final int FMT_DATE_YYYY_MM_DD = 18;
    public static final int FMT_DATE_EEEMMMddHHmmsszzzyyyy = 19;

    /** 静态常量值 用于获取 某一个日期的 年 月 日 时 分 秒 标识 **/
    public static final int GET_TIME_OF_YEAR = 100;// 获得 日期的年份
    public static final int GET_TIME_OF_MONTH = 200;// 获得 日期的月份
    public static final int GET_TIME_OF_DAY = 300;// 获取 日期的天
    public static final int GET_TIME_IF_HOUR = 400;// 获取日期的小时
    public static final int GET_TIME_OF_MINUTE = 500;
    public static final int GET_TIME_OF_SECOND = 600;
    
    private DateUtil() {
    }

    public static Date parseUtilDate(String strDate, int nFmtDate) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }
        SimpleDateFormat fmtDate;
        switch (nFmtDate) {//NOPMD
            default:
            case FMT_DATE_YYYYMMDD:
                fmtDate = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                break;
            case FMT_DATE_YYYYMMDD_HHMMSS:
                fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                break;
            case FMT_DATE_HHMM:
                fmtDate = new SimpleDateFormat("HH:mm", Locale.CHINA);
                break;
            case FMT_DATE_HHMMSS:
                fmtDate = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
                break;
            case FMT_DATE_YYYYMMDDHHMMSS:
                fmtDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
                break;
            case FMT_DATE_EEEMMMddHHmmsszzzyyyy:
                fmtDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                break;
            case FMT_DATE_YYYY_Y_MM_M_DD_D:
                fmtDate = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
                break;
            case FMT_DATE_SPECIAL:
                fmtDate = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
                break;
        }
        try {
            return fmtDate.parse(strDate);
        }
        catch (ParseException e) {
            return null;
        }
    }

    public static int getPartDate(Date date, int flag) {
        if (date == null) {
            return 0;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int result = 0;
        switch (flag) {
            case GET_TIME_OF_YEAR:
                result = cal.get(Calendar.YEAR);
                break;

            case GET_TIME_OF_MONTH:
                result = cal.get(Calendar.MONTH) + 1;
                break;

            case GET_TIME_OF_DAY:
                result = cal.get(Calendar.DAY_OF_MONTH);
                break;

            case GET_TIME_IF_HOUR:
                result = cal.get(Calendar.HOUR_OF_DAY);
                break;

            case GET_TIME_OF_MINUTE:
                result = cal.get(Calendar.MINUTE);
                break;

            case GET_TIME_OF_SECOND:
                result = cal.get(Calendar.SECOND);
                break;

            default:// 注意默认返回一个时间的年份
                result = cal.get(Calendar.YEAR);
                break;

        }
        return result;
    }

    /**
     * 获得一个date 类型的 某个 特殊的 内容
     *
     * 比如 返回 时间的 年 、月、日、时、分、秒
     *
     * @param date
     * @param flag
     * @return
     */
    public static String getPartTime(Date date, int flag) {
        int result = getPartDate(date, flag);
        return String.valueOf(result);
    }

    public static String formatNowTime(int nFmt) {
        Calendar cal = Calendar.getInstance();

        return formatDate(cal.getTime(), nFmt);
    }

    public static String formatDate(Date date, int nFmt) {
        SimpleDateFormat fmtDate = new SimpleDateFormat();
        switch (nFmt) {//NOPMD
            default:
            case FMT_DATE_YYYY:
                fmtDate.applyLocalizedPattern("yyyy");
                break;
            case FMT_DATE_YYYYMMDD:
                fmtDate.applyPattern("yyyy-MM-dd");
                break;
            case FMT_DATE_YYYYMMDD_HHMMSS:
                fmtDate.applyPattern("yyyy-MM-dd HH:mm:ss");
                break;
            case FMT_DATE_HHMM:
                fmtDate.applyPattern("HH:mm");
                break;
            case FMT_DATE_HHMMSS:
                fmtDate.applyPattern("HH:mm:ss");
                break;
            case FMT_DATE_SPECIAL:
                fmtDate.applyPattern("yyyyMMdd");
                break;
            case FMT_DATE_MMDD:
                fmtDate.applyPattern("MM-dd");
                break;
            case FMT_DATE_MMdd:
                fmtDate.applyPattern("MMdd");
                break;
            case FMT_DATE_YYYYMMDDHHMM:
                fmtDate.applyPattern("yyyy-MM-dd HH:mm");
                break;
            case FMT_DATE_MMDD_HHMM:
                fmtDate.applyPattern("MM-dd HH:mm");
                break;
            case FMT_DATE_MMMDDD:
                fmtDate.applyPattern("MM月dd日");
                break;
            case DateUtil.FMT_DATE_YYYYMMDDHHMM_NEW:
                fmtDate.applyPattern("yyyyMMddHHmm");
                break;
            case DateUtil.FMT_DATE_YYYY_Y_MM_M_DD_D:
                fmtDate.applyPattern("yyyy年MM月dd日");
                break;
            case DateUtil.FMT_DATE_YYYYMMDDHHMMSS:
                fmtDate.applyPattern("yyyyMMddHHmmss");
                break;
            case FMT_DATE_YYMMDD:
                fmtDate.applyPattern("yyMMdd");
                break;
            case FMT_DATE_YYMMDDHH:
                fmtDate.applyPattern("yyyyMMddHH");
                break;
            case FMT_DATE_MMDD_HHMM_CH:
                fmtDate.applyPattern("MM月dd日HH时mm分");
                break;
            case FMT_DATE_YY_MM_DD:
                fmtDate.applyPattern("yy/MM/dd");
                break;
            case FMT_DATE_YYYY_MM_DD:
                fmtDate.applyPattern("yyyy/MM/dd");
                break;
        }
        return fmtDate.format(date);
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp getToday(Timestamp ts) {
        try {
            String dateStr = formatDate(ts, FMT_DATE_YYYYMMDD);
            SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            return new Timestamp(fmtDate.parse(dateStr).getTime());
        }
        catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取当前日期的后N天
     * @param after 天数
     * @return
     */
    public static Date getDateAfter(int after) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + after);

        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String dateStr = form.format(calendar.getTime());
        try {
            return form.parse(dateStr);
        }
        catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取当前日期的前N天
     * @param before 天数
     * @return
     */
    public static Date getDateBefore(int before) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - before);

        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String dateStr = form.format(calendar.getTime());
        try {
            return form.parse(dateStr);
        }
        catch (ParseException e) {
            return null;
        }
    }

    public static Timestamp getYesterdayTimestamp() {
        return new Timestamp(getDateBefore(1).getTime());
    }

    public static Date getYesterdayDate() {
        return getDateBefore(-1);
    }

    /**
     * 将日期按照指定的天数增加或者减少，并转换为需要的日期格式
     *
     * @param sourceFormat
     *            原始日期格式
     * @param date2Get
     *            需要转换成的日期
     * @param sourceFormat
     *            需要转换的格式
     * @return date2Get 成功：转换后的日期，失败：null
     */
    public static Date getIntervalDateFormat(String date2Get, String sourceFormat, int days) {
        try {
            SimpleDateFormat sorceFmt = new SimpleDateFormat(sourceFormat, Locale.CHINA);
            Date date = new Date(sorceFmt.parse(date2Get).getTime() + days * 86400000L); // 一天的时间24*3600*1000
            return date;
        }
        catch (ParseException e) {
            e.printStackTrace();
//            log.warn("invalid date2Get :" + date2Get);
            return null;
        }
    }

    /**
     * 按默认日期格式 格式化日期
     *
     * @param target
     * @return 格式化后的日期字符串，如果传入的日期对象为NULL，返回空字符串
     */
    public static String formatDate(Date target) {
        return formatDate(target, DEFAULT_DATE_FORMAT);
    }

    public static String formatTime(Date target) {
        return formatDate(target, DEFAULT_TIME_FORMAT);
    }

    /**
     * 保留小时，去掉分，秒
     * @param target
     * @return
     */
    public static String formatShortDate(Date target) {
        return formatDate(target, "yyyy-MM-dd HH");
    }

    /**
     * 去掉秒
     * @param target
     * @return
     */
    public static String formatLongDate(Date target) {
        return formatDate(target, "yyyy-MM-dd HH:mm");
    }

    /**
     * 获得当前时间的n天前或后
     *
     * @param origin
     * @param intervals
     * @return
     */
    public static Date getIntervalDate(Date origin, long intervals) {
        return new Date(origin.getTime() + intervals * 86400000);
    }

    /**
     * 获得当前时间的n天前或后
     *
     * @param origin
     * @param days
     * @return
     */
    public static Timestamp getIntervalDate(Timestamp origin, long days) {
        return new Timestamp(origin.getTime() + days * 86400000);
    }

    /**
     * 获得指定时间间隔的timestamp
     *
     * @param ts
     * @param minutes
     * @return
     */
    public static Timestamp getIntervalTimestamp(Timestamp ts, int minutes) {
        return new Timestamp(ts.getTime() + minutes * 60 * 1000L);
    }

    /**
     * 获得指定时间间隔的timestamp
     *
     * @param ts
     * @param seconds
     * @return
     */
    public static Timestamp getIntervalTimestampBySeconds(Timestamp ts, long seconds) {
        return new Timestamp(ts.getTime() + seconds * 1000L);
    }

    @SuppressWarnings("deprecation")
    public static Date transToQueryDate(Date date) {
        Calendar c = new GregorianCalendar();// 新建日期对象
        c.set(date.getYear() + 1900, date.getMonth(), date.getDate(), 0, 0, 0);
        return c.getTime();
    }

    /**
     * 返回两个时间间隔的分钟数
     *
     * @param from
     * @param to
     * @return
     */
    public static long getDiffMinutes(Timestamp from, Timestamp to) {
        return (to.getTime() - from.getTime()) / (60 * 1000);
    }

    /**
     * 获得两个时间之内的毫秒数
     *
     * @param from
     * @param to
     * @return
     */
    public static long getDiffMsecs(Timestamp from, Timestamp to) {
        return to.getTime() - from.getTime();
    }

    /**
     * 获得两个时间之间的天数
     * @param from
     * @param to
     * @return
     */
    public static int getDiffDays(Timestamp from, Timestamp to) {
        return (int) (getDiffMinutes(from, to) / 1440);
    }

    public static boolean isInThisDay(Timestamp day, Timestamp time) {
        Timestamp beginTime = getBeginOfCurrentDate(day);
        Timestamp endTime = getBeginOfCurrentDate(getIntervalTimestamp(beginTime, 60 * 24));

        if (time.before(beginTime) || time.after(endTime))
            return false;
        return true;

    }

    public static boolean isBetween(Date date, Date start, Date end) {
        boolean isBetween = date.compareTo(start) >= 0 && date.getTime() <= (end.getTime() + 999);
//        log.debug("date == " + date + ", start == " + start + ", end == " + end + ", isBetween:" + isBetween);
        return isBetween;
    }

    public static boolean isToday(Date start) {
        long beginOfToday = getBeginOfToday().getTime();
        long endOfToday = getEndOfToday().getTime();
//        log.debug("begin of today:" + getBeginOfToday());
//        log.debug("end of today:" + getEndOfToday());
        boolean result = start.getTime() >= beginOfToday && start.getTime() <= endOfToday;
//        log.debug("beginOfToday:" + beginOfToday + ", endOfToday:" + endOfToday + ", start:" + start.getTime()
//                + ", result:" + result);
        return result;
    }

    public static Timestamp getBeginOfToday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String dateStr = form.format(cal.getTime()) + " 00:00:00";
        Date date = null;
        try {
            date = form.parse(dateStr);
        }
        catch (ParseException e) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    public static Timestamp getEndOfToday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String dateStr = form.format(cal.getTime()) + " 23:59:59";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            date = sdf.parse(dateStr);
        }
        catch (ParseException e) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    /**
     * 获取传入的参数时间的第二天的开始时间
     * @param curTimestamp
     * @return
     */
    public static Timestamp getBeginOfNextDay(Timestamp curTimestamp) {
        curTimestamp.setHours(0);
        curTimestamp.setMinutes(0);
        curTimestamp.setSeconds(0);
        curTimestamp.setNanos(0);
        Timestamp nextDay = new Timestamp(curTimestamp.getTime() + 24 * 60 * 60 * 1000);
        return nextDay;
    }

    public static Timestamp getBeginHourOfToday(int hour) {
        if (hour == 0) {
            return getBeginOfToday();
        }
        Calendar cal = Calendar.getInstance();
        int curHour = cal.get(Calendar.HOUR_OF_DAY);
        if (curHour < hour) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String dateStr = form.format(cal.getTime());
        if (hour < 10) {
            dateStr += " 0" + hour + ":00:00";
        }
        else {
            dateStr += " " + hour + ":00:00";
        }
        Date date = null;
        try {
            form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            date = form.parse(dateStr);
        }
        catch (ParseException e) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    public static Timestamp getEndHourOfToday(int hour) {
        if (hour == 0) {
            return getEndOfToday();
        }
        Calendar cal = Calendar.getInstance();
        int curHour = cal.get(Calendar.HOUR_OF_DAY);
        if (curHour >= hour) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String dateStr = form.format(cal.getTime());
        if (hour < 11) {
            dateStr += " 0" + (hour - 1) + ":59:59";
        }
        else {
            dateStr += " " + (hour - 1) + ":59:59";
        }
        Date date = null;
        try {
            form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            date = form.parse(dateStr);
        }
        catch (ParseException e) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    public static boolean isYesterday(Date start) {
        Date today = getBeginOfToday();
        Date yesterday = getIntervalDate(today, -1);
        return start.getTime() >= yesterday.getTime() && start.getTime() < today.getTime();
    }

    public static boolean isTomorrow(Date start) {
        Date today = getBeginOfToday();
        Date yesterday = getIntervalDate(today, 1);
        return start.getTime() >= yesterday.getTime() && start.getTime() < today.getTime();
    }

    /**
     * 按自定义日期格式格式化日期
     *
     * @param target
     * @param format
     * @return 格式化后的日期字符串，如果传入的日期对象为NULL，返回空字符串
     */
    public static String formatDate(Date target, String format) {
        if (target == null) {
            return "";
        }
        return new SimpleDateFormat(format, Locale.CHINA).format(target);
    }

    public static Date getFormatDateByDate(Date date) throws ParseException {
        String target = formatDate(date);
        if (StringUtils.isBlank(target)) {
            return null;
        }
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.CHINA).parse(target);
    }

    public static String formatTime(Timestamp target) {
        if (target == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(target);
    }

    public static String formatTime(Timestamp target, String format) {
        if (target == null) {
            return "";
        }
        return new SimpleDateFormat(format, Locale.CHINA).format(target);
    }

    public static Timestamp formatString(String target, String format) {

        if (StringUtils.isBlank(target)) {
            return null;
        }
        Date date;
        try {
            date = new SimpleDateFormat(format, Locale.CHINA).parse(target);
            return new Timestamp(date.getTime());
        }
        catch (ParseException e) {
            return null;
        }
    }

    /**
     * @author wangmeng
     * 比较两个时间，if: firstDate before nextDate return true;
     *  else: return true;
     * @param firstDate
     * @param nextDate
     * @return
     */
    public static boolean compareDateBoolean(Date firstDate, Date nextDate) {
        if (firstDate.before(nextDate)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 将字符串格式化为日期对象
     * @param dateStr
     * @param format
     * @return
     */
    public static Timestamp formatToTimestamp(String dateStr, String format) {

        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            SimpleDateFormat sorceFmt = new SimpleDateFormat(format, Locale.CHINA);
            return new Timestamp(sorceFmt.parse(dateStr).getTime()); // 一天的时间24*3600*1000
        }
        catch (ParseException e) {
            e.printStackTrace();
//            log.warn("invalid date2Get :" + dateStr, e);
            return null;
        }
    }

    /**
     * 将字符串格式化为日期对象
     * @param date
     * @param format
     * @return 如果date为空或格式不标准，返回NULL，否则返回对应的日期对象
     */
    public static Date formatToDate(String date, String format) {
        try {
            if (StringUtils.isBlank(date)) {
                return null;
            }

            SimpleDateFormat sorceFmt = new SimpleDateFormat(format, Locale.CHINA);
            return new Date(sorceFmt.parse(date).getTime());
        }
        catch (ParseException e) {
            e.printStackTrace();
//            log.warn("invalid date :" + date);
            return null;
        }
    }

    /**
     * 获得几天前或几天后的，制定格式的日期
     */
    public static Timestamp getDatePoint(String format, int day) {
        Date d = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return new Timestamp(now.getTimeInMillis());
    }

    //获得当前月份。
    public static String getCurrentMonth() {
        return new SimpleDateFormat("yyyyMM", Locale.CHINA).format(new Date());
    }

    //获得当前月份。
    public static String getCurrentHour() {
        return new SimpleDateFormat("HH", Locale.CHINA).format(new Date());
    }

    /**
     * 获得N年前的1月1号
     * @param date
     * @param before
     * @return
     */
    public static Date getYearBefore(Date date, int before) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - before);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String dateStr = form.format(calendar.getTime());
        try {
            date = form.parse(dateStr);
        }
        catch (ParseException e) {
            return null;
        }
        return date;

    }

    public static Date getMonthBefore(Date date, int before) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - before);
        calendar.set(Calendar.DATE, 1);

        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String dateStr = form.format(calendar.getTime());
        try {
            date = form.parse(dateStr);
        }
        catch (ParseException e) {
            return null;
        }
        return date;

    }

    public static Integer getAge(Timestamp birthday) {
        Date now = new Date();
        Long i = (now.getTime() - birthday.getTime()) / (1000 * 60 * 60 * 24);
        Double result = (i / 365.0d);
        return result.intValue() + 1;
    }

    //获取N天前的凌晨时间。
    public static Date getIntervalBeginOfDay(Date date, int i) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - i);

        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String dateStr = form.format(calendar.getTime()) + " 00:00:00";
        try {
            date = form.parse(dateStr);
        }
        catch (ParseException e) {
            return null;
        }
        return date;

    }

    /**
     * 取得月份 yyyyMM
     * @param time
     * @return
     */
    public static String getMonth(Timestamp time) {
        return new SimpleDateFormat("yyyyMM", Locale.CHINA).format(time);
    }

    public static Timestamp getTimestampFromStr(String dateStr, String dateFormat) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.CHINA);
            date = sdf.parse(dateStr);
        }
        catch (ParseException e) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    public static String getWeekDay(Timestamp time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int result = cal.get(Calendar.DAY_OF_WEEK);
        String weekDay = "";
        switch (result) {
            case 2:
                weekDay = "星期一";
                break;
            case 3:
                weekDay = "星期二";
                break;
            case 4:
                weekDay = "星期三";
                break;
            case 5:
                weekDay = "星期四";
                break;
            case 6:
                weekDay = "星期五";
                break;
            case 7:
                weekDay = "星期六";
                break;
            case 1:
                weekDay = "星期日";
                break;
            default:
                break;
        }
        return weekDay;
    }

    //获取年月日
    public static String[] getDateInfo(Timestamp time) {
        String[] dateInfo = new String[3];
        String formatDate = formatDate(time);
        dateInfo = formatDate.split("-");
        return dateInfo;
    }

    public static String getDateMonth(Timestamp time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int result = 0;
        result = cal.get(Calendar.MONTH) + 1;
        return String.valueOf(result);
    }

    /**
     * 获得当前时间的n秒前或后
     *
     * @param origin
     * @param seconds
     * @return
     */
    public static Date getIntervalSeconds(Date origin, long seconds) {
        return new Date(origin.getTime() + seconds * 1000L);
    }

    /***
     * 将秒数转化成 mm:ss格式例如37:02，当分钟和秒数小于10则补0
     * @param seconds
     * @return
     */
    public static String getFormatedMinutesBySeconds(long seconds) {
        return (seconds / 60 > 9 ? "" : "0") + seconds / 60 + ":" + (seconds % 60 > 9 ? "" : "0") + seconds % 60;
    }

    /**
     * Description:
     * 获得给定时间戳的当天的开始时间戳(凌晨)
     * 2013-07-17 00:00:00.0
     * @param date
     * Date:2013-7-17
     * @author zhangt@corp.netease.com
     */
    public static Timestamp getBeginOfCurrentDate(Timestamp date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String dateStr = form.format(cal.getTime()) + " 00:00:00";
        Date BeginOfCurrentDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            BeginOfCurrentDate = sdf.parse(dateStr);
        }
        catch (ParseException e) {
            return null;
        }
        return new Timestamp(BeginOfCurrentDate.getTime());
    }

    /**
     * Description:
     * 获得给定时间戳的当天的结束时间戳
     * 2013-07-17 23:59:59.0
     * @param date
     * Date:2013-7-17
     * @author zhangt@corp.netease.com
     */
    public static Timestamp getEndOfCurrentDate(Timestamp date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String dateStr = form.format(cal.getTime()) + " 23:59:59";
        Date endOfCurrentDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            endOfCurrentDate = sdf.parse(dateStr);
        }
        catch (ParseException e) {
            return null;
        }
        return new Timestamp(endOfCurrentDate.getTime());

    }

    public static Date getLastDateOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Date getFirstDateOfMonth(Integer year, Integer month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        return cal.getTime();
    }

    //获得当前月份。
    public static String getMonthInt(Timestamp currentTimestamp) {
        return new SimpleDateFormat("MM", Locale.CHINA).format(currentTimestamp);
    }

    public static int getMonthDays(Timestamp currentTimestamp) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, currentTimestamp.getYear());
        cal.set(Calendar.MONTH, currentTimestamp.getMonth());//Java月份才0开始算
        int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
        return dateOfMonth;
    }

    //获得日期的小时数。
    public static int getDayHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    //获取给定日期所在的月份的最后一天
    public static Date getEndOfMonth(Date beginDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.SECOND, -1);
        return cal.getTime();
    }

    /**
     * 判断时间是否在时间段内
     * @param patten
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(String startTime, String endTime, String patten) {
        try {

            Date beginDate = null;
            Date endDate = null;
            SimpleDateFormat df = new SimpleDateFormat(patten, Locale.CHINA);//设置日期格式
            Date nowDate = df.parse(df.format(new Date()));
            beginDate = df.parse(startTime);
            endDate = df.parse(endTime);
            Calendar date = Calendar.getInstance();
            date.setTime(nowDate);

            Calendar begin = Calendar.getInstance();
            begin.setTime(beginDate);

            Calendar end = Calendar.getInstance();
            end.setTime(endDate);

            if (date.after(begin) && date.before(end)) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
//            log.error("日期解析错误", e);
            return false;
        }
    }

    public static String getCurrentMillisecondTime() {
        Date currentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmsssss",
                Locale.CHINA);
        String timestamp = simpleDateFormat.format(currentTime);
        return timestamp;
    }

    public static Timestamp getPreDate(Timestamp date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTime().getTime());
    }

    public static Timestamp getCurDate(Timestamp date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTime().getTime());
    }

    /**
     * 获取两个日期之间的日期
     * @param start 开始日期
     * @param end 结束日期
     * @return 日期集合
     */
    public static List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    /**
     * 判断某个时间是否是在当前时间的某几天之内
     * @param addtime
     * @return
     */
    public static boolean isLatestDays(Date addtime, int lastDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getBeginOfToday());
        calendar.add(Calendar.DAY_OF_MONTH, 0 - lastDays);
        Date before7days = calendar.getTime();
        if (before7days.getTime() < addtime.getTime()) {
            return true;
        }
        else {
            return false;
        }
    }

    public static Date getDate(String date, String format) throws ParseException {
        SimpleDateFormat sorceFmt = new SimpleDateFormat(format);
        return new Date(sorceFmt.parse(date).getTime());
    }

    public static Date getDate(int year, int month, int day) {
        return getDate(year, month, day, 0, 0, 0);
    }

    public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, second);
        return calendar.getTime();
    }
}
