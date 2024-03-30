package tw.appworks.school.example.stylish.controller.v2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.appworks.school.example.stylish.repository.redis.MessagePublisher;

import java.util.Map;

import static tw.appworks.school.example.stylish.repository.redis.RedisConfiguration.COMMAND_GET_PAYMENT;

@Controller
@RequestMapping("api/2.0/report")
public class ReportControllerV2 {

    private final MessagePublisher messagePublisher;
    private final RedisMessageListenerContainer redisMessageListenerContainer;

    public static final Logger logger = LoggerFactory.getLogger(ReportControllerV2.class);

    public ReportControllerV2(MessagePublisher messagePublisher, RedisMessageListenerContainer redisMessageListenerContainer) {
        this.messagePublisher = messagePublisher;
        this.redisMessageListenerContainer = redisMessageListenerContainer;
    }

    @GetMapping("payments")
    @ResponseBody
    public ResponseEntity<?> getPayments() {
        if (!redisMessageListenerContainer.isListening()) {
            redisMessageListenerContainer.start();
        }
        messagePublisher.publish(COMMAND_GET_PAYMENT);
        return ResponseEntity.ok().body(Map.of("data", "OK"));
    }

}