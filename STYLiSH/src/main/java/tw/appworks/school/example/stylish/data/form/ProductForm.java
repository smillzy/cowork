package tw.appworks.school.example.stylish.data.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductForm {

    private String category;

    private String title;

    private String description;

    private Integer price;

    private String texture;

    private String wash;

    private String place;

    private String note;

    private String colorIds;

    private String sizes;

    private String story;

    private MultipartFile mainImage;

    private List<MultipartFile> otherImages = new ArrayList<>(2);

    @Override
    public String toString() {
        return "ProductForm [category=" + category + ", title=" + title + ", description=" + description + ", price="
                + price + ", texture=" + texture + ", wash=" + wash + ", place=" + place + ", note=" + note
                + ", colorIds=" + colorIds + ", sizes=" + sizes + ", story=" + story + ", mainImage="
                + mainImage.getOriginalFilename() + ", otherImage1=" + otherImages.get(0).getOriginalFilename()
                + ", otherImage2=" + otherImages.get(1).getOriginalFilename() + "]";
    }

}
