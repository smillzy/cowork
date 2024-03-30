package tw.appworks.school.example.stylish.controller.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.example.stylish.data.StylishResponse;
import tw.appworks.school.example.stylish.data.dto.ProductDto;
import tw.appworks.school.example.stylish.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/1.0/products")
public class ProductsController {

    private final ProductService productService;

    @Value("${product.paging.size}")
    private int pagingSize;

    public static final Logger logger = LoggerFactory.getLogger(ProductsController.class);

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{category}")
    @ResponseBody
    public ResponseEntity<?> getProducts(@PathVariable String category,
                                         @RequestParam(name = "paging") Optional<Integer> paging) throws ServletRequestBindingException {

        if (!category.matches("^(women|men|accessories|all)$"))
            throw new ServletRequestBindingException("Wrong Request");

        List<ProductDto> ret = productService.getProducts(category, paging.orElse(0));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new StylishResponse<>(ret.stream().limit(pagingSize).toList(),
                        ret.size() > pagingSize ? paging.orElse(0) + 1 : null));
    }

    @GetMapping("/details")
    @ResponseBody
    public ResponseEntity<?> getProductDetail(@RequestParam(name = "id") long id) {
        ProductDto ret = productService.getProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(new StylishResponse<>(ret));
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<?> searchProducts(@RequestParam(name = "keyword") String keyword,
                                            @RequestParam(name = "paging") Optional<Integer> paging) {
        List<ProductDto> ret = productService.searchProducts(keyword, paging.orElse(0));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new StylishResponse<>(ret.stream().limit(pagingSize).toList(),
                        ret.size() > pagingSize ? paging.orElse(0) + 1 : null));
    }
}
