package tw.appworks.school.example.stylish.repository.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductProjection implements IProductProjection {

    private Long id;

    private String category;

    private String title;

    private String description;

    private Integer price;

    private String texture;

    private String wash;

    private String place;

    private String note;

    private String story;

    private String mainImage;

    private String size;

    private Integer stock;

    private String image;

    private String colorCode;

    private String colorName;

}
