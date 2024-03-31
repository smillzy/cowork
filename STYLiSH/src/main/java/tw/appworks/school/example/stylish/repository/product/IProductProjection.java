package tw.appworks.school.example.stylish.repository.product;

import jakarta.persistence.Column;

public interface IProductProjection {

    Long getId();

    String getCategory();

    String getTitle();

    String getDescription();

    Integer getPrice();

    String getTexture();

    String getWash();

    String getPlace();

    String getNote();

    String getStory();

    String getMainImage();

    @Column(name = "avg_star")
    Float getSold();

    String getSize();

    Integer getStock();

    String getImage();

    String getColorCode();

    String getColorName();

}
