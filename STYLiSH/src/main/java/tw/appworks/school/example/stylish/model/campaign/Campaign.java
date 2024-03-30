package tw.appworks.school.example.stylish.model.campaign;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.appworks.school.example.stylish.model.product.Product;

@Entity
@Table(name = "campaign", indexes = {@Index(name = "product", columnList = "product_id")})
@Data
@NoArgsConstructor
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "picture", nullable = false)
    private String picture;

    @Column(name = "story", nullable = false)
    private String story;

    @Override
    public String toString() {
        return "Campaign [id=" + id + ", product=" + product + ", picture=" + picture + ", story=" + story + "]";
    }

}
