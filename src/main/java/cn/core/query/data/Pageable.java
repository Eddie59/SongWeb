package cn.core.query.data;

public interface Pageable {
    /**
     * @return 当前第几页
     */
    int getPageNumber();
    /**
     * @return 每页数据量
     */
    int getPageSize();
    /**
     * @return 当前页的数据序号
     */
    int getOffset();
    /**
     *
     * @return 当前页的排序对象
     */
    Sort getSort();
    /**
     * @return 下一页 自身Pageable接口对象
     */
    Pageable next();
    /**
     * @return 上一页 自身Pageable接口对象
     */
    Pageable previousOrFirst();

    /**
     * @return 第一页 自身Pageable接口对象
     */
    Pageable first();

    /**
     * @return 是否有上一页
     */
    boolean hasPrevious();
}
