package cn.modules.sys.security.shiro.web.filter.authc.credential;

import cn.core.security.shiro.cache.SpringCacheManagerWrapper;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import cn.modules.sys.security.shiro.web.filter.authc.UsernamePasswordToken;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RetryLimitHashedCredentialsMatcher class
 *
 * @author Administrator
 * @date
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private Cache<String, AtomicInteger> passwordRetryCache;
    private int maxRetryCount = 5;

    public RetryLimitHashedCredentialsMatcher(CacheManager manager) {
        passwordRetryCache = manager.getCache("passwordRetryCache");
    }


    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
        String username = authcToken.getUsername();

        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }

        //重试次数大于5次时，抛出异常
        retryCount.incrementAndGet();
        if (retryCount.get() > maxRetryCount) {
            throw new ExcessiveAttemptsException();
        }

        //如果登陆成功，清除缓存
        boolean match = super.doCredentialsMatch(token, info);
        if (match) {
            passwordRetryCache.remove(username);
        }
        return match;
    }






    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

}
