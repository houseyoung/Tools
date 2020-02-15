import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    public static <T> T getBean(Class<T> requiredType) {
        try {
            checkApplicationContext();
            return context.getBean(requiredType);
        }
        catch (Exception e) {
            e.printStackTrace();
//            log.error("[SpringContextUtil][getBean] error", e);
            return null;
        }
    }

    public static Object getBean(String name) {
        try {
            checkApplicationContext();
            return context.getBean(name);
        }
        catch (Exception e) {
            e.printStackTrace();
//            log.error("[SpringContextUtil][getBean] error", e);
            return null;
        }
    }

    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return context;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.context = applicationContext;
    }

    private static void checkApplicationContext() {
        if (context == null) {
            throw new IllegalStateException("SpringContextUtils中ApplicationContext为空");
        }
    }

}
