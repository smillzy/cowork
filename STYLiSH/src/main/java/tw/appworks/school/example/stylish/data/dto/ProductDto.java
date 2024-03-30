package tw.appworks.school.example.stylish.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.appworks.school.example.stylish.repository.product.IProductProjection;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class ProductDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("category")
    private String category;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("texture")
    private String texture;

    @JsonProperty("wash")
    private String wash;

    @JsonProperty("place")
    private String place;

    @JsonProperty("note")
    private String note;

    @JsonProperty("story")
    private String story;

    @JsonProperty("main_image")
    private String mainImage;

    @JsonProperty("images")
    private Set<String> images;

    @JsonProperty("variants")
    private Set<VariantsDto> variants;

    @JsonProperty("colors")
    private Set<ColorDto> colors;

    @JsonProperty("sizes")
    private Set<String> sizes;

    public void updateAll(IProductProjection mp) {
        updateColors(mp);
        updateVariant(mp);
        updateSize(mp);
        updateImages(mp);
    }

    private void updateColors(IProductProjection mp) {
        if (colors == null)
            colors = new HashSet<>();
        colors.add(ColorDto.from(mp));
    }

    private void updateVariant(IProductProjection mp) {
        if (variants == null)
            variants = new HashSet<>();
        variants.add(VariantsDto.from(mp));
    }

    private void updateSize(IProductProjection mp) {
        if (sizes == null)
            sizes = new HashSet<>();
        sizes.add(mp.getSize());
    }

    private void updateImages(IProductProjection mp) {
        setMainImage(mp.getMainImage());
        if (images == null)
            images = new HashSet<>();
        images.add(mp.getImage());
    }

    public static ProductDto from(IProductProjection mp) {
        ProductDto ret = new ProductDto();
        ret.setId(mp.getId());
        ret.setCategory(mp.getCategory());
        ret.setTitle(mp.getTitle());
        ret.setDescription(mp.getDescription());
        ret.setPrice(mp.getPrice());
        ret.setTexture(mp.getTexture());
        ret.setWash(mp.getWash());
        ret.setPlace(mp.getPlace());
        ret.setNote(mp.getNote());
        ret.setStory(mp.getStory());
        ret.updateAll(mp);
        return ret;
    }

}
