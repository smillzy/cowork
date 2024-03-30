package tw.appworks.school.example.stylish.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import tw.appworks.school.example.stylish.StylishApplication;
import tw.appworks.school.example.stylish.data.dto.ColorDto;
import tw.appworks.school.example.stylish.data.dto.ProductDto;
import tw.appworks.school.example.stylish.data.dto.VariantsDto;
import tw.appworks.school.example.stylish.repository.product.IProductProjection;
import tw.appworks.school.example.stylish.repository.product.ProductProjection;
import tw.appworks.school.example.stylish.repository.product.ProductsRepository;

import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StylishApplication.class, properties = {
        "spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false",
        "spring.jpa.hibernate.ddl-auto=none"
})
@ActiveProfiles("test")
public class ProductServiceTest {


    @Value("${stylish.domain}")
    private String domain;

    @Value("${stylish.port}")
    private int port;

    @Value("${stylish.scheme}")
    private String scheme;

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductsRepository productsRepository;

    private List<IProductProjection> fakeIProductProjections;
    private List<ProductProjection> fakeProductProjections;
    private ProductProjection p1, p2, p3, p4;

    private String prefix;

    @Before
    public void init() {
        p1 = new ProductProjection();
        p1.setId(1234L);
        p1.setImage("image 1");
        p1.setSize("M");
        p1.setStock(123);
        p1.setColorCode("color code 1");
        p1.setColorName("color name 1");

        p2 = new ProductProjection();
        p2.setId(2234L);
        p2.setImage("image 2");
        p2.setSize("L");
        p2.setStock(223);
        p2.setColorCode("color code 2");
        p2.setColorName("color name 2");

        p3 = new ProductProjection();
        p3.setId(3234L);
        p3.setImage("image 3");
        p3.setSize("XL");
        p3.setStock(323);
        p3.setColorCode("color code 3");
        p3.setColorName("color name 3");

        p4 = new ProductProjection();
        p4.setId(3234L);
        p4.setImage("image 4");
        p4.setSize("L");
        p4.setStock(1234);
        p4.setColorCode("color code 4");
        p4.setColorName("color name 4");

        fakeIProductProjections = List.of(p1, p2, p3, p4);
        fakeProductProjections = fakeIProductProjections.stream()
                .map(productProjection -> (ProductProjection) productProjection).toList();

        System.out.println("scheme: " + scheme);
        System.out.println("domain: " + domain);

        StringBuilder builder = new StringBuilder(scheme + "://" + domain);
        if (port != 80 && port != 443) {
            builder.append(":").append(port);
        }
        builder.append("/");
        this.prefix = builder.toString();
    }

    @Test
    public void get_products_with_paging_size() {

        when(productsRepository.fetchAllProductsByCategory(anyString(), anyInt(), anyInt())).thenReturn(fakeProductProjections);
        List<ProductDto> productDtoList = productService.getProducts("", 0, 0);
        assertProductDto(productDtoList);
    }

    @Test
    public void get_products_without_paging_size() {

        when(productsRepository.fetchAllProductsByCategory(anyString(), anyInt(), anyInt())).thenReturn(fakeProductProjections);
        List<ProductDto> productDtoList = productService.getProducts("", 0);
        assertProductDto(productDtoList);
    }

    @Test
    public void search_product() {

        when(productsRepository.searchProductByTitle(anyString(), anyInt(), anyInt())).thenReturn(fakeIProductProjections);
        List<ProductDto> productDtoList = productService.searchProducts("", 0);
        assertProductDto(productDtoList);
    }

    @Test
    public void get_product_with_id() {

        when(productsRepository.fetchProductById(anyLong())).thenReturn(fakeIProductProjections);
        ProductDto productDto = productService.getProduct(0L);
        assertNotNull(productDto);
        assertEquals(1234L, productDto.getId().longValue());
        assertEquals(1, productDto.getImages().size());
        assertEquals(this.prefix + "image 1", productDto.getImages().stream().toList().get(0));

        assertEquals(1, productDto.getVariants().size());
        VariantsDto variantsDto = productDto.getVariants().stream().toList().get(0);
        assertEquals("color code 1", variantsDto.getColorCode());
        assertEquals("M", variantsDto.getSize());
        assertEquals(123L, variantsDto.getStock().longValue());

        assertEquals(1, productDto.getColors().size());
        ColorDto colorDto = productDto.getColors().stream().toList().get(0);
        assertEquals("color code 1", colorDto.getCode());
        assertEquals("color name 1", colorDto.getName());

        assertEquals(1, productDto.getSizes().size());
        assertEquals("M", productDto.getSizes().stream().toList().get(0));
    }

