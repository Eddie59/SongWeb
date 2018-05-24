package cn.modules.sys.controller;

import cn.core.common.controller.BaseCRUDController;
import cn.core.utils.StringUtil;
import cn.modules.sys.entity.Dict;
import cn.modules.sys.service.IDictService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DictController class
 *
 * @author Administrator
 * @date
 */
@Controller
@RequestMapping("${admin.url.prefix}/sys/dict")
public class DictController extends BaseCRUDController<Dict,String> {

    @RequestMapping("/index")
    public String index() {
        return display("index");
    }

    private IDictService service;

    @Autowired
    public void setService(IDictService service)
    {
        this.service=service;
    }

    @RequestMapping(value = "/list2", method = RequestMethod.GET)
    public void list2(Model model, HttpServletRequest request, HttpServletResponse response) {

        int rows =Integer.parseInt(request.getParameter("rows")) ;
        int page =Integer.parseInt(request.getParameter("page")) ;
        String sidx = request.getParameter("sidx");
        String sord = request.getParameter("sord");

        Page pageObj=new Page(page,rows);
        service.selectDictList(pageObj);

        String json = JSON.toJSONString(pageObj);
        StringUtil.printJson(response, json);
    }
}
