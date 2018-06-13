package cn.modules.sys.security.shiro.web.filter.authc;

import cn.core.model.AjaxJson;
import cn.core.utils.IpUtils;
import cn.modules.sys.security.shiro.exception.RepeatAuthenticationException;
import cn.modules.sys.utils.UserUtils;
import cn.core.utils.StringUtil;
import cn.modules.sys.security.shiro.realm.UserRealm.Principal;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * FormAuthenticationFilter class
 *
 * @author Administrator
 * @date
 */
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

    public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
    private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    public static final String DEFAULT_MESSAGE_ERROR_PARAM = "error";
    public static final String DEFAULT_MESSAGE_SUCCESS_PARAM = "success";
    public static final String DEFAULT_MESSAGE_NORMAL_PARAM = "normal";
    private String messageErrorParam = DEFAULT_MESSAGE_ERROR_PARAM;
    private String messageSuccessParam = DEFAULT_MESSAGE_SUCCESS_PARAM;
    private String messageNormallParam = DEFAULT_MESSAGE_NORMAL_PARAM;

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        if (password == null) {
            password = "";
        }
        boolean rememberMe = isRememberMe(request);
        String host = IpUtils.getIpAddr((HttpServletRequest) request);

        String captcha = getCaptcha(request);
        boolean mobile = isMobileLogin(request);
        return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, mobile);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        super.onLoginFailure(token, e, request, response);

        UsernamePasswordToken authcToken = (UsernamePasswordToken) token;

        if (!authcToken.isMobileLogin()) {
            //获取异常类的
            String className = e.getClass().getName();
            String message = "";
            if (IncorrectCredentialsException.class.getName().equals(className)
                    || UnknownAccountException.class.getName().equals(className)) {
                message = "用户或密码错误, 请重试.";
            } else if (RepeatAuthenticationException.class.getName().equals(className)) {
                message = "请勿重复提交认证.";
            } else if (ExcessiveAttemptsException.class.getName().equals(className)) {
                message = "请勿重复提交认证,请半小时之后登录";
            } else if (StringUtils.isNoneBlank(e.getMessage())) {
                message = e.getMessage();
            } else {
                message = "系统出现点问题，请稍后再试！";
                e.printStackTrace(); // 输出到控制台
            }
            request.setAttribute(getFailureKeyAttribute(), className);
            request.setAttribute(getMessageErrorParam(), message);
            return true;
        } else {
            // 登录失败返回false
            AjaxJson ajaxJson = new AjaxJson();
            ajaxJson.setRet(AjaxJson.RET_FAIL);
            ajaxJson.setMsg("登录失败!");
            ajaxJson.put("mobileLogin", authcToken.isMobileLogin());
            ajaxJson.put("JSESSIONID", UserUtils.getSession().getId());
            StringUtil.printJson((HttpServletResponse) response, ajaxJson);
            return false;
        }
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        return super.onLoginSuccess(token, subject, request, response);
    }

    /**
     * 登录成功之后调用，重定向
     */
    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        Principal p = UserUtils.getPrincipal();
//        UserUtils.clearCache();
        if (p != null && !p.isMobileLogin()) {
            WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
        } else {
            AjaxJson ajaxJson = new AjaxJson();
            ajaxJson.setRet(AjaxJson.RET_SUCCESS);
            ajaxJson.setMsg("登录成功!");
            ajaxJson.put("username", p.getUsername());
            ajaxJson.put("realname", p.getRealname());
            ajaxJson.put("mobileLogin", p.isMobileLogin());
            ajaxJson.put("JSESSIONID", p.getSessionid());
            StringUtil.printJson((HttpServletResponse) response, ajaxJson);
        }
    }

    @Override
    public String getSuccessUrl() {
        return super.getSuccessUrl();
    }


    protected boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, getMobileLoginParam());
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }


    public String getMobileLoginParam() {
        return mobileLoginParam;
    }

    public void setMobileLoginParam(String mobileLoginParam) {
        this.mobileLoginParam = mobileLoginParam;
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }


    public String getMessageErrorParam() {
        return messageErrorParam;
    }

    public String getMessageNormallParam() {
        return messageNormallParam;
    }

    public String getMessageSuccessParam() {
        return messageSuccessParam;
    }

    public void setMessageErrorParam(String messageErrorParam) {
        this.messageErrorParam = messageErrorParam;
    }

    public void setMessageNormallParam(String messageNormallParam) {
        this.messageNormallParam = messageNormallParam;
    }

    public void setMessageSuccessParam(String messageSuccessParam) {
        this.messageSuccessParam = messageSuccessParam;
    }

}
