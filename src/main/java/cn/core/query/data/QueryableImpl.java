package cn.core.query.data;

import cn.core.utils.StringUtil;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import  cn.core.query.data.Condition.Operator;

import java.util.List;

/**
 * QueryableImpl class
 *
 * @author Administrator
 * @date
 */
public class QueryableImpl implements Queryable {

    private Pageable page;
    private Sort sort;
    private Condition condition;

    // 查询参数分隔符
    public static final String separator = "||";

    //????做什么用的？从哪里赋值呢？还是说本来就没什么用？
    private boolean converted;

    public QueryableImpl() {}

    public static Queryable newQueryable() {
        return new QueryableImpl();
    }


    @Override
    public Pageable getPage() {
        return page;
    }

    @Override
    public void setPage(Pageable page) {
        this.page = page;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public void addSort(Sort sort) {

    }

    @Override
    public Condition getCondition() {
        return condition;
    }

    @Override
    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    /**
     *
     * @param property 列名
     * @return 过滤值，就是用户输入的搜索条件
     */
    @Override
    public Object getValue(String property) {
        if (this.getCondition() != null && this.getCondition().getFilterFor(property) != null) {

            return this.getCondition().getFilterFor(property).getValue();
        }
        return null;
    }

    @Override
    public void removeCondition(String property) {
        this.getCondition().remove(property);
    }

    @Override
    public boolean isConverted() {
        return converted;
    }


    /**
     *
     * @param property 列名
     * @param value 搜索值
     * @return 由参数生成一Filter对象，然后加进Condition对象里面
     */
    @Override
    public Queryable addCondition(String property, Object value) {
        Assert.notNull(property, "Condition key must not null");

        //property类似：query.code||like: sex
        String[] searchs = StringUtils.split(property, "||");
        if (searchs.length == 0) {
            //throw new QueryException("Condition key format must be : property or property_op");
        }
        //列名
        property = searchs[0];
        //操作（枚举）
        Operator operator = null;

        if (searchs.length == 1) {
            operator = Operator.eq;
        }
        else
        {
            //返回指定名称的操作枚举
            operator = Operator.fromString(searchs[1]);
        }

        boolean isValueBlank = (value == null);
        isValueBlank = isValueBlank || (value instanceof String && StringUtil.isBlank((String) value));
        isValueBlank = isValueBlank || (value instanceof List && ((List<?>) value).size() == 0);
        //is null  is not null
        boolean allowBlankValue = Operator.isAllowBlankValue(operator);
        // 过滤掉空格、空值，即不参与查询
        if (!allowBlankValue && isValueBlank) {
            return null;
        }


        if (condition == null) {
            //创建搜索条件
            Condition.Filter filter = new Condition.Filter(operator, property, value);
            condition = new Condition(filter);
        } else {
            //添加搜索条件
            condition.and(operator, property, value);
        }

        return this;

    }


}
