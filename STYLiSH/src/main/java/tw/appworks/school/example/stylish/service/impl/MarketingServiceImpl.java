package tw.appworks.school.example.stylish.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tw.appworks.school.example.stylish.data.dto.CampaignDto;
import tw.appworks.school.example.stylish.data.dto.MarketHotsDto;
import tw.appworks.school.example.stylish.data.dto.ProductDto;
import tw.appworks.school.example.stylish.data.form.CampaignForm;
import tw.appworks.school.example.stylish.model.campaign.Campaign;
import tw.appworks.school.example.stylish.model.product.Product;
import tw.appworks.school.example.stylish.repository.marketing.MarketingRepository;
import tw.appworks.school.example.stylish.repository.product.ProductsRepository;
import tw.appworks.school.example.stylish.service.MarketingService;
import tw.appworks.school.example.stylish.service.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "marketingService")
public class MarketingServiceImpl implements MarketingService {

    private static final Logger logger = LoggerFactory.getLogger(MarketingServiceImpl.class);

    private final MarketingRepository marketingRepository;

    private final ProductsRepository productsRepository;

    public MarketingServiceImpl(MarketingRepository marketingRepository, ProductsRepository productsRepository) {
        this.marketingRepository = marketingRepository;
        this.productsRepository = productsRepository;
    }

    @Override
    @Cacheable(value = "getCampaigns", keyGenerator = "wiselyKeyGenerator")
    public List<CampaignDto> getCampaigns() {
        return marketingRepository.findAll().stream().map(CampaignDto::from).toList();
    }

    @Override
    @CacheEvict(value = "getCampaigns", allEntries = true)
    public Campaign saveCampaign(CampaignForm campaignForm) {
        Optional<Product> p = productsRepository.findById(campaignForm.getProductId());

        Product product = p.orElseThrow();

        Campaign c = new Campaign();
        c.setProduct(product);
        c.setStory(campaignForm.getStory());
        c.setPicture("assets/campaigns/" + campaignForm.getMainImage().getOriginalFilename());

        return marketingRepository.save(c);
    }

    @Override
    public List<MarketHotsDto> getHots(ProductService service) {
        Map<String, MarketHotsDto> map = new HashMap<>();
        marketingRepository.findAllHotProduct().forEach(hotProduct -> {
            ProductDto productDto = service.getProduct(hotProduct.getProduct().getId());
            if (productDto != null) {
                MarketHotsDto dto = map.get(hotProduct.getHot().getTitle());
                if (dto == null) {
                    dto = MarketHotsDto.from(hotProduct);
                    map.put(hotProduct.getHot().getTitle(), dto);
                }
                dto.getProducts().add(productDto);
            }
        });
        return map.values().stream().toList();
    }

}
