package tw.appworks.school.example.stylish.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import tw.appworks.school.example.stylish.model.campaign.Campaign;

import java.io.Serializable;

@Data
@RedisHash
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDto implements Serializable {

    @Id
    @JsonProperty("id")
    private Long id;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("picture")
    private String picture;

    @JsonProperty("story")
    private String story;

    public static CampaignDto from(Campaign campaign) {
        CampaignDto dto = new CampaignDto();
        dto.setId(campaign.getId());
        dto.setProductId(campaign.getProduct().getId());
        dto.setPicture(campaign.getPicture());
        dto.setStory(campaign.getStory());
        return dto;
    }

}
