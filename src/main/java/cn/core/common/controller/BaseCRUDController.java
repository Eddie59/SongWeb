package cn.core.common.controller;

import cn.core.common.entity.AbstractEntity;
import cn.core.common.service.ICommonService;
import cn.core.model.AjaxJson;
import cn.core.query.data.Queryable;
import cn.core.utils.StringUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    public Entity get(ID id) {
        if (!ObjectUtils.isEmpty(id)) {
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
    public void ajaxList( Model model, HttpServletRequest request, HttpServletResponse response) {
        EntityWrapper ew = new EntityWrapper();

        List<Entity> data = commonService.selectList(ew);
        String json = JSON.toJSONString(data);
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

        if (!model.containsAttribute("data")) {
            model.addAttribute("data", obj);
        }

        String createView = showCreate(newModel(), model, request, response);
        if (!StringUtils.isEmpty(createView)) {
            return display(createView);
        }
        return display("edit");
    }



    public String showUpdate(Entity entity, Model model, HttpServletRequest request, HttpServletResponse response) {
        return "";
    }

    @RequestMapping(value = "{id}/update", method = RequestMethod.GET)
    public String _showUpdate(@PathVariable("id") ID id, Model model, HttpServletRequest request, HttpServletResponse response) {
        Entity instance = get(id);
        preEdit(instance, model, request, response);
        model.addAttribute("data", instance);

        String updateView = showUpdate(newModel(), model, request, response);
        if (!StringUtils.isEmpty(updateView)) {
            return updateView;
        }

        return display("edit");
    }

    /**
     *
     * 添加
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson create(Model model, @Validated @ModelAttribute("data") Entity entity, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        return doSave(entity,request,response,result);
    }

    /**
     *
     * 更新
     */
    @RequestMapping(value = "{id}/update",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson update(Model model,@Validated @RequestAttribute("data") Entity entity,BindingResult result,HttpServletRequest request,HttpServletResponse response)
    {
        return doSave(entity,request,response,result);
    }

    public void preSave(Entity entity, HttpServletRequest request, HttpServletResponse response) {
    }

    /**
     * 添加、更新
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxJson doSave(Entity entity, HttpServletRequest request, HttpServletResponse response, BindingResult result) {
        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.success("保存成功");
        if (hasError(entity, result)) {
            String errorMsg = errorMsg(result);
            if (StringUtils.isNoneEmpty(errorMsg)) {
                ajaxJson.fail(errorMsg);
            } else {
                ajaxJson.fail("保存失败");
            }
            return ajaxJson;
        }

        try {
            preSave(entity, request, response);
            if (ObjectUtils.isEmpty(entity.getId())) {
                commonService.insert(entity);
            } else {
                commonService.insertOrUpdate(entity);
            }
            afterSave(entity, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.fail("保存失败!<br />原因:" + e.getMessage());
        }
        return ajaxJson;
    }

    public void afterSave(Entity entity, HttpServletRequest request, HttpServletResponse response) {
    }




    @RequestMapping(value = "{id}/delete",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson delete(@PathVariable ID id)
    {
        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.success("删除成功");
        try {
            commonService.deleteById(id);
        }
        catch (Exception e){
            e.printStackTrace();
            ajaxJson.fail("删除失败");
        }
        return ajaxJson;
    }


    @RequestMapping(value = "batch/delete",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson batchDelete(@RequestParam(value = "ids",required = false) ID[] ids ) {
        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.success("删除成功");
        try {
            List<ID> idList= java.util.Arrays.asList(ids);
            commonService.deleteBatchIds(idList);
        } catch (Exception exp)
        {
            exp.printStackTrace();
            ajaxJson.fail("删除失败");
        }
        return ajaxJson;
    }


}
