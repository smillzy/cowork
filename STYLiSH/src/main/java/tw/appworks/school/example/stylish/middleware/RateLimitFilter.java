package tw.appworks.school.example.stylish.middleware;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    @Value("${rate.limit.window}")
    private int timeBase;

    @Value("${rate.limit.count}")
    private int rps;

    @SuppressWarnings("rawtypes")
    private final RedisTemplate redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RateLimitFilter.class);

    @SuppressWarnings("rawtypes")
    public RateLimitFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @SuppressWarnings({"DataFlowIssue", "unchecked"})
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String key = request.getRemoteAddr();
            long count = redisTemplate.opsForValue().increment(key, 1);
            logger.debug("request: " + request.getRequestURI());
            logger.debug("key: " + key + ", count: " + count);
            if (count == 1) {
                redisTemplate.expire(key, timeBase, TimeUnit.SECONDS);
            }

            if (count <= rps) {
                filterChain.doFilter(request, response);
            } else {
                logger.warn("over the rate limit");
                throw new ServletException("over the rate limit");
            }
        } catch (RedisConnectionFailureException e) {
//            logger.warn("RedisConnectionFailureException");
            filterChain.doFilter(request, response);
        }
    }
}
