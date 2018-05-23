package cn.core.utils;

import org.apache.log4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ReflectUtil class
 *
 * @author Administrator
 * @date
 */
public class ReflectUtil {

    private static final Logger logger = Logger.getLogger(ReflectUtil.class);

    public static Class<?> getSuperClassGenricType(Class<?> clazz, int index) {
        //获取clazz的直接父类
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }
        Type[] types = ((ParameterizedType) genType).getActualTypeArguments();

        if (!(types[index] instanceof Class)) {
            logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }
        return (Class<?>)types[index];
    }

    public static <T> Class<T> getSuperGenericType(Class<?> clazz)
    {
       return (Class<T>)getSuperClassGenricType(clazz,0);
    }
}
