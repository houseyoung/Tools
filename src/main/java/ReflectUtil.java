import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public class ReflectUtil {
    public static Field getFieldByFieldName(Object obj, String fieldName) {
        // **参数校验
        if (obj == null || StringUtils.isEmpty(fieldName)) {
            return null;
        }
        String internedName = fieldName.intern();

        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {

            // **加载所有field
            Field[] declaredFields = superClass.getDeclaredFields();
            if (declaredFields == null || declaredFields.length == 0) {
                continue;
            }

            // **选择name相同的field
            for (Field field : declaredFields) {
                if (field.getName() == internedName) {//filed.getName()得到的name是interned
                    return field;
                }
            }
        }
        return null;
    }

    public static Object getValueByFieldName(Object obj, String fieldName)
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = getFieldByFieldName(obj, fieldName);
        Object value = null;
        if (field != null) {
            if (field.isAccessible()) {
                value = field.get(obj);
            }
            else {
                field.setAccessible(true);
                value = field.get(obj);
                field.setAccessible(false);
            }
        }
        return value;
    }

    public static void setValueByFieldName(Object obj, String fieldName, Object value)
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field.isAccessible()) {
            field.set(obj, value);
        }
        else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }
}