package cn.core.query.data;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Condition class
 *
 * @author Administrator
 * @date
 */
public class Condition implements Iterable<Condition.Filter>, Serializable {
    private static final long serialVersionUID = 5737186511678863905L;
    private static final Operator DEFAULT_OPERATOR = Operator.custom;

    private final List<Filter> filters = new ArrayList<>();

    public Condition(Filter... filters) {
        this.filters.addAll(Arrays.asList(filters));
    }

    public Condition(List<Filter> filters) {
        this.filters.addAll(filters);
    }

    public Condition and(Operator operator, final String property, final Object value) {
        Filter filter = new Filter(operator, property, value);
        filters.add(filter);
        return this;
    }

    public Condition and(Condition condition) {
        if (condition == null) {
            return this;
        }
        //实现了Iterator接口
        for (Filter filter : condition) {
            filters.add(filter);
        }
        return this;
    }



    /**
     *
     * @param property 列名
     * @return 返回在列名上的过程条件Filter对象
     */
    public Filter getFilterFor(String property) {
        for (Filter filter : this) {
            if (filter.getProperty().equals(property)) {
                return filter;
            }
        }
        return null;
    }

    @Override
    public Iterator<Filter> iterator() {
        return this.filters.iterator();
    }

    public void remove(String property) {

        for (Filter filter : this.filters) {
            if (filter.getProperty().equals(property)) {
                this.filters.remove(filter);
            }
        }
    }


    public static enum Operator {
        eq("等于", "="),
        ne("不等于", "!="),
        gt("大于", ">"),
        ge("大于等于", ">="),
        lt("小于", "<"),
        le("小于等于", "<="),
        isNull("空", "is null"),
        isNotNull("非空", "is not null"),
        in("包含", "in"),
        notIn("不包含", "not in"),
        between("对应SQL的between子句", "between"),
        prefixLike("前缀模糊匹配", "like"),
        prefixNotLike("前缀模糊不匹配", "not like"),
        suffixLike("后缀模糊匹配", "like"),
        suffixNotLike("后缀模糊不匹配", "not like"),
        like("模糊匹配", "like"),
        notLike("不匹配", "not like"),
        custom("自定义默认的", null);

        private final String info;
        private final String symbol;

        Operator(final String info, String symbol) {
            this.info = info;
            this.symbol = symbol;
        }

        public String getInfo() {
            return info;
        }

        public String getSymbol() {
            return symbol;
        }

        /**
         * @param value
         * @return 返回指定名称的枚举值
         */
        public static Operator fromString(String value) {
            return Operator.valueOf(value);
        }

        public static Operator fromStringOrNull(String value) {
            return fromString(value);
        }

        /**
         * @return 所有枚举变量组成的数组
         */
        public static String toStringAllOperator() {
            return Arrays.toString(Operator.values());
        }

        public static boolean isAllowBlankValue(final Operator operator) {
            return operator == Operator.isNotNull || operator == Operator.isNull;

        }
    }

    public static class Filter implements Serializable {
        private static final long serialVersionUID = 1522511010900108987L;
        private static final boolean DEFAULT_IGNORE_CASE = false;

        private final String property;
        private final Operator operator;
        private Object value;

        private Filter(Operator operator, String property, Object value, boolean ignoreCase) {
            if (!org.springframework.util.StringUtils.hasText(property)) {
                throw new IllegalArgumentException();
            }
            this.operator = operator;
            this.property = property;
            this.value = value;
        }

        public Filter(Operator operator, String property, Object value) {
            this(operator, property, value, DEFAULT_IGNORE_CASE);
        }

        public Filter(String property, Object value) {
            this(DEFAULT_OPERATOR, property, value);
        }

        public Operator getOperator() {
            return operator;
        }

        public String getProperty() {
            return property;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        /**
         * 是否是一元过滤 如is null is not null
         *
         * @return
         */
        public boolean isUnaryFilter() {
            String operatorStr = getOperator().getSymbol();
            return !StringUtils.isNoneEmpty(operatorStr) && operatorStr.startsWith("is");
        }


        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + operator.hashCode();
            result = 31 * result + property.hashCode();
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Filter)) {
                return false;
            }
            Filter that = (Filter) obj;
            return this.operator.equals(that.operator) && this.property.equals(that.property);
        }

        @Override
        public String toString() {
            return String.format("%s: %s", property, operator);
        }
    }


}