    private void assertProductDto(List<ProductDto> input) {
        List<ProductDto> productDtoList = input
                .stream()
                .sorted(Comparator.comparing(ProductDto::getId))
                .toList();

        assertEquals(3, productDtoList.size());

        //p1
        assertEquals(1234L, productDtoList.get(0).getId().longValue());
        assertEquals(1, productDtoList.get(0).getImages().size());
        assertEquals(this.prefix + "image 1", productDtoList.get(0).getImages().stream().toList().get(0));

        assertEquals(1, productDtoList.get(0).getVariants().size());
        VariantsDto variantsDto = productDtoList.get(0).getVariants().stream().toList().get(0);
        assertEquals("color code 1", variantsDto.getColorCode());
        assertEquals("M", variantsDto.getSize());
        assertEquals(123L, variantsDto.getStock().longValue());

        assertEquals(1, productDtoList.get(0).getColors().size());
        ColorDto colorDto = productDtoList.get(0).getColors().stream().toList().get(0);
        assertEquals("color code 1", colorDto.getCode());
        assertEquals("color name 1", colorDto.getName());

        assertEquals(1, productDtoList.get(0).getSizes().size());
        assertEquals("M", productDtoList.get(0).getSizes().stream().toList().get(0));


        //p2
        assertEquals(2234L, productDtoList.get(1).getId().longValue());
        assertEquals(1, productDtoList.get(1).getImages().size());
        assertEquals(this.prefix + "image 2", productDtoList.get(1).getImages().stream().toList().get(0));

        assertEquals(1, productDtoList.get(1).getVariants().size());
        variantsDto = productDtoList.get(1).getVariants().stream().toList().get(0);
        assertEquals("color code 2", variantsDto.getColorCode());
        assertEquals("L", variantsDto.getSize());
        assertEquals(223L, variantsDto.getStock().longValue());

        assertEquals(1, productDtoList.get(1).getColors().size());
        colorDto = productDtoList.get(1).getColors().stream().toList().get(0);
        assertEquals("color code 2", colorDto.getCode());
        assertEquals("color name 2", colorDto.getName());

        assertEquals(1, productDtoList.get(1).getSizes().size());
        assertEquals("L", productDtoList.get(1).getSizes().stream().toList().get(0));

        //p3 & p4
        assertEquals(3234L, productDtoList.get(2).getId().longValue());
        assertEquals(2, productDtoList.get(2).getImages().size());
        List<String> images = productDtoList.get(2).getImages().stream().sorted(String::compareTo).toList();
        assertEquals(this.prefix + "image 3", images.get(0));
        assertEquals(this.prefix + "image 4", images.get(1));

        assertEquals(2, productDtoList.get(2).getVariants().size());

        List<VariantsDto> variantsDtoList = productDtoList.get(2).getVariants()
                .stream().sorted(Comparator.comparing(VariantsDto::getColorCode)).toList();

        variantsDto = variantsDtoList.get(0);
        assertEquals("color code 3", variantsDto.getColorCode());
        assertEquals("XL", variantsDto.getSize());
        assertEquals(323L, variantsDto.getStock().longValue());

        variantsDto = variantsDtoList.get(1);
        assertEquals("color code 4", variantsDto.getColorCode());
        assertEquals("L", variantsDto.getSize());
        assertEquals(1234L, variantsDto.getStock().longValue());

        assertEquals(2, productDtoList.get(2).getColors().size());
        List<ColorDto> colorDtoList = productDtoList.get(2).getColors()
                .stream().sorted(Comparator.comparing(ColorDto::getCode)).toList();
        colorDto = colorDtoList.get(0);
        assertEquals("color code 3", colorDto.getCode());
        assertEquals("color name 3", colorDto.getName());

        colorDto = colorDtoList.get(1);
        assertEquals("color code 4", colorDto.getCode());
        assertEquals("color name 4", colorDto.getName());

        assertEquals(2, productDtoList.get(2).getSizes().size());
        List<String> sizes = productDtoList.get(2).getSizes().stream().sorted(String::compareTo).toList();
        assertEquals("L", sizes.get(0));
        assertEquals("XL", sizes.get(1));
    }

}