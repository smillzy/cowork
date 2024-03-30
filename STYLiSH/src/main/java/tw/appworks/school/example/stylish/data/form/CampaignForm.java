package tw.appworks.school.example.stylish.data.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CampaignForm {

    private Long productId;

    private String story;

    private MultipartFile mainImage;

}
