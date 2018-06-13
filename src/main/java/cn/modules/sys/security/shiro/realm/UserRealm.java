package cn.modules.sys.security.shiro.realm;

import cn.modules.sys.entity.User;
import cn.modules.sys.service.IUserService;
import cn.modules.sys.security.shiro.web.filter.authc.UsernamePasswordToken;
import cn.modules.sys.utils.UserUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * UserRealm class
 *
 * @author Administrator
 * @date
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        authorizationInfo.setRoles(UserUtils.getRoleStringList());
//        authorizationInfo.setStringPermissions(UserUtils.getPermissionsList());
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken)authenticationToken;
        String username= usernamePasswordToken.getUsername();
        User user= userService.findByUsername(username);
        if(user==null)
        {
           throw new UnknownAccountException();
        }

        if (User.STATUS_LOCKED.equals(user.getStatus())) {
            // 帐号锁定
            throw new LockedAccountException();
        }

        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(
                new Principal(user, usernamePasswordToken.isMobileLogin()), // 用户名
                user.getPassword(), // 密码
                ByteSource.Util.bytes(user.getCredentialsSalt()), // salt=username+salt
                getName() // realm name
        );

        return authenticationInfo;
    }


    public class Principal implements Serializable
    {
        private static final long serialVersionUID = 1L;

        private String id; // 编号
        private String username; // 登录名
        private String realname; // 姓名
        private boolean mobileLogin; // 是否手机登录

        public Principal(User user, boolean mobileLogin) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.realname = user.getRealname();
            this.mobileLogin = mobileLogin;
        }

        public String getSessionid()
        {
            try {
               return (String) UserUtils.getSession().getId();
            }
            catch (Exception e)
            {}
            return "";
        }

        public String getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getRealname() {
            return realname;
        }

        public boolean isMobileLogin() {
            return mobileLogin;
        }
        @Override
        public String toString() {
            return id;
        }
    }
}
