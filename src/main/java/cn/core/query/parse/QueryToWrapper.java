package cn.core.query.parse;

import cn.core.query.data.Condition;
import cn.core.query.data.Condition.Operator;
import cn.core.query.data.Condition.Filter;
import cn.core.query.data.Queryable;
import cn.core.query.data.Sort;
import com.baomidou.mybatisplus.mapper.Wrapper;


import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

/**
 * QueryToWrapper class
 *
 * @author Administrator
 * @date
 */
public class QueryToWrapper<T> {

    public void parseCondition(Wrapper<T> wrapper, Queryable queryable) {
        Condition condition = queryable.getCondition();
        if (condition != null) {
            for (Filter filter : condition) {
                String property = filter.getProperty();
                Operator operator = filter.getOperator();
                Object value = filter.getValue();
                if (value != null) {
                    switch (operator) {
                        case custom:
                            continue;
                        case isNull:
                            wrapper.isNull(property);
                            continue;
                        case isNotNull:
                            wrapper.isNotNull(property);
                            continue;
                        case between:
                            Object[] between = null;
                            if (value instanceof List) {
                                between = ((List<?>) value).toArray(new Object[((List<?>) value).size()]);
                            } else {
                                between = (Object[]) value;
                            }
                            if (between.length == 2) {
                                wrapper.between(property, between[0], between[1]);
                            }
                            continue;
                        default:
                            if (operator.name().toUpperCase(Locale.US).contains("LIKE")) {
//                                value = parseLike(filter);
                                if (operator.name().toUpperCase(Locale.US).contains("NOT")) {
                                    wrapper.notLike(property, (String) value);
                                } else {
                                    wrapper.like(property, (String) value);
                                }
                            } else {
                                try {
                                    Method method = wrapper.getClass().getMethod(operator.name(), String.class, Object.class);
                                    method.invoke(wrapper, property, value);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            continue;
                    }


                }
            }
        }
    }


    public Object parseLike(Filter filter) {
        String operator = filter.getOperator().name().toUpperCase(Locale.US);
        Object value = filter.getValue();
        if (operator.contains("PREFIX")) {
            value = "%" + value;
        } else if (operator.contains("SUFFIX")) {
            value = value + "%";
        } else {
            value = "%" + value + "%";
        }
        return value;
    }

    /**
     * 跟顺序有关
     */
    public void parseSort(Wrapper<T> wrapper, Queryable queryable) {
        Sort sort = queryable.getPage().getSort();
        if (sort != null) {
            for (Sort.Order order : sort) {
                if (order.getDirection() == Sort.Direction.ASC) {
                    wrapper.orderBy(order.getProperty(), true);
                } else if (order.getDirection() == Sort.Direction.DESC) {
                    wrapper.orderBy(order.getProperty(), false);
                }
            }
        }
    }


}
