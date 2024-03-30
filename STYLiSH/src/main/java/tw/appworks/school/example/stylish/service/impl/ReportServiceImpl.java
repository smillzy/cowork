package tw.appworks.school.example.stylish.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.appworks.school.example.stylish.data.dto.ReportPayment;
import tw.appworks.school.example.stylish.model.order.Order;
import tw.appworks.school.example.stylish.model.user.User;
import tw.appworks.school.example.stylish.repository.order.OrderRepository;
import tw.appworks.school.example.stylish.repository.user.UserRepository;
import tw.appworks.school.example.stylish.service.ReportService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    public ReportServiceImpl(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public List<ReportPayment> getPayments() {
        List<User> users = userRepository.findAll();
        List<Order> orders = orderRepository.findAll();
        List<ReportPayment> ret = new ArrayList<>(users.size());

        users.forEach(user -> {
            ReportPayment rp = new ReportPayment();
            rp.setId(user.getId());
            rp.setTotalPayment(orders.stream()
                    .filter(order -> order.getUser().getId().equals(user.getId()))
                    .map(Order::getTotal)
                    .reduce(0f, Float::sum));
            ret.add(rp);
        });
        return ret;
    }

}
