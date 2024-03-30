package tw.appworks.school.example.stylish.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.appworks.school.example.stylish.model.marketing.HotProduct;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MarketHotsDto {

    @JsonProperty("title")
    private String title;

    @JsonProperty("products")
    private List<ProductDto> products;

    public static MarketHotsDto from(HotProduct hotProduct) {
        MarketHotsDto dto = new MarketHotsDto();
        dto.setTitle(hotProduct.getHot().getTitle());
        dto.setProducts(new ArrayList<>());
        return dto;
    }

}
