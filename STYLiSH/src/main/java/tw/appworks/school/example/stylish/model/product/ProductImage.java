package tw.appworks.school.example.stylish.model.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import tw.appworks.school.example.stylish.data.form.ProductForm;

import java.util.List;

@Entity
@Table(name = "product_images", indexes = {@Index(name = "product_id", columnList = "product_id")})
@Data
@NoArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @Column(name = "image", length = 100)
    private String image;

    @Override
    public String toString() {
        return "ProductImage [id=" + id + ", product=" + product + ", image=" + image + "]";
    }

    public static List<ProductImage> from(Product product, ProductForm form) {
        return form.getOtherImages().stream().map(image1 -> {
            ProductImage pi = new ProductImage();
            pi.setProduct(product);
            pi.setImage("assets/products/" + image1.getOriginalFilename());
            return pi;
        }).toList();
    }

}
