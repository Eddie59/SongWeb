package cn.modules.sys.utils;

import cn.core.utils.CacheUtils;
import cn.core.utils.ServletUtils;
import cn.core.utils.SpringContextHolder;
import cn.core.utils.StringUtil;
import cn.modules.sys.entity.Menu;
import cn.modules.sys.entity.User;
import cn.modules.sys.service.IMenuService;
import cn.modules.sys.service.IUserService;
import cn.modules.sys.security.shiro.realm.UserRealm.Principal;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.session.Session;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * UserUtils class
 * 用户信息缓存在Ehcache中，用户的菜单缓存到Session中
 *
 * @author Administrator
 * @date
 */
public class UserUtils {
    public static final String USER_CACHE = "userCache";
    public static final String USER_CACHE_ID_ = "id_";
    public static final String USER_CACHE_USER_NAME_ = "username_";
    public static final String CACHE_ROLE_LIST = "roleList";
    public static final String CACHE_MENU_LIST = "menuList";

    private static IUserService userService = SpringContextHolder.getBean(IUserService.class);
    private static IMenuService menuService = SpringContextHolder.getBean(IMenuService.class);

    /**
     * @param id userid
     * @return 如果ehcache中有值，返回，如果没有，从数据库中找到user，put到ehcache
     */
    public static User get(String id) {
        User user;
        Object obj = CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
        if (obj == null) {
            user = userService.selectById(id);
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + id, user);
            CacheUtils.put(USER_CACHE, USER_CACHE_USER_NAME_ + user.getUsername(), user);
        } else {
            user = (User) obj;
        }
        return user;
    }

    /**
     * @return 获取当前登陆用户
     */
    public static User getUser() {
        Principal principal = getPrincipal();
        if (principal != null) {
            User user = get(principal.getId());
            return user;
        }
        return new User();
    }

    /**
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
     * @return 从Session中获取当前登陆用户的menu列表，不存在则从数据库中获取，并缓存到Session中
     */
    public static List<Menu> getMenuList() {
        List<Menu> menus = (List<Menu>) getSession(CACHE_MENU_LIST);
        if (menus == null) {
            User user = getUser();
            menus = menuService.findMenuByUserId(user.getId());
            putSession(CACHE_MENU_LIST, menus);
        }
        return menus;
    }

    public static Menu getCurrentMenu() {
        String url = ServletUtils.getRequest().getServletPath();
        if (url.endsWith(".jsp")) {
            return null;
        }

        url = url.replace("/admin", "");
        url = StringUtil.trimFirstAndLastChar(url, '/');
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        Menu menu = getCurrentMenu(getMenuList(), url);
        System.out.println(url);
        return menu;
    }

    private static Menu getCurrentMenu(List<Menu> menuList, String url) {
        return menuList.stream()
                .filter((x) -> x.getUrl().trim().toLowerCase().equals(url.trim().toLowerCase()))
                .findFirst()
                .get();
    }

    /* Start Session*/

    /**
     * @return 获取当前用户的Session
     */
    public static Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }
            if (session != null) {
                return session;
            }
        } catch (InvalidSessionException e) {

        }
        return null;
    }

    public static Object getSession(String key) {
        return getSession(key, null);
    }

    public static Object getSession(String key, Object defaultValue) {
        Object obj = getSession().getAttribute(key);
        if (obj == null) {
            obj = defaultValue;
        }
        return obj;
    }

    public static void putSession(String key, Object value) {
        getSession().setAttribute(key, value);
    }
   /* End Session*/

}
