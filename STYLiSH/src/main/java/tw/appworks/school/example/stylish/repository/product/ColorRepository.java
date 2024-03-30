package tw.appworks.school.example.stylish.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.appworks.school.example.stylish.model.product.Color;

public interface ColorRepository extends JpaRepository<Color, Long> {

}
