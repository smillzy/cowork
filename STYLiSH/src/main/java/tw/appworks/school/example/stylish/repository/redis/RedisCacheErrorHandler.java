package tw.appworks.school.example.stylish.repository.redis;

import io.lettuce.core.RedisCommandTimeoutException;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.data.redis.RedisConnectionFailureException;

public class RedisCacheErrorHandler implements CacheErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(RedisCacheErrorHandler.class);

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        handleTimeOutException(exception);
        log.warn("Unable to get from cache " + cache.getName() + " : " + exception.getMessage());
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, @Nullable Object value) {
        handleTimeOutException(exception);
        log.warn("Unable to put into cache " + cache.getName() + " : " + exception.getMessage());
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        handleTimeOutException(exception);
        log.warn("Unable to evict from cache " + cache.getName() + " : " + exception.getMessage());
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        handleTimeOutException(exception);
        log.warn("Unable to clean cache " + cache.getName() + " : " + exception.getMessage());
    }

    private void handleTimeOutException(RuntimeException exception) {
        if (exception instanceof RedisCommandTimeoutException) {
            //TODO: handle timeout
        }
        if (exception instanceof RedisConnectionFailureException) {
            //TODO: handle connection failure
        }
    }
}
