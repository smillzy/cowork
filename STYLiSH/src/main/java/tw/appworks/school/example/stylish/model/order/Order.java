package tw.appworks.school.example.stylish.model.order;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import tw.appworks.school.example.stylish.model.JsonNodeConverter;
import tw.appworks.school.example.stylish.model.user.User;

@Entity
@Table(name = "order_table", indexes = {@Index(name = "user_id", columnList = "user_id")})
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "time", nullable = false)
    private Long time;

    @Column(name = "status", columnDefinition = "tinyint", nullable = false)
    private int status;

    @Column(name = "details", columnDefinition = "json", nullable = false)
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode details;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "total")
    private Float total;

    @Override
    public String toString() {
        return "Order [id=" + id + ", number=" + number + ", time=" + time + ", status=" + status + ", details="
                + details + ", user=" + user + ", total=" + total + "]";
    }

}
