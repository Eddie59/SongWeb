package cn.core.query.annotation;

import java.lang.annotation.*;

/**
 * QueryableDefaults class
 *
 * @author Administrator
 * @date
 */
@Target({ ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryableDefaults {

    /**
     * 默认查询参数字符串
     *
     * @return
     */
    String[] value() default {};

    /**
     * 是否合并默认的与自定义的
     *
     * @return
     */
    boolean merge() default false;

    /**
     * 是否需要分页
     *
     * @return
     */
    boolean needPage() default true;

    /**
     * 是否需要排序
     *
     * @return
     */
    boolean needSort() default true;
}
