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

    private static CacheManager cacheManager = ((CacheManager) SpringContextHolder.getBean("ehcacheManager"));
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
        return element == null ? null : element.getObjectValue();
    }

    /**
     * 写入SYS_CACHE缓存
     *
     * @param key
     * @return
     */
    public static void put(String key, Object value) {
        put(SYS_CACHE, key, value);
    }
    /**
     * 写入缓存
     *
     * @param cacheName
     * @param key
     * @param value
     */
    public static void put(String cacheName, String key, Object value) {
        Element element = new Element(key, value);
        getCache(cacheName).put(element);
    }
}
