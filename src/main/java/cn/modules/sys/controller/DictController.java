package cn.modules.sys.controller;

import cn.core.common.controller.BaseCRUDController;
import cn.core.query.data.Queryable;
import cn.core.utils.StringUtil;
import cn.modules.sys.entity.Dict;
import cn.modules.sys.entity.DictGroup;
import cn.modules.sys.service.IDictGroupService;
import cn.modules.sys.service.IDictService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * DictController class
 *
 * @author Administrator
 * @date
 */
@Controller
@RequestMapping("${admin.url.prefix}/sys/dict")
public class DictController extends BaseCRUDController<Dict, String> {

    @RequestMapping("/index")
    public String index() {
        return display("index");
    }

    private IDictService service;
    private IDictGroupService dictGroupService;

    @Autowired
    public void setService(IDictService service) {
        this.service = service;
    }

    @Autowired
    public void setService(IDictGroupService service) {
        this.dictGroupService = service;
    }

    @RequestMapping(value = "/dictcreate", method = RequestMethod.GET)
    public String dictCreate(Model model, HttpServletRequest request, HttpServletResponse response) {
        Dict obj = new Dict();
        if (!model.containsAttribute("data")) {
            model.addAttribute("data", obj);
        }

        //DictGroup
        EntityWrapper wapper = new EntityWrapper();
        List<DictGroup> dictGroups = dictGroupService.selectList(wapper);
        request.getSession().setAttribute("dictGroups", dictGroups);

        String createView = showCreate(obj, model, request, response);
        if (!StringUtils.isEmpty(createView)) {
            return display(createView);
        }
        return display("edit");
    }

    @RequestMapping(value = "{id}/dictupdate", method = RequestMethod.GET)
    public String dictUpdate(@PathVariable("id") String id, Model model, HttpServletRequest request, HttpServletResponse response) {
        Dict instance = get(id);
        preEdit(instance, model, request, response);
        model.addAttribute("data", instance);

        //DictGroup
        EntityWrapper wapper = new EntityWrapper();
        List<DictGroup> dictGroups = dictGroupService.selectList(wapper);
        request.getSession().setAttribute("dictGroups", dictGroups);

        String updateView = showUpdate(newModel(), model, request, response);
        if (!StringUtils.isEmpty(updateView)) {
            return updateView;
        }

        return display("edit");
    }


    @RequestMapping(value = "/list2", method = RequestMethod.GET)
    public void list2(Queryable queryable, Model model, HttpServletRequest request, HttpServletResponse response) {
        int rows = Integer.parseInt(request.getParameter("page.size"));
        int page = Integer.parseInt(request.getParameter("page.pn"));
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        boolean isAsc = true;
        if (!StringUtils.equalsIgnoreCase(order, "asc")) {
            isAsc = false;
        }

        Page pageObj = new Page(page, rows, sort, isAsc);
        service.selectDictList(pageObj);

        String json = JSON.toJSONString(pageObj);
        StringUtil.printJson(response, json);
    }
}
