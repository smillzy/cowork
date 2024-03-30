package tw.appworks.school.example.stylish.controller;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.appworks.school.example.stylish.data.dto.ProductDto;
import tw.appworks.school.example.stylish.data.form.ProductForm;
import tw.appworks.school.example.stylish.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin")
public class AdminPageController {

    private final ProductService productService;

    public static final Logger logger = LoggerFactory.getLogger(AdminPageController.class);

    public AdminPageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("campaign")
    public String getCampaignPageSSR(@NotNull Model model) {
        return "admin/campaign";
    }

    @GetMapping("checkout")
    public String getCheckoutPageSSR(@NotNull Model model) {
        return "admin/checkout";
    }

    @GetMapping("hot")
    public String getHotSSR(@NotNull Model model) {
        return "admin/hot";
    }

    @GetMapping("product")
    /* demo for service side render */
    public String getProductPageSSR(@NotNull Model model) {
        List<ProductDto> ret = productService.getProducts("all", 100, 0);
        ret.forEach(p -> {
            p.setMainImage("../" + p.getMainImage());
            p.setImages(p.getImages().stream().map(s -> "../" + s).collect(Collectors.toSet()));
            p.getVariants().forEach(v -> v.setColorCode("#" + v.getColorCode()));
        });
        model.addAttribute("productForm", new ProductForm());
        model.addAttribute("currentProducts", ret);
        return "admin/product";
    }

}
