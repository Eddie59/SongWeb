package cn.modules.sys.controller;

import cn.core.common.controller.BaseController;
import cn.modules.sys.utils.UserUtils;
import cn.modules.sys.security.shiro.realm.UserRealm.Principal;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LoginController class
 *
 * @author Administrator
 * @date
 */
@Controller
@RequestMapping("${admin.url.prefix}")
public class LoginController extends BaseController {

    public LoginController() {

    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView loginView(HttpServletRequest request, HttpServletResponse response, Model model) {

        Principal principal = UserUtils.getPrincipal();
        if (principal != null) {
            return new ModelAndView("redirect:/admin");
        }

        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
      /*  if (RepeatAuthenticationException.class.getName().equals(exception) || retryLimitHashedCredentialsMatcher.isShowCaptcha(useruame)) {
            model.addAttribute("showCaptcha", "1");
        } else {
            model.addAttribute("showCaptcha", "0");
        }
*/

  /*      // 强制登陆跳转
        if (ExcessiveAttemptsException.class.getName().equals(exception)
                || retryLimitHashedCredentialsMatcher.isForceLogin(useruame)) { // 重复认证异常加入验证码。
            // model.addAttribute("showCaptcha", "1");
        }
*/
        return new ModelAndView("modules/sys/login/login");
    }


    @RequestMapping(value = "/logout")
    public ModelAndView logout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            if (subject != null && subject.isAuthenticated()) {
                subject.logout();
            }
            return new ModelAndView("modules/sys/login/login");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("modules/sys/login/index");
    }

}
