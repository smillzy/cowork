package tw.appworks.school.example.stylish.model.order;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import tw.appworks.school.example.stylish.model.JsonNodeConverter;

@Entity
@Table(name = "payment", indexes = {@Index(name = "order_id", columnList = "order_id")})
@Data
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @Column(name = "details", columnDefinition = "json", nullable = false)
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode details;

    @Override
    public String toString() {
        return "Payment [id=" + id + ", order=" + order + ", details=" + details + "]";
    }

}
