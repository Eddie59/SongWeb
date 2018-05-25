package cn.core.utils;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * SpringContextHolder class
 *
 * @author Administrator
 * @date
 */
public class SpringContextHolder implements ApplicationContextAware,DisposableBean {

    private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

    private static ApplicationContext applicationContext = null;

    /**
     * 注入ApplicationContextAware
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        assertContext();
        return applicationContext;
    }

    private static void assertContext() {
        Validate.validState(applicationContext != null, "applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder");
    }


    /**
     * 从applicationContext获取bean
     *
     * @param name
     * @param <T>
     * @return
     */
    public static  <T> T getBean(String name) {
        assertContext();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从applicationContext获取bean
     *
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> beanClass) {
        assertContext();
        return applicationContext.getBean(beanClass);
    }


    /**
     * 清理applicationContext
     */
    public static void clearHolder()
    {
        if(logger.isDebugEnabled())
        {
            logger.info("清除SpringContextHolder中的ApplicationContext");
        }
        applicationContext=null;
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        clearHolder();
    }
}
