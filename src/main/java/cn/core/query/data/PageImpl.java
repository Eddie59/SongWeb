package cn.core.query.data;

import java.io.Serializable;

/**
 * PageImpl class
 *
 * @author Administrator
 * @date
 */
public class PageImpl implements Page, Serializable {
    private static final long serialVersionUID = 8280485938848398236L;

    /**
     * 当前第几页
     */
    private final int page;
    /**
     * 总页数
     */
    private final int size;
    /**
     * 排序对象
     */
    private final Sort sort;

    public PageImpl(int page, int size, Sort sort) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero!");
        }
        if (size < 0) {
            throw new IllegalArgumentException("Page size must not be less than zero!");
        }

        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public PageImpl(int page, int size, Sort.Direction direction, String... properties) {
        this(page, size, new Sort(direction, properties));
    }

    public PageImpl(int page, int size) {
        this(page, size, null);
    }


    @Override
    public int getPageNumber() {
        return page;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public int getOffset() {
        return page * size;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Page next() {
        return new PageImpl(page + 1, size, sort);
    }

    @Override
    public Page previousOrFirst() {
        return hasPrevious() ? new PageImpl(page - 1, size, sort) : this;
    }

    @Override
    public Page first() {
        return new PageImpl(0, size, sort);
    }

    @Override
    public boolean hasPrevious() {
        return page > 0;
    }


    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PageImpl)) {
            return false;
        }
        PageImpl that = (PageImpl) obj;
        boolean pageEqual = this.page == that.page;
        boolean sizeEqual = this.size == that.size;
        boolean sortEqual = this.sort == null ? that.sort == null : this.sort.equals(that.sort);
        return pageEqual && sizeEqual && sortEqual;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + page;
        result = 31 * result + size;
        result = 31 * result + (null == sort ? 0 : sort.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return String.format("Page request [number: %d, size %d, sort: %s]", page, size,
                sort == null ? null : sort.toString());
    }
}
