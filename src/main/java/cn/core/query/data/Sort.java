package cn.core.query.data;


import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.io.Serializable;

/**
 * Sort class
 *
 * @author Administrator
 * @date
 */
public class Sort implements Iterable<Sort.Order>, Serializable {
    private static final long serialVersionUID = 5737186511678863905L;
    public static final Direction DEFAULT_DIRECTION = Direction.ASC;

    private final List<Order> orders;

    public Sort(Order... orders) {
        this(Arrays.asList(orders));
    }

    public Sort(List<Order> orders) {
        if (null == orders || orders.isEmpty()) {
            throw new IllegalArgumentException("You have to provide at least one sort property to sort by!");
        }
        this.orders = orders;
    }

    public Sort(String... properties) {
        this(DEFAULT_DIRECTION, properties);
    }

    public Sort(Direction direction, String... properties) {
        this(direction, properties == null ? new ArrayList<String>() : Arrays.asList(properties));
    }

    public Sort(Direction direction, List<String> properties) {
        if (properties == null || properties.isEmpty()) {
            throw new IllegalArgumentException("You have to provide at least one property to sort by!");
        }
        this.orders = new ArrayList<>(properties.size());
        for (String property : properties) {
            this.orders.add(new Order(direction, property));
        }
    }

    /**
     *
     * @param sort
     * @return 返回一个新Sort对象，这个新对象的orders包括，当前Sort对象的orders和参数Sort对象的orders
     */
    public Sort and(Sort sort) {
        if (sort == null) {
            return this;
        }
        ArrayList<Order> these = new ArrayList<>(this.orders);
        for (Order order : sort) {
            these.add(order);
        }
        return new Sort(these);
    }

    /**
     *
     * @param property 排序的列名
     * @return 返回参数property的Order对象
     */
    public Order getOrderFor(String property) {
        for (Order order : this) {
            if (order.getProperty().equals(property)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public Iterator<Order> iterator() {
        return this.orders.iterator();
    }


    public static enum Direction {
        ASC, DESC;

        public static Direction fromString(String value) {
            return Direction.valueOf(value);
        }
    }

    public static class Order implements Serializable {
        private static final long serialVersionUID = 1522511010900108987L;
        private static final boolean DEFAULT_IGNORE_CASE = false;

        private final Direction direction;
        private final String property;
        private final boolean ignoreCase;

        private Order(Direction direction, String property, boolean ignoreCase) {
            if (!StringUtils.hasText(property)) {
                throw new IllegalArgumentException();
            }
            this.direction = direction;
            this.property = property;
            this.ignoreCase = ignoreCase;
        }

        public Order(Direction direction, String property) {
            this(direction, property, DEFAULT_IGNORE_CASE);
        }

        public Order(String property) {
            this(DEFAULT_DIRECTION, property);
        }

        public String getProperty() {
            return property;
        }

        public Direction getDirection() {
            return direction;
        }

        public boolean isAscending() {
            return this.direction.equals(Direction.ASC);
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + direction.hashCode();
            result = 31 * result + property.hashCode();
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Order)) {
                return false;
            }
            Order that = (Order) obj;
            return this.direction.equals(that.direction) && this.property.equals(that.property);
        }

        @Override
        public String toString() {
            return String.format("%s: %s", property, direction);
        }
    }
}
