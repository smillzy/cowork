package tw.appworks.school.example.stylish.repository.product;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductProjectionRepository {

    List<ProductProjection> fetchAllProductsByCategory(@Param("category") String category,
                                                       @Param("pagingSize") int pagingSize, @Param("currentOffset") int offset);

}
