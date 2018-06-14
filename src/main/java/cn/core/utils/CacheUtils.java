package cn.core.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * CacheUtils class
 *
 * @author Administrator
 * @date
 */
public class CacheUtils {

    public static CacheManager cacheManager = (CacheManager) SpringContextHolder.getBean("shiroEncacheManager");
    private static final String SYS_CACHE = "sysCache";

    /**
     * @param cacheName
     * @return 如果ehcache中不存在名为cacheName的cache，向ehcache中添加cache，如果存在，返回
     */
    public static Cache getCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            cacheManager.addCache(cacheName);
            cache = cacheManager.getCache(cacheName);
            cache.getCacheConfiguration().setEternal(true);
        }
        return cache;
    }

    /**
     *
     * @param cacheName cache名
     * @param key key
     * @return
     */
    public static Object get(String cacheName, String key) {
        Element element = getCache(cacheName).get(key);
        return element == null ? "" : element.getObjectValue();
    }
    public static void put(String cacheName, String key,Object value)
    {
        Element element=new Element(key,value);
        getCache(cacheName).put(element);
    }
}
