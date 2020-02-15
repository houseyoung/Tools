import com.google.common.base.Charsets;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 读取property文件中的配置
 * 
 */
public class PropertyUtil {
    private static final String MODULE_NAME = "[PropertyUtil]";
    private static final String PROJECT_NAME_KEY = "uniqueProjectName";
    // 配置文件列表
    private static final String[] config = { "config" };

    private static Map<String, String> configMap = new ConcurrentHashMap<String, String>();
    private static boolean bInit = false;

    static {
        refresh();
    }

    public static void refresh() {
        try {
            for (int i = 0; i < config.length; i++) {
                Properties configProperties = null;
                InputStream in = null;
                BufferedReader bf = null;
                InputStreamReader inputStreamReader = null;
                try {

                    in = PropertyUtil.class.getResourceAsStream("/" + config[i] + ".properties");
                    inputStreamReader = new InputStreamReader(in, Charsets.UTF_8);
                    bf = new BufferedReader(inputStreamReader);
                    configProperties = new Properties();
                    configProperties.load(bf);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
//                    logger.error("加载配置文件出错,文件名称：{}", config[i], ex);
                }
                finally {
                    try {
                        if (null != in) {
                            in.close();
                        }
                        if (null != inputStreamReader) {
                            inputStreamReader.close();
                        }
                        if (null != bf) {
                            bf.close();
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
//                        logger.error("文件流关闭失败", e);
                    }
                }

                if (configProperties != null) {

                    for (Object key : configProperties.keySet()) {
                        String strKey = (String) key;
                        configMap.put(strKey, configProperties.getProperty(strKey));
                    }
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
//            logger.error(MODULE_NAME + "读取配置文件中的信息出错", e);
        }

        bInit = true;
    }

    public static String getProperty(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return configMap.get(key);
    }

    /**
     * 获取不到时设置默认值
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getProperty(String key, String defaultValue) {
        if (!bInit) {
            refresh();
        }
        String value = defaultValue;
        value = configMap.get(key);
        if (null == value) {
            value = defaultValue;
        }
        return value;
    }

    public static int getPropertyIntValue(String key, int defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return defaultValue;
        }
        return (StringUtils.isEmpty(configMap.get(key)) || !ValidatorUtil.isInt(configMap.get(key))) ? defaultValue
                : Integer.parseInt(configMap.get(key));
    }

    public static String getFileProperty(String filename, String propertyName) {
        return getFileProperty(filename, propertyName, null);
    }

    public static String getFileProperty(String filename, String propertyName, String defaultValue) {
        InputStream in = null;
        try {
            in = PropertyUtil.class.getResourceAsStream("/" + filename);
            Properties config = new Properties();
            config.load(in);
            String property = config.getProperty(propertyName);
            if (property == null) {
                property = defaultValue;
            }
            return property;
        }
        catch (Exception e) {
            e.printStackTrace();
//            logger.error(MODULE_NAME + "读取配置文件中的信息出错，propertyName:" + propertyName + ",filename:" + filename, e);
        }
        finally {
            if (null != in) {
                try {
                    in.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
//                    logger.error(MODULE_NAME + "关闭输入流出错", e);
                }
            }
        }
        return defaultValue;
    }

    public static int getFilePropertyIntValue(String filename, String propertyName, int defaultValue) {
        if (StringUtils.isAnyBlank(filename, propertyName)) {
            return defaultValue;
        }
        String value = getFileProperty(filename, propertyName);
        return !ValidatorUtil.isInt(value) ? defaultValue : Integer.parseInt(value);
    }

    public static Properties loadProperties(String fileName) {
        Properties configProperties = null;
        InputStream in = null;
        BufferedReader bf = null;
        InputStreamReader inputStreamReader = null;
        try {

            in = PropertyUtil.class.getResourceAsStream("/" + fileName);
            inputStreamReader = new InputStreamReader(in, Charsets.UTF_8);
            bf = new BufferedReader(inputStreamReader);
            configProperties = new Properties();
            configProperties.load(bf);
            return configProperties;
        }
        catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("加载配置文件出错,文件名称：{}", fileName, ex);
            return new Properties();
        }
        finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != inputStreamReader) {
                    inputStreamReader.close();
                }
                if (null != bf) {
                    bf.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
//                logger.error("文件流关闭失败", e);
            }
        }
    }

    public static String getProjectName() {
        String productId = getProperty("nsip.productId");
        String applicationName = getProperty("nsip.applicationName");

        if (StringUtils.isAnyBlank(productId, applicationName)) {
//            logger.warn("项目名称配置获取失败，检查nsip-agent.properties配置文件是否配置正确");
            return null;
        }
        else {
            return productId + applicationName;
        }
    }
}
