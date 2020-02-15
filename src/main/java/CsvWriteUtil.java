import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017/6/28.
 */
public class CsvWriteUtil {
    private static final String CHANGE_LINE = "\r\n";
    private static final String DELIMITER = ",";
    public static final String CSV_SUFFIX = ".csv";
    public static final String ZIP_SUFFIX = ".zip";
    private static final DecimalFormat floatDecimalFormat = new DecimalFormat("0.00");
    private static final DecimalFormat integerDecimalFormat = new DecimalFormat("0");
    private String fileName;
    private OutputStreamWriter out; // 写银行的csv需要utf-8
    private String md5_value;

    public CsvWriteUtil(String fileName) {
        this.fileName = fileName;
    }

    public void writeCsv(CsvDataVo csvDataVo) throws Exception {
        Writer fw = null;
        try {
            String[] titleName = csvDataVo.getTitleName();
            List<?> dataList = csvDataVo.getDataList();
            Map<String, String> strategy = csvDataVo.getStrategy();
            String[] titleColumn = csvDataVo.getTitleColumn();
            String csvFileName = fileName + CSV_SUFFIX;
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFileName, true),
                    StandardCharsets.UTF_8));
            String header = generateHeader(titleName);
            if (!csvDataVo.isHasWriteHeader()) {
                fw.append(header);
            }
            int length = titleName.length;
            String content = "";
            for (int i = 0; i < dataList.size(); i++) {
                Object obj = dataList.get(i); //获得该对象
                Class clazz = obj.getClass(); //获得该对对象的class实例
                StringBuilder sb = new StringBuilder();
                for (int columnIndex = 0; columnIndex < length; columnIndex++) {
                    String title = titleColumn[columnIndex].trim();
                    if (!"".equals(title)) { //字段不为空
                        String methodName = "get" + StringUtils.capitalize(title); // 使其首字母大写;
                        // 设置要执行的方法
                        Method method = clazz.getDeclaredMethod(methodName);
                        //获取返回类型
                        String returnType = method.getReturnType().getName();
                        String data = method.invoke(obj) == null ? "" : method.invoke(obj).toString();
                        if (data != null && !"".equals(data)) {
                            if (strategy.containsKey(title)) {
                                if (StringUtils.equalsIgnoreCase(strategy.get(title), "TimeStamp")) {
                                    try {
                                        data = format(new Date(Long.parseLong(data)), "yyyy-MM-dd HH:mm:ss");
                                    }
                                    catch (Throwable e) {
                                        data = format(Timestamp.valueOf(data), "yyyy-MM-dd HH:mm:ss");
                                    }
                                    sb.append(data);
                                }
                                else if (StringUtils.equalsIgnoreCase(strategy.get(title), "number")) {
                                    writeNumberData(sb, data, "0.00");
                                }
                                else {
                                    Class cls = Class.forName(
                                            "com.netease.nsip.admin.common.model.enums.dict." + strategy.get(title));
                                    Object enum1 = EnumUtil.getEnum(cls, "code", Integer.valueOf(data));
                                    Field field = cls.getDeclaredField("desc");
                                    field.setAccessible(true);
                                    try {
                                        sb.append(String.valueOf(field.get(enum1)));
                                    }
                                    catch (Throwable ignored) {
                                    }
                                }
                            }
                            else if ("int".equals(returnType) || returnType.contains("Integer")) {
                                writeNumberData(sb, data, "0");
                            }
                            else if ("long".equals(returnType) || returnType.contains("Long")) {
                                writeNumberData(sb, data, "0");
                            }
                            else if ("float".equals(returnType)) {
                                writeNumberData(sb, data, "0.00");
                            }
                            else if ("double".equals(returnType)) {
                                writeNumberData(sb, data, "0.00");
                            }
                            else {
                                sb.append(data);
                            }
                        }
                        else {
                            sb.append(data);
                        }
                        sb.append(DELIMITER);
                    }
                }
                String insertLine = sb.substring(0, sb.length() - 1) + CHANGE_LINE;
                fw.append(insertLine);
                fw.flush();
                content += insertLine;
            }
            md5_value = MD5Util.convertToMd5(header + content);
        }
        finally {
            if (fw != null) {
                fw.close();
            }
        }
    }

    public String generateHeader(String[] titleNames) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (String titleName : titleNames) {
            stringBuilder.append(titleName);
            stringBuilder.append(DELIMITER);
        }
        String res = stringBuilder.substring(0, stringBuilder.length() - 1);
        res = res + CHANGE_LINE;
        return res;
    }

    /**
     * 写入小数，失败时当成字符串写入
     * 使用BigDecimal是为了保证精度
     * @param stringBuilder 写入数据
     * @param data 数据内容
     */
    private void writeNumberData(StringBuilder stringBuilder, String data, String formatString) {
        try {
            BigDecimal realData = new BigDecimal(data);
            if ("0.00".equals(formatString)) {
                stringBuilder.append(floatDecimalFormat.format(realData));
            }
            else if ("0".equals(formatString)) {
                stringBuilder.append(integerDecimalFormat.format(realData));
            }
        }
        catch (Exception e) {
            stringBuilder.append(data);
            e.printStackTrace();
        }
    }

    private StringBuilder getSb(Map<String, String> strategy, String title, String obj, String returnType)
            throws Exception {
        StringBuilder sb = new StringBuilder();
        if (strategy.containsKey(title)) {
            if (StringUtils.equalsIgnoreCase(strategy.get(title), "TimeStamp")) {
                try {
                    obj = format(new Date(Long.parseLong(obj)), "yyyy-MM-dd HH:mm:ss");
                }
                catch (Throwable e) {
                    obj = format(Timestamp.valueOf(obj), "yyyy-MM-dd HH:mm:ss");
                }
                sb.append(obj);
            }
            else if (StringUtils.equalsIgnoreCase(strategy.get(title), "number")) {
                writeNumberData(sb, obj, "0.00");
            }
            else if ("int".equals(returnType) || returnType.contains("Integer")) {
                writeNumberData(sb, obj, "0");
            }
            else if ("long".equals(returnType) || returnType.contains("Long")) {
                writeNumberData(sb, obj, "0");
            }
            else if ("float".equals(returnType)) {
                writeNumberData(sb, obj, "0.00");
            }
            else if ("double".equals(returnType)) {
                writeNumberData(sb, obj, "0.00");
            }
        }
        else {
            sb.append(obj);
        }
        return sb;
    }

    /**
     * 将数据写入csv
     * @param csvDataVo
     * @throws Exception
     */
    public void writeOfflineBankCsvDate(CsvDataVo csvDataVo) throws Exception {
        try {
            List<?> dataList = csvDataVo.getDataList();
            String[] titleColumn = csvDataVo.getTitleColumn();
            String csvFileName = fileName + CSV_SUFFIX;
            out = new OutputStreamWriter(new FileOutputStream(csvFileName), StandardCharsets.UTF_8);
            if (!csvDataVo.isHasWriteHeader()) {
                // 银行第一行写如总数
                out.append(String.valueOf(csvDataVo.getDataList().size())).append(CHANGE_LINE);
            }
            int length = csvDataVo.getTitleColumn().length;
            for (int i = 0; i < dataList.size(); i++) {
                Object obj = dataList.get(i);
                Class clazz = obj.getClass(); //获得该对对象的class实例
                StringBuilder sb = new StringBuilder();
                for (int columnIndex = 0; columnIndex < length; columnIndex++) {
                    String title = titleColumn[columnIndex].trim();
                    if (!"".equals(title)) { //字段不为空
                        String methodName = "get" + StringUtils.capitalize(title); // 使其首字母大写;
                        // 设置要执行的方法
                        Method method = clazz.getDeclaredMethod(methodName);
                        //获取返回类型
                        String data = method.invoke(obj) == null ? "" : method.invoke(obj).toString();
                        sb.append(data);
                        sb.append(DELIMITER);
                    }
                }
                String insertLine = sb.substring(0, sb.length() - 1);
                out.append(insertLine);
                if (i + 1 != dataList.size()) {
                    // 不是最后一行添加换行
                    out.append(CHANGE_LINE);
                }
                out.flush();
            }
        }
        catch (RuntimeException e){
            e.printStackTrace();
        }
        finally {
            out.close();
        }
    }

    public boolean deleteSourceFile() {
        File file = new File(fileName + CSV_SUFFIX);
        boolean result = file.delete();
        if (!result) {
//            logger.error("[删除源文件失败],fileName:{}", fileName + CSV_SUFFIX);
        }
        return result;
    }

    public static String format(Date d, String format) {
        return d == null ? null : (new SimpleDateFormat(format)).format(d);
    }

    public static String format(Timestamp time, String format) {
        return time == null ? null : (new SimpleDateFormat(format)).format(time);
    }

    /**
     * Created on 2017/6/28.
     */
    public static class CsvDataVo {
        private String titleColumn[];
        private String titleName[];
        private List<?> dataList;
        private Map<String, String> strategy;
        private boolean hasWriteHeader;

        public CsvDataVo(String[] titleColumn, String[] titleName, List<?> dataList, Map<String, String> strategy,
                         boolean hasWriteHeader) {
            this.titleColumn = titleColumn;
            this.titleName = titleName;
            this.dataList = dataList;
            this.strategy = strategy;
            this.hasWriteHeader = hasWriteHeader;
        }

        public CsvDataVo(String[] titleColumn, List<?> dataList, Map<String, String> strategy,
                         boolean hasWriteHeader) {
            this.titleColumn = titleColumn;
            this.dataList = dataList;
            this.strategy = strategy;
            this.hasWriteHeader = hasWriteHeader;
        }

        public String[] getTitleColumn() {
            return Arrays.copyOf(titleColumn, titleColumn.length);
        }

        public void setTitleColumn(String[] titleColumn) {
            this.titleColumn = titleColumn;
        }

        public String[] getTitleName() {
            return Arrays.copyOf(titleName, titleName.length);
        }

        public void setTitleName(String[] titleName) {
            this.titleName = titleName;
        }

        public List<?> getDataList() {
            return dataList;
        }

        public void setDataList(List<?> dataList) {
            this.dataList = dataList;
        }

        public Map<String, String> getStrategy() {
            return strategy;
        }

        public void setStrategy(Map<String, String> strategy) {
            this.strategy = strategy;
        }

        public boolean isHasWriteHeader() {
            return hasWriteHeader;
        }

        public void setHasWriteHeader(boolean hasWriteHeader) {
            this.hasWriteHeader = hasWriteHeader;
        }

        @Override
        public String toString() {
            return "CsvWriteUtil.CsvDataVo{" + "titleColumn=" + Arrays.toString(titleColumn) + ", titleName="
                    + Arrays.toString(titleName) + ", dataList=" + dataList + ", strategy=" + strategy + ", hasWriteHeader="
                    + hasWriteHeader + '}';
        }
    }
}
