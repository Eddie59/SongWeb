package cn.modules.sys.controller;

import cn.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * IndexController class
 *
 * @author Administrator
 * @date
 */
@Controller
@RequestMapping("${admin.url.prefix}")
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request, HttpServletResponse response)
    {
        model.addAttribute("menu", UserUtils.getMenuList());
        return "modules/sys/home/index";
    }

}
