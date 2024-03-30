package tw.appworks.school.example.stylish.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import tw.appworks.school.example.stylish.StylishApplication;
import tw.appworks.school.example.stylish.data.dto.CampaignDto;
import tw.appworks.school.example.stylish.data.dto.MarketHotsDto;
import tw.appworks.school.example.stylish.data.dto.ProductDto;
import tw.appworks.school.example.stylish.data.form.CampaignForm;
import tw.appworks.school.example.stylish.model.campaign.Campaign;
import tw.appworks.school.example.stylish.model.marketing.Hot;
import tw.appworks.school.example.stylish.model.marketing.HotProduct;
import tw.appworks.school.example.stylish.model.product.Product;
import tw.appworks.school.example.stylish.repository.marketing.MarketingRepository;
import tw.appworks.school.example.stylish.repository.product.ProductsRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StylishApplication.class, properties = {
        "spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false",
        "spring.jpa.hibernate.ddl-auto=none"
})
public class MarketingServiceTest {

    @Value("${server.port}")
    private int port;

    @Autowired
    private MarketingService marketingService;

    @MockBean
    private MarketingRepository marketingRepository;

    @MockBean
    private ProductsRepository productsRepository;

    @MockBean
    private ProductService productService;

    @MockBean
    private CampaignForm campaignForm;

    @MockBean
    private MultipartFile multipartFile;

    private List<Campaign> fakeCampaigns;
    private Campaign c1, c2;
    private Product p1, p2, p3;
    private ProductDto pDto1, pDto2, pDto3;


    private List<HotProduct> fakeHotProducts;
    private Hot h1, h2, h3;

    private HotProduct hp1, hp2, hp3;

    @Before
    public void init() {
        p1 = new Product();
        p1.setId(1234L);
        pDto1 = new ProductDto();
        pDto1.setId(p1.getId());
        c1 = new Campaign();
        c1.setId(1234L);
        c1.setPicture("c1 picture");
        c1.setStory("c1 story");
        c1.setProduct(p1);

        p2 = new Product();
        p2.setId(2234L);
        pDto2 = new ProductDto();
        pDto2.setId(p2.getId());
        c2 = new Campaign();
        c2.setId(2234L);
        c2.setPicture("c2 picture");
        c2.setStory("c2 story");
        c2.setProduct(p2);

        fakeCampaigns = List.of(c1, c2);

        p3 = new Product();
        p3.setId(3234L);
        pDto3 = new ProductDto();
        pDto3.setId(p3.getId());

        h1 = new Hot();
        h1.setId(12345L);
        h1.setTitle("h1 title");

        h2 = new Hot();
        h2.setId(22345L);
        h2.setTitle("same title");

        h3 = new Hot();
        h3.setId(12345L);
        h3.setTitle("same title");

        hp1 = new HotProduct();
        hp1.setHot(h1);
        hp1.setProduct(p1);

        hp2 = new HotProduct();
        hp2.setHot(h2);
        hp2.setProduct(p2);

        hp3 = new HotProduct();
        hp3.setHot(h3);
        hp3.setProduct(p3);

        fakeHotProducts = List.of(hp1, hp2, hp3);
    }

    @Test
    public void get_campaigns_success() {

        when(marketingRepository.findAll()).thenReturn(fakeCampaigns);
        List<CampaignDto> campaignDtoList = marketingService.getCampaigns();

        assertEquals(2, campaignDtoList.size());

        assertEquals(1234L, campaignDtoList.get(0).getId().longValue());
        assertEquals(1234L, campaignDtoList.get(0).getProductId().longValue());
        assertEquals("c1 picture", campaignDtoList.get(0).getPicture());
        assertEquals("c1 story", campaignDtoList.get(0).getStory());

        assertEquals(2234L, campaignDtoList.get(1).getId().longValue());
        assertEquals(2234L, campaignDtoList.get(1).getProductId().longValue());
        assertEquals("c2 picture", campaignDtoList.get(1).getPicture());
        assertEquals("c2 story", campaignDtoList.get(1).getStory());

    }

    @Test
    public void save_campaign_success() {
        when(multipartFile.getOriginalFilename()).thenReturn(c1.getPicture());
        when(campaignForm.getProductId()).thenReturn(c1.getId());
        when(campaignForm.getStory()).thenReturn(c1.getStory());
        when(campaignForm.getMainImage()).thenReturn(multipartFile);
        when(productsRepository.findById(any())).thenReturn(Optional.of(p1));
        when(marketingRepository.save(any())).thenReturn(c1);

        Campaign c = marketingService.saveCampaign(campaignForm);
        assertEquals(1234L, c.getId().longValue());
        assertEquals(1234L, c.getProduct().getId().longValue());
        assertEquals("c1 picture", c.getPicture());
        assertEquals("c1 story", c.getStory());
    }

    @Test
    public void get_hots_success() {
        when(marketingRepository.findAllHotProduct()).thenReturn(fakeHotProducts);
        when(productService.getProduct(p1.getId())).thenReturn(pDto1);
        when(productService.getProduct(p2.getId())).thenReturn(pDto2);
        when(productService.getProduct(p3.getId())).thenReturn(pDto3);


        List<MarketHotsDto> marketHotsDtoList = marketingService.getHots(productService);
        assertEquals(2, marketHotsDtoList.size());

        assertEquals("h1 title", marketHotsDtoList.get(0).getTitle());
        assertEquals(1, marketHotsDtoList.get(0).getProducts().size());
        assertEquals(1234L, marketHotsDtoList.get(0).getProducts().get(0).getId().longValue());


        assertEquals("same title", marketHotsDtoList.get(1).getTitle());
        assertEquals(2, marketHotsDtoList.get(1).getProducts().size());
        assertEquals(2234L, marketHotsDtoList.get(1).getProducts().get(0).getId().longValue());
        assertEquals(3234L, marketHotsDtoList.get(1).getProducts().get(1).getId().longValue());

    }

}