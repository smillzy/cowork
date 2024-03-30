package tw.appworks.school.example.stylish.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.appworks.school.example.stylish.model.order.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
