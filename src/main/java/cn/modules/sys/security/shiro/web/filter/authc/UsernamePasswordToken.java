package cn.modules.sys.security.shiro.web.filter.authc;


/**
 * UsernamePasswordToken class
 *
 * @author Administrator
 * @date
 */
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {
    private static final long serialVersionUID = 1L;

    private String captcha;
    private boolean mobileLogin;

    public UsernamePasswordToken(String username, char[] password, boolean rememberMe, String host, String captcha, boolean mobileLogin) {
        this.setUsername(username);
        this.setPassword(password);
        this.setHost(host);
        this.setRememberMe(rememberMe);

        captcha = captcha;
        mobileLogin = mobileLogin;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getCaptcha() {
        return captcha;
    }

    public boolean isMobileLogin() {
        return mobileLogin;
    }
}
