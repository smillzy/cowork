package tw.appworks.school.example.stylish.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.appworks.school.example.stylish.model.product.Product;

import java.util.List;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
@Repository
public interface ProductsRepository extends JpaRepository<Product, Long>, ProductProjectionRepository {

    @Query(value = """
                SELECT p.id, p.category, p.title, p.description, p.price, p.texture,
                        p.wash, p.place, p.note, p.story, p.main_image as mainImage,
                        v.size, v.stock, i.image, c.code as colorCode, c.name as colorName
                FROM
                (SELECT * FROM product WHERE title LIKE %:keyword% ORDER BY id DESC LIMIT :pagingSize OFFSET :currentOffset) p
                LEFT JOIN variant v ON p.id = v.product_id
                LEFT JOIN color c ON v.color_id = c.id
                LEFT JOIN product_images i ON v.product_id = i.product_id
            """,
            nativeQuery = true)
    List<IProductProjection> searchProductByTitle(@Param("keyword") String keyword, @Param("pagingSize") int pagingSize,
                                                  @Param("currentOffset") int offset);

    @Query(value = """
                SELECT p.id, p.category, p.title, p.description, p.price, p.texture,
                        p.wash, p.place, p.note, p.story, p.main_image as mainImage,
                        v.size, v.stock, i.image, c.code as colorCode, c.name as colorName
                FROM
                (SELECT * FROM product WHERE id = :id) p
                LEFT JOIN variant v ON p.id = v.product_id
                LEFT JOIN color c ON v.color_id = c.id
                LEFT JOIN product_images i ON v.product_id = i.product_id
            """, nativeQuery = true)
    List<IProductProjection> fetchProductById(@Param("id") Long id);

}
