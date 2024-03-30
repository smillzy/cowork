package tw.appworks.school.example.stylish.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.appworks.school.example.stylish.repository.product.IProductProjection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColorDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("code")
    private String code;

    public static ColorDto from(IProductProjection mp) {
        ColorDto dto = new ColorDto();
        dto.setName(mp.getColorName());
        dto.setCode(mp.getColorCode());
        return dto;
    }

}
