package tw.appworks.school.example.stylish.controller.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tw.appworks.school.example.stylish.data.StylishResponse;
import tw.appworks.school.example.stylish.service.MarketingService;
import tw.appworks.school.example.stylish.service.ProductService;

@RestController
@RequestMapping("api/1.0/marketing")
public class MarketingController {

    private final MarketingService marketingService;

    private final ProductService productService;

    public MarketingController(MarketingService marketingService, ProductService productService) {
        this.marketingService = marketingService;
        this.productService = productService;
    }

    @GetMapping("/campaigns")
    @ResponseBody
    public ResponseEntity<?> getCampaigns() {
        return ResponseEntity.status(HttpStatus.OK).body(new StylishResponse<>(marketingService.getCampaigns()));
    }

    @GetMapping("/hots")
    @ResponseBody
    public ResponseEntity<?> getHots() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new StylishResponse<>(marketingService.getHots(productService)));
    }

}
