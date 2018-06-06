package cn.core.query.annotation;

/**
 * PageDefaults class
 *
 * @author Administrator
 * @date
 */
public @interface PageDefaults {
    /**
     *
     * @return 每页的数据量
     */
    int pageSize() default 10;

    /**
     *
     * @return 当前页码
     */
    int pageNumber() default 0;

    /**
     *
     * @return 默认的排序 格式为{"a=desc, a.b=desc"}
     */
    String[] sort() default {};
}
