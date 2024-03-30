package tw.appworks.school.example.stylish.controller.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import tw.appworks.school.example.stylish.middleware.JwtTokenFilter;
import tw.appworks.school.example.stylish.middleware.RateLimitFilter;
import tw.appworks.school.example.stylish.security.SecurityConfiguration;
import tw.appworks.school.example.stylish.service.ProductService;
import tw.appworks.school.example.stylish.utils.ProductDtoGenerator;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tw.appworks.school.example.stylish.utils.ProductDtoGenerator.PRODUCTS_JSON;
import static tw.appworks.school.example.stylish.utils.ProductDtoGenerator.PRODUCT_JSON;

@WebMvcTest(controllers = ProductsController.class, excludeFilters =
@ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {JwtTokenFilter.class, RateLimitFilter.class}),
        excludeAutoConfiguration = {SecurityConfiguration.class})
@WithMockUser(username = "admin", roles = "ADMIN")
public class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void get_products() throws Exception {
        when(productService.getProducts(anyString(), anyInt()))
                .thenReturn(ProductDtoGenerator.getMockProducts());

        mockMvc.perform(
                        get("/api/1.0/products/{category}", "all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(PRODUCTS_JSON));
    }

    @Test
    public void search_products() throws Exception {
        when(productService.searchProducts(anyString(), anyInt()))
                .thenReturn(ProductDtoGenerator.getMockProducts());

        mockMvc.perform(
                        get("/api/1.0/products/search").queryParam("keyword", "keyword"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(PRODUCTS_JSON));
    }

    @Test
    public void get_product_detail() throws Exception {
        when(productService.getProduct(anyLong()))
                .thenReturn(ProductDtoGenerator.getMockProduct());

        mockMvc.perform(
                        get("/api/1.0/products/details").queryParam("id", "1234"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(PRODUCT_JSON));
    }
}
