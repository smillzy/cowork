package tw.appworks.school.example.stylish.controller.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tw.appworks.school.example.stylish.data.form.CampaignForm;
import tw.appworks.school.example.stylish.data.form.ProductForm;
import tw.appworks.school.example.stylish.service.MarketingService;
import tw.appworks.school.example.stylish.service.ProductService;
import tw.appworks.school.example.stylish.service.StorageService;

@Controller
@RequestMapping("api/1.0/admin")
public class AdminApiController {

    public static final Logger logger = LoggerFactory.getLogger(AdminApiController.class);

    private final StorageService storageService;

    private final MarketingService marketingService;

    private final ProductService productService;

    public AdminApiController(StorageService storageService, MarketingService marketingService,
                              ProductService productService) {
        this.storageService = storageService;
        this.marketingService = marketingService;
        this.productService = productService;
    }

    @PostMapping(path = "/campaign", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<?> postCampaigns(@ModelAttribute CampaignForm campaignForm,
                                           RedirectAttributes redirectAttributes) {
        try {
            marketingService.saveCampaign(campaignForm);
            storageService.store(campaignForm.getMainImage(), "campaigns");
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + campaignForm.getMainImage().getOriginalFilename() + "!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(path = "/product", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> postProduct(@ModelAttribute ProductForm productForm) {
        logger.info(productForm.toString());
        productService.saveProduct(productForm, storageService);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
