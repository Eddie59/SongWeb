package cn.modules.sys.utils;

import org.apache.shiro.SecurityUtils;

import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;

import cn.modules.sys.security.shiro.realm.UserRealm.Principal;
import org.apache.shiro.session.Session;


/**
 * UserUtils class
 *
 * @author Administrator
 * @date
 */
public class UserUtils {


    /**
     *
     * @return 获取当前登陆用户的Principal
     */
    public static Principal getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal) subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
        } catch (Exception exp) {

        }
        return null;
    }

    /**
     *
     * @return 获取当前用户的Session
     */
    public static Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session==null) {
                session=subject.getSession();
            }
            if(session!=null)
            {
                return session;
            }
        } catch (InvalidSessionException e) {

        }
        return null;
    }
}
