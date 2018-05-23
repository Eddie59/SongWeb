package cn.core.common.controller;

import cn.core.common.entity.AbstractEntity;
import cn.core.common.service.ICommonService;
import cn.modules.sys.entity.Dict;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.sql.Wrapper;
import java.util.List;

/**
 * BaseCRUDController class
 *
 * @author Administrator
 * @date
 */
public class BaseCRUDController<Entity extends AbstractEntity<ID>, ID extends Serializable>
        extends BaseBeanController<Entity> {

    protected ICommonService<Entity> commonService;

    @Autowired
    public void setCommonService(ICommonService<Entity> commonService) {
        this.commonService = commonService;
    }

    public void preList(Model model, HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * list页面
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {
        preList(model, request, response);
        return display("list");
    }

    public void preAjaxList()
    {}



}
