package tw.appworks.school.example.stylish.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    @JsonProperty("user_id")
    private Long userId;

    @Column(name = "star", nullable = false)
    private Integer star;

    @Column(name = "product_id", nullable = false)
    @JsonProperty("product_id")
    private Long productId;


    @Override
    public String toString() {
        return "Comment [id=" + id + ", userId=" + userId + ", productId=" + productId + ", star=" + star + "]";
    }
}

