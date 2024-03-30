package tw.appworks.school.example.stylish.model.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "variant", indexes = {@Index(name = "product", columnList = "product_id")})
@Data
@NoArgsConstructor
public class Variant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @Column(name = "size", length = 15, nullable = false)
    private String size;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Override
    public String toString() {
        return "Variant [id=" + id + ", product=" + product + ", color=" + color + ", size=" + size + ", stock=" + stock
                + "]";
    }

}
