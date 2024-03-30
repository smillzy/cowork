package tw.appworks.school.example.stylish.repository.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

public class CustomRedisMessageListenerContainer extends RedisMessageListenerContainer {
    private static final Logger logger = LoggerFactory.getLogger(CustomRedisMessageListenerContainer.class);

    @Override
    public void start() {
        try {
            super.start();
        } catch (Exception e) {
            logger.error("start with exception: " + e.getMessage());
            stop();
        }
    }
}