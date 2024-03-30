package tw.appworks.school.example.stylish.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tw.appworks.school.example.stylish.data.dto.TappayPrimeResponse;
import tw.appworks.school.example.stylish.data.form.OrderForm;
import tw.appworks.school.example.stylish.data.form.TappayRequestForm;
import tw.appworks.school.example.stylish.model.JsonNodeConverter;
import tw.appworks.school.example.stylish.model.order.Order;
import tw.appworks.school.example.stylish.model.order.Payment;
import tw.appworks.school.example.stylish.model.user.User;
import tw.appworks.school.example.stylish.repository.order.OrderRepository;
import tw.appworks.school.example.stylish.repository.order.PaymentRepository;
import tw.appworks.school.example.stylish.service.OrderService;
import tw.appworks.school.example.stylish.service.UserService;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${tappay.partner.key}")
    private String TAPPAY_PARTNER_KEY;

    @Value("${tappay.merchant.id}")
    private String TAPPAY_MERCHANT_ID;

    private static final int PAYMENT_STATE_UNPAID = -1;

    private static final int PAYMENT_STATE_PAID = 0;

    public static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final JsonNodeConverter converter = new JsonNodeConverter();

    private final OrderRepository orderRepository;

    private final PaymentRepository paymentRepository;

    public OrderServiceImpl(OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Nullable
    public Payment payByPrime(OrderForm orderForm, String token, UserService userService)
            throws JsonProcessingException, UserService.UserNotExistException, PaymentNotSuccessException {
        Order order = createOrderAndSave(orderForm, token, userService);
        TappayPrimeResponse response = postToTappay(order, orderForm);

        if (response.getStatus() != 0) {
            throw new PaymentNotSuccessException(response.getMsg());
        }
        return createPaymentAndSave(response, order);
    }

    private Order createOrderAndSave(OrderForm orderForm, String token, UserService userService)
            throws UserService.UserNotExistException, JsonProcessingException {
        Order order = createOrder(orderForm, userService.getUserByToken(token));
        return orderRepository.save(order);
    }

    private TappayPrimeResponse postToTappay(Order order, OrderForm orderForm) throws JsonProcessingException {
        TappayRequestForm from = TappayRequestForm.from(TAPPAY_PARTNER_KEY, TAPPAY_MERCHANT_ID, orderForm,
                order.getNumber());
        logger.debug("tap pay form: " + objectMapper.writeValueAsString(from));
        return WebClient.create("https://sandbox.tappaysdk.com/tpc/payment/pay-by-prime")
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-api-key", TAPPAY_PARTNER_KEY)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(from), TappayRequestForm.class)
                .retrieve()
                .bodyToMono(TappayPrimeResponse.class)
                .block();
    }

    private Payment createPaymentAndSave(TappayPrimeResponse tappayPrimeResponse, Order order) {
        Payment payment = new Payment();
        payment.setOrder(order);
        try {
            payment
                    .setDetails(converter.convertToEntityAttribute(objectMapper.writeValueAsString(tappayPrimeResponse)));
        } catch (JsonProcessingException ignore) {
        }
        payment = paymentRepository.save(payment);
        order.setStatus(PAYMENT_STATE_PAID);
        payment.setOrder(order);
        orderRepository.save(order);
        return payment;
    }

    private Order createOrder(OrderForm orderForm, User user) throws JsonProcessingException {
        Order order = new Order();
        order.setTime(Instant.now().toEpochMilli());
        order.setStatus(PAYMENT_STATE_UNPAID);
        order.setTotal(Float.valueOf(orderForm.getOrder().getTotal()));
        order.setDetails(converter.convertToEntityAttribute(createOrderDetailJsonString(orderForm)));
        order.setUser(user);

        Date now = Date.from(Instant.now());
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        String number = "" + c.get(Calendar.MONTH) + c.get(Calendar.DATE)
                + (c.getTimeInMillis() % (24 * 60 * 60 * 1000)) + (int) Math.floor(Math.random() * 10);
        order.setNumber(number);
        return order;
    }

    private String createOrderDetailJsonString(OrderForm orderForm) throws JsonProcessingException {
        HashMap<String, List<OrderForm.OrderItem>> map = new HashMap<>();
        map.put("list", orderForm.getOrder().getList());
        return objectMapper.writeValueAsString(map);
    }

}
