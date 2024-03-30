package tw.appworks.school.example.stylish.service.impl;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tw.appworks.school.example.stylish.repository.redis.MessageSubscriber;

@NoArgsConstructor
public class RedisMessageSubscriber implements MessageSubscriber {

    public static final Logger logger = LoggerFactory.getLogger(RedisMessageSubscriber.class);

    @Override
    public void receiveMessage(@NotNull String message) {
        logger.info("received: " + message);
    }
}