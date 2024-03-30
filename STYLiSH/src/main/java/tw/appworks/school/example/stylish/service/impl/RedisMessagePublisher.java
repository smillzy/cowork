package tw.appworks.school.example.stylish.service.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.stereotype.Component;
import tw.appworks.school.example.stylish.repository.redis.MessagePublisher;

@Component
public class RedisMessagePublisher implements MessagePublisher {

    private final StringRedisTemplate redisTemplate;

    private final PatternTopic topic;

    public RedisMessagePublisher(StringRedisTemplate redisTemplate, PatternTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(@NotNull String message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

}