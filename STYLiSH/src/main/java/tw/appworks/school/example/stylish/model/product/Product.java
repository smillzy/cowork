package tw.appworks.school.example.stylish.model.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.appworks.school.example.stylish.data.form.ProductForm;

@Entity
@Table(name = "product",
        indexes = {@Index(name = "category", columnList = "category"), @Index(name = "title", columnList = "title"),})
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint unsigned", nullable = false)
    private Long id;

    @Column(name = "category", length = 127, nullable = false)
    private String category;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", columnDefinition = "int unsigned", nullable = false)
    private long price;

    @Column(name = "wash", length = 127, nullable = false)
    private String wash;

    @Column(name = "texture", length = 127, nullable = false)
    private String texture;

    @Column(name = "place", length = 127, nullable = false)
    private String place;

    @Column(name = "note", length = 127, nullable = false)
    private String note;

    @Column(name = "story", columnDefinition = "text", nullable = false)
    private String story;

    @Column(name = "main_image")
    private String mainImage;

    // toString method for debugging or logging purposes
    @Override
    public String toString() {
        return "Product [id=" + id + ", category=" + category + ", title=" + title + ", description=" + description
                + ", price=" + price + ", texture=" + texture + ", wash=" + wash + ", place=" + place + ", note=" + note
                + ", story=" + story + ", mainImage=" + mainImage + "]";
    }

    public static Product from(ProductForm productForm) {
        Product p = new Product();
        p.setCategory(productForm.getCategory());
        p.setTitle(productForm.getTitle());
        p.setDescription(productForm.getDescription());
        p.setPrice(productForm.getPrice());
        p.setTexture(productForm.getTexture());
        p.setWash(productForm.getWash());
        p.setPlace(productForm.getPlace());
        p.setNote(productForm.getNote());
        p.setStory(productForm.getStory());
        p.setMainImage("assets/products/" + productForm.getMainImage().getOriginalFilename());
        return p;
    }

}
