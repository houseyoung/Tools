import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.List;

/**
 * 生成csv文件的工具类
 * Created by houseyoung on 2019/11/1 15:43.
 */
public class CsvUtil {
    public static boolean createCsvFile(List<String> titleList, List<List<String>> dataList, String fileName) {
        if (CollectionUtils.isEmpty(titleList) || CollectionUtils.isEmpty(dataList) || StringUtils.isEmpty(fileName)) {
//            log.error("[CsvUtils] 参数错误, titleList: {}, dataList: {}, fileName: {}", titleList, dataList, fileName);
            return false;
        }
        if (titleList.size() != dataList.get(0).size()) {
//            log.error("[CsvUtils] 数据列数与title列数不匹配, title: {}, 数据: {}", titleList.size(), dataList.get(0).size());
            return false;
        }

        BufferedWriter bufferedWriter = null;
        try {
            File csvFile = new File(fileName);
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);

            // 写入title
            writeRow(titleList, bufferedWriter);

            // 写入内容
            for (List<String> rowList : dataList) {
                writeRow(rowList, bufferedWriter);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            log.error("[CsvUtils] 异常, titleList: {}, dataList: {}, fileName: {}", titleList, dataList, fileName, e);
            return false;
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
//                log.error("[CsvUtils] close bufferedWriter 异常, titleList: {}, dataList: {}, fileName: {}", titleList, dataList, fileName, e);
            }
        }

        return true;
    }

    private static void writeRow(List<String> row, BufferedWriter bufferedWriter) throws IOException {
        for (String str : row) {
            if (StringUtils.isNotEmpty(str)) {
                String rowStr = "\"" + str + "\",";
                bufferedWriter.write(rowStr);
            } else {
                bufferedWriter.write(",");
            }
        }

        bufferedWriter.newLine();
    }
}
