package cn.core.common.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * AbstractEntity class
 *
 * @author Administrator
 * @date
 */
public abstract class AbstractEntity<ID> extends BaseEntity {

    public abstract ID getId();

    public abstract void setId(ID id);

    public boolean isNew() {
        return null == getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        AbstractEntity<ID> that = (AbstractEntity<ID>) obj;
        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
        return hashCode;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
