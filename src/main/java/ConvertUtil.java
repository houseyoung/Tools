import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ConvertUtil {
    /**
     * 利用paramterMap中的参数值，构建一个clazz类型的对象
     * @param clazz
     * @param paramterMap
     * @param <T>
     * @return
     */
    public static <T> T convertReq(Class<T> clazz, Map<String, String[]> paramterMap) {

        if (null != clazz && null != paramterMap) {
            try {
//                logger.debug("[接受参数][{}]{}", clazz.getSimpleName(), JacksonUtil.encode(paramterMap));
                T result = clazz.newInstance();
                BeanWrapper beanWrapper = new BeanWrapperImpl(result);
                paramterMap.entrySet().stream().filter(item -> null != item && item.getValue().length != 0)
                        .forEach(item -> {
                            if (beanWrapper.isWritableProperty(item.getKey())) {
                                if (item.getValue().length > 1) {
                                    beanWrapper.setPropertyValue(item.getKey(), item.getValue());
                                }
                                else {
                                    beanWrapper.setPropertyValue(item.getKey(), item.getValue()[0].trim());
                                }
                            }
                        });

                return result;
            }
            catch (Throwable e) {
                e.printStackTrace();
//                logger.error("convert the request param map error!{},{}", JacksonUtil.encode(paramterMap),
//                        clazz.getName(), e);
            }
        }
        return null;
    }

    /**
     * 将thisObj中的属性值转换为map
     * @param thisObj
     * @return
     */
    public static Map<String, Object> converMap(Object thisObj) {
        Map<String, Object> map = new HashMap<>();
        Class clazz;
        try {
            clazz = Class.forName(thisObj.getClass().getName());
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                if (methodName.startsWith("get") && !methodName.startsWith("getClass")) {
                    Object value = method.invoke(thisObj);
                    String key = methodName.substring(3);
                    key = key.substring(0, 1).toLowerCase() + key.substring(1);
                    if (value != null) {
                        map.put(key, value);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
//            logger.error("convert object to map error! object: {}", JacksonUtil.encode(thisObj), e);
        }
        return map;
    }

    /**
     * 将请求的参数转为json格式的字符串
     * pageNo pageSize去掉引号（数据通用接口要求）
     * 没有参数时返回{}
     * @param paramterMap
     * @return
     */
    public static String convertReqToJsonString(Map<String, String[]> paramterMap) {
        JSONObject jsonObject = new JSONObject();
        String param;
        for (Map.Entry<String, String[]> entry : paramterMap.entrySet()) {
            param = entry.getValue()[0];
            if ((entry.getKey().contains("pageNo")) || (entry.getKey().contains("pageSize"))) {
                try {
                    jsonObject.put(entry.getKey(), Long.parseLong(param));
                }
                catch (Exception e) {
                    jsonObject.put(entry.getKey(), param);
                }
            }
            else {
                jsonObject.put(entry.getKey(), param);
            }
        }
        return jsonObject.toString();
    }

    //转化对象
    public static <T> T modifyVo(T vo, Map<String, String> paramterMap) {

        if (null != vo && null != paramterMap) {
            try {
//                logger.info("[接受参数][{}]{}", vo.getClass().getSimpleName(), JacksonUtil.encode(paramterMap));
                BeanWrapper beanWrapper = new BeanWrapperImpl(vo);
                paramterMap.entrySet().stream().filter(item -> null != item && null != item.getValue())
                        .forEach(item -> {
                            beanWrapper.setPropertyValue(item.getKey(), item.getValue());
                        });

                return vo;
            }
            catch (Throwable e) {
                e.printStackTrace();
//                logger.error("convert the request param map error!{},{}", JacksonUtil.encode(paramterMap),
//                        vo.getClass().getName(), e);
            }
        }
        return null;
    }

}
