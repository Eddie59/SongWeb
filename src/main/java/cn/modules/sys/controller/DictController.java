package cn.modules.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * DictController class
 *
 * @author Administrator
 * @date
 */
@Controller
@RequestMapping("/dict")
public class DictController {

    @RequestMapping("/index")
    public String index()
    {
        return "modules/sys/dict/index";
    }
}
