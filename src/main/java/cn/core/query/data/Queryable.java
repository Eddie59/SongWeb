package cn.core.query.data;

/**
 * Queryable class
 *
 * @author Administrator
 * @date
 */
public interface Queryable {
    /**
     * 分页
     */
    Page getPage();

    void setPage(Page page);


    /**
     * 排序
     */
    Sort getSort();

    void addSort(Sort sort);

    /**
     * 过滤
     */
    Condition getCondition();

    void setCondition(Condition condition);

    /**
     * @param property 过滤的列名
     * @return 获取搜索的value，就是用户输入的值
     */
    Object getValue(String property);

    /**
     * @param property 列名
     * @param value 搜索值
     * @return 添加完返回Queryable
     */
    Queryable addCondition(final String property, final Object value);

    void removeCondition(String property);


    boolean isConverted();
}
