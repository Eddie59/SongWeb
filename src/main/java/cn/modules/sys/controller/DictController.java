package cn.modules.sys.controller;

import cn.core.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * DictController class
 *
 * @author Administrator
 * @date
 */
@Controller
@RequestMapping("${admin.url.prefix}/sys/dict")
public class DictController extends BaseController {

    @RequestMapping("/index")
    public String index() {
        return display("index");
    }

}
