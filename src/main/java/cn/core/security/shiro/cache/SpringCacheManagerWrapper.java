package cn.core.security.shiro.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * SpringCacheManagerWrapper class
 *
 * @author Administrator
 * @date
 */
public class SpringCacheManagerWrapper implements CacheManager {

    private CacheManager cacheManager;

    /**
     * @param cacheManager 注入SpringCacheManager
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Nullable
    @Override
    public Cache getCache(String name) {
        Cache cache = cacheManager.getCache(name);
        //SpringCacheWrapper实现了Cache接口
        return new SpringCacheWrapper(cache);
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheManager.getCacheNames();
    }


    /**
     * 实现Cache接口
     */
    class SpringCacheWrapper implements Cache {
        private Cache cache;

        public SpringCacheWrapper(Cache cache) {
            this.cache = cache;
        }

        @Override
        public String getName() {
            return cache.getName();
        }

        @Override
        public Object getNativeCache() {
            return cache.getNativeCache();
        }

        @Nullable
        @Override
        public ValueWrapper get(Object key) {
            return cache.get(key);
        }

        @Nullable
        @Override
        public <T> T get(Object key, @Nullable Class<T> type) {
            return cache.get(key, type);
        }

        @Nullable
        @Override
        public <T> T get(Object key, Callable<T> valueLoader) {
            return cache.get(key, valueLoader);
        }

        @Override
        public void put(Object key, @Nullable Object value) {
            cache.put(key, value);
        }

        @Nullable
        @Override
        public ValueWrapper putIfAbsent(Object key, @Nullable Object value) {
            return null;
        }

        @Override
        public void evict(Object key) {
            cache.evict(key);
        }

        @Override
        public void clear() {
            cache.clear();
        }
    }
}
