import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by bjlinsen on 2018/12/26.
 */
public class JsonUtil {
    /**
     * 枚举转jsonObject
     * @param returnEnum
     * @return
     */
    public static JSONObject enumToJson(Object returnEnum) {
        Class clazz = returnEnum.getClass();
        if (!clazz.isEnum()) {
//            log.warn("参数不是一个枚举类型,enumArr={}", returnEnum);
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        Field[] fieldArr = clazz.getDeclaredFields();
        for (int i = 0; i < fieldArr.length; i++) {
            String fieldName = fieldArr[i].getName();
            try {
                String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method m = clazz.getMethod(methodName);
                Object value = m.invoke(returnEnum);
                jsonObject.put(fieldName, value);
            }
            catch (Exception ex) {
                continue;
            }
        }
        return jsonObject;
    }
}
