package cn.core.common.controller;

import cn.core.common.entity.AbstractEntity;

import java.io.Serializable;

/**
 * BaseCRUDController class
 *
 * @author Administrator
 * @date
 */
public class BaseCRUDController<Entity extends AbstractEntity<ID>,ID extends Serializable>
    extends BaseBeanController<Entity>{



}
