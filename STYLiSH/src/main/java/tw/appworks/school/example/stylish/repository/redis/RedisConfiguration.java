package tw.appworks.school.example.stylish.repository.redis;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import tw.appworks.school.example.stylish.service.impl.RedisMessageSubscriber;

import java.lang.reflect.Method;
import java.time.Duration;

@Configuration
public class RedisConfiguration implements CachingConfigurer {

    @Value("${redis.timeout.secs:1}")
    private int redisTimeoutInSecs;
    @Value("${redis.socket.timeout.secs:1}")
    private int redisSocketTimeoutInSecs;
    @Value("${redis.ttl.hours:1}")
    private int redisDataTTL;

    public static final Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);


    /**
     * pub/sub
     */
    public static final String COMMAND_GET_PAYMENT = "COMMAND_GET_PAYMENT";

    @Bean
    MessageListenerAdapter listenerAdapter(MessageSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "receiveMessage");
    }

    @Bean
    MessageSubscriber redisMessageSubscriber() {
        return new RedisMessageSubscriber();
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new CustomRedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, topic());
        container.setErrorHandler(t -> {
            logger.warn("redis message listener error: " + t.getMessage());
        });
        return container;
    }

    @Bean
    PatternTopic topic() {
        return new PatternTopic("command");
    }

    /**
     * cache
     */
    @Bean
    SocketOptions socketOptions() {
        return SocketOptions.builder()
                .connectTimeout(Duration.ofSeconds(redisSocketTimeoutInSecs))
                .build();
    }

    @Bean
    ClientOptions clientOptions(SocketOptions socketOptions) {
        return ClientOptions.builder()
                .socketOptions(socketOptions)
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .build();
    }

    @Bean
    public LettuceClientConfiguration singleClientConfig(ClientOptions clientOptions) {
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(redisTimeoutInSecs)).clientOptions(clientOptions)
                .build();
        return clientConfig;
    }

    @Bean
    public RedisConnectionFactory connectionFactory(LettuceClientConfiguration clientConfig) {
        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration();
        final LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(serverConfig, clientConfig);
        lettuceConnectionFactory.setValidateConnection(true);
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return new KeyGenerator() {
            @Override
            @NotNull
            public Object generate(@NotNull Object target, @NotNull Method method, Object @NotNull ... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }

                return sb.toString();
            }
        };
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {

        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
                .fromSerializer(new JdkSerializationRedisSerializer());
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair)
                .entryTtl(Duration.ofHours(redisDataTTL));
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
                .cacheDefaults(defaultCacheConfig)
                .build();
        redisCacheManager.setTransactionAware(true);
        return redisCacheManager;
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new RedisCacheErrorHandler();
    }
}