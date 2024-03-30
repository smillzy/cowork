package tw.appworks.school.example.stylish.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.appworks.school.example.stylish.model.product.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
