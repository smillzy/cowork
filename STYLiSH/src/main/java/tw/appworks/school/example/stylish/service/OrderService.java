package tw.appworks.school.example.stylish.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import tw.appworks.school.example.stylish.data.form.OrderForm;
import tw.appworks.school.example.stylish.model.order.Payment;

public interface OrderService {

    class PaymentNotSuccessException extends Exception {

        public PaymentNotSuccessException(String message) {
            super(message);
        }

    }

    Payment payByPrime(OrderForm orderForm, String token, UserService userService)
            throws JsonProcessingException, UserService.UserNotExistException, PaymentNotSuccessException;

}
