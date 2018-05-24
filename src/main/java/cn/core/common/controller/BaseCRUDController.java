package cn.core.common.controller;

import cn.core.common.entity.AbstractEntity;
import cn.core.common.service.ICommonService;
import cn.core.utils.StringUtil;
import cn.modules.sys.entity.Dict;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
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

    public Entity newModel() {
        try {
            return entityClass.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("can not instantiated model : " + this.entityClass, e);
        }
    }

    public Entity get(ID id)
    {
        if(!ObjectUtils.isEmpty(id))
        {
            return commonService.selectById(id);
        }
        return newModel();
    }

    public void preList(Model model, HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * list页面
     *
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

    public void preAjaxList() {
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public void list1(Model model, HttpServletRequest request, HttpServletResponse response) {
        EntityWrapper wrapper = new EntityWrapper();
        List<Dict> dicts = commonService.selectList(wrapper);
        String json = JSON.toJSONString(dicts);
        StringUtil.printJson(response, json);
    }



    public void preEdit(Entity entity, Model model, HttpServletRequest request, HttpServletResponse response) {

    }

    public String showCreate(Entity entity, Model model, HttpServletRequest request, HttpServletResponse response) {
        return "";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String _showCreate(Model model, HttpServletRequest request, HttpServletResponse response) {
        Entity obj = this.newModel();
        preEdit(obj, model, request, response);

        if(!model.containsAttribute("data"))
        {
            model.addAttribute("data",obj);
        }

        String createView = showCreate(newModel(), model, request, response);
        if (StringUtils.isNoneEmpty(createView)) {
            return createView;
        }

        return display("create");
    }


    public String showUpdate(Entity entity, Model model, HttpServletRequest request, HttpServletResponse response) {
        return "";
    }
    @RequestMapping(value = "{id}/update",method = RequestMethod.GET)
    public String _showUpdate(@PathVariable("id") ID id,Model model,HttpServletRequest request,HttpServletResponse response)
    {
        Entity instance=get(id);
        preEdit(instance,model,request,response);
        model.addAttribute("data",instance);

        String updateView = showUpdate(newModel(), model, request, response);
        if (!StringUtils.isEmpty(updateView)) {
            return updateView;
        }

        return display("edit");
    }



}
