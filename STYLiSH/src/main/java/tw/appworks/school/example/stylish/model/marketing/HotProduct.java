package tw.appworks.school.example.stylish.model.marketing;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.appworks.school.example.stylish.model.product.Product;

import java.io.Serializable;

import static tw.appworks.school.example.stylish.model.marketing.HotProduct.HotProductId;

@Entity
@Table(name = "hot_product", indexes = {@Index(name = "product", columnList = "product_id")})
@Data
@NoArgsConstructor
@IdClass(HotProductId.class)
public class HotProduct implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "hot_id", nullable = false)
    private Hot hot;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NoArgsConstructor
    @Data
    public static class HotProductId implements Serializable {

        private Long hot;

        private Long product;

    }

}
