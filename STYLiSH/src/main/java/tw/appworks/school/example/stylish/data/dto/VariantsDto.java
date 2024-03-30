package tw.appworks.school.example.stylish.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.appworks.school.example.stylish.repository.product.IProductProjection;

@Data
@NoArgsConstructor
public class VariantsDto {

    @JsonProperty("color_code")
    private String colorCode;

    @JsonProperty("size")
    private String size;

    @JsonProperty("stock")
    private Integer stock;

    public static VariantsDto from(IProductProjection mp) {
        VariantsDto dto = new VariantsDto();
        dto.setColorCode(mp.getColorCode());
        dto.setSize(mp.getSize());
        dto.setStock(mp.getStock());
        return dto;
    }

}
