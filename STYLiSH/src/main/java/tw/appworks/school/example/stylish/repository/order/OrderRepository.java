package tw.appworks.school.example.stylish.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.appworks.school.example.stylish.model.order.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
