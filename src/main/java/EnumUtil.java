import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EnumUtil {
    public static <T extends Enum, I> boolean getIfPresent(Class<T> enumClass, String attName, I attValue) {
        if (null == attValue) {
            return true;
        }
        T[] constants = enumClass.getEnumConstants();
        if (ArrayUtils.isEmpty(constants)) {
            return false;
        }
        Field field;
        for (T constant : constants)
            try {
                field = constant.getClass().getDeclaredField(attName);
                field.setAccessible(true);
                I value = (I) field.get(constant);
                if (value.equals(attValue)) {
                    return true;
                }
            }
            catch (NoSuchFieldException | IllegalAccessException e) {
                return false;
            }
        return false;
    }

    public static <T extends Enum, I> T getEnum(Class<T> enumClass, String attName, I attValue) {
        if (null == attValue) {
            return null;
        }
        T[] constants = enumClass.getEnumConstants();
        if (ArrayUtils.isEmpty(constants)) {
            return null;
        }
        Field field;
        for (T constant : constants)
            try {
                field = constant.getClass().getDeclaredField(attName);
                field.setAccessible(true);
                I value = (I) field.get(constant);
                if (value.equals(attValue)) {
                    return constant;
                }
            }
            catch (NoSuchFieldException | IllegalAccessException e) {
                return null;
            }
        return null;
    }

    /**
     * 反射获取枚举的code和desc的map对象
     * 只可以获取只含有code合desc的枚举对象
     * @param classPath
     * @param className
     * @param isInnerClass
     * @return
     */
    public static Map<Integer, String> getEnum(String classPath, String className, Boolean isInnerClass) {
        Map<Integer, String> result = new HashMap<>();
        try {
            String split = isInnerClass ? "$" : ".";
            Class cls = Class.forName(classPath + split + className);
            Object[] constants = cls.getEnumConstants();
            Method[] methods = cls.getDeclaredMethods();
            for (Object object : constants) {
                Integer key = null;
                String value = null;
                for (Method method : methods) {
                    if (method.getName().contains("get")) {
                        Object temp = method.invoke(object);
                        if (temp instanceof Integer) {
                            key = Integer.valueOf(temp.toString());
                        }
                        else if (temp instanceof String) {
                            value = temp.toString();
                        }
                    }
                }
                if (key != null) {
                    result.put(key, value);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
