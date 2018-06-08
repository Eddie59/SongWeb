package cn.core.common.controller;

import java.io.Serializable;
import java.util.List;

import cn.core.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * BaseBeanController class
 *
 * @author Administrator
 * @date
 */
public class BaseBeanController<Entity extends Serializable>
        extends BaseController {

    protected final Class<Entity> entityClass;

    protected BaseBeanController() {
        this.entityClass = ReflectUtil.getSuperGenericType(getClass());
    }


    /**
     * 共享的验证规则 验证失败返回true
     *
     * @param result
     * @return
     */
    protected boolean hasError(Entity entity, BindingResult result) {
        Assert.notNull(entity);
        return result.hasErrors();
    }

    /**
     *
     * @return 从bindingResult获取错误信息
     */
    public String errorMsg(BindingResult bindingResult) {
        StringBuilder stringBuilder = new StringBuilder();
        if (bindingResult.getErrorCount() > 0) {
            List<ObjectError> objectErrors = bindingResult.getAllErrors();
            for (ObjectError error : objectErrors) {
                String msg = MessageUtils.getMessage(error.getCode(), error.getDefaultMessage(), error.getArguments());
                if (StringUtils.isNoneEmpty(msg)) {
                    stringBuilder.append(msg);
                }
            }

        }
        return stringBuilder.toString();
    }
}
