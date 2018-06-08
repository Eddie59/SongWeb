package cn.core.query.utils;

import cn.core.query.data.Condition;
import cn.core.query.data.Queryable;
import cn.core.utils.SpringContextHolder;
import cn.core.utils.convert.DateConvertEditor;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * QueryableConvertUtils class
 *
 * @author Administrator
 * @date
 */
public class QueryableConvertUtils {

    private static volatile ConversionService conversionService;

    public static void setConversionService(ConversionService conversionService) {
        QueryableConvertUtils.conversionService = conversionService;
    }

    public static ConversionService getConversionService() {
        if (conversionService == null) {
            synchronized (QueryableConvertUtils.class) {
                if (conversionService == null) {
                    try {
                        conversionService = SpringContextHolder.getBean(ConversionService.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return conversionService;
    }

    public static <T> void convertQueryValueToEntityValue(final Queryable queryable, final Class<T> entityClass) {
        if (queryable.isConverted()) {
            return;
        }

        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(entityClass);
        beanWrapper.setAutoGrowNestedPaths(true);
        beanWrapper.setConversionService(getConversionService());
        beanWrapper.registerCustomEditor(Date.class, new DateConvertEditor());


        Condition condition = queryable.getCondition();
        for (Condition.Filter filter : condition) {
            if (filter.getOperator() == Condition.Operator.custom) {
                continue;
            }
            if (filter.getOperator() == Condition.Operator.isNull || filter.getOperator() == Condition.Operator.isNotNull) {
                continue;
            }
            String property = filter.getProperty();
            Object value = filter.getValue();
            Object newValue = null;
            try {
                boolean isCollection = value instanceof Collection;
                boolean isArray = value.getClass().isArray();
                if (isCollection || isArray) {
                    List<Object> list = new ArrayList<>();
                    if (isCollection) {
                        list.addAll((Collection) value);
                    } else {
                        list = CollectionUtils.arrayToList(value);
                    }
                    int length = list.size();
                    //对于list中的每一个值，都先赋值给property属性，如果不成功，值设置为null
                    for (int i = 0; i < length; i++) {
                        list.set(i, getConvertedValue(beanWrapper, property, list.get(i)));
                    }
                    newValue = list;
                } else {
                    newValue = getConvertedValue(beanWrapper, property, value);

                }
            } catch (Exception exp) {
                newValue = null;
            }
            filter.setValue(newValue);
        }
    }

    /**
     * 使用beanWrapper对entityClass属性设置值
     * 如果设置成功，返回的值跟原来的一样，如果设置不成功，返回null
     *
     * @param beanWrapper
     * @param property
     * @param value
     * @return
     */
    private static Object getConvertedValue(final BeanWrapperImpl beanWrapper, final String property, final Object value) {

        Object newValue;
        try {
            //beanWrapper，在设置entityClass的Bean的属性时，当取到entityClass的属性是默认的类型，比如number,string等，会检索“默认的属性编辑器表”，
            //找到entityClass属性的类型对应的编辑器，把value字符串的值，转化为entityClass属性的类型的值，然后把转化后的值赋值给属性
            //如果是自定义类型，会检索"自定义属性编辑器表"，找到entityClass对应的编辑器
            //这里这样做的原因是，value值是用户输入的值，跟entityClass的Bean属性的类型可能不匹配；
            beanWrapper.setPropertyValue(property, value);
            //返回set后的新值，如果设置成功，返回的值跟原来的一样，如果设置不成功，返回null
            newValue = beanWrapper.getPropertyValue(property);
        } catch (InvalidPropertyException e) {
            newValue = null;
        } catch (Exception e) {
            newValue = null;
        }

        return newValue;
    }


}
