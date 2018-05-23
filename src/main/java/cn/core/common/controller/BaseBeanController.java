package cn.core.common.controller;

import java.io.Serializable;

import cn.core.utils.*;

/**
 * BaseBeanController class
 *
 * @author Administrator
 * @date
 */
public class BaseBeanController<Entity extends Serializable> extends BaseController {

    protected final Class<Entity> entityClass;

    protected BaseBeanController() {
        this.entityClass = ReflectUtil.getSuperGenericType(getClass());
    }
}
