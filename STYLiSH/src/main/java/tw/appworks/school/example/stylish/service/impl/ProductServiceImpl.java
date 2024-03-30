package tw.appworks.school.example.stylish.service.impl;

import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.appworks.school.example.stylish.data.dto.ProductDto;
import tw.appworks.school.example.stylish.data.form.ProductForm;
import tw.appworks.school.example.stylish.model.product.Color;
import tw.appworks.school.example.stylish.model.product.Product;
import tw.appworks.school.example.stylish.model.product.ProductImage;
import tw.appworks.school.example.stylish.model.product.Variant;
import tw.appworks.school.example.stylish.repository.product.*;
import tw.appworks.school.example.stylish.service.ProductService;
import tw.appworks.school.example.stylish.service.StorageService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${stylish.domain}")
    private String domain;

    @Value("${stylish.port}")
    private int port;

    @Value("${stylish.scheme}")
    private String scheme;

    @Value("${product.paging.size}")
    private int pagingSize;

    private final ProductsRepository productsRepository;

    private final ProductImageRepository productImageRepository;

    private final VariantRepository variantRepository;

    private final ColorRepository colorRepository;

    public static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private String prefix;

    public ProductServiceImpl(ProductsRepository productsRepository, ProductImageRepository productImageRepository,
                              VariantRepository variantRepository, ColorRepository colorRepository) {
        this.productsRepository = productsRepository;
        this.productImageRepository = productImageRepository;
        this.variantRepository = variantRepository;
        this.colorRepository = colorRepository;
    }

    private void createPrefix() {
        StringBuilder builder = new StringBuilder(scheme + "://" + domain);
        if (port != 80 && port != 443) {
            builder.append(":").append(port);
        }
        builder.append("/");
        this.prefix = builder.toString();
    }

    @Override
    public List<ProductDto> getProducts(@Nonnull String category, int pagingSize, int paging) {
        List<ProductProjection> products = getProductsProjections(category, pagingSize + 1, paging * pagingSize);
        return mapProjectionToDto(products);
    }

    @Override
    public List<ProductDto> getProducts(@Nonnull String category, int paging) {
        return getProducts(category, pagingSize, paging);
    }

    @Override
    public ProductDto getProduct(long id) {
        List<IProductProjection> productProjection = productsRepository.fetchProductById(id);
        if (productProjection != null) {
            return mapProjectionToDto(productProjection).stream().findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public List<ProductDto> searchProducts(@Nonnull String title, int paging) {
        List<IProductProjection> products = productsRepository.searchProductByTitle(title, pagingSize + 1,
                paging * pagingSize);
        return mapProjectionToDto(products);
    }

    private List<ProductProjection> getProductsProjections(@Nonnull String category, int pagingSize, int paging) {
        return productsRepository.fetchAllProductsByCategory(category, pagingSize + 1, paging * pagingSize);
    }

    @Override
    public Product saveProduct(Product product) {
        return productsRepository.save(product);
    }

    @Transactional
    @Override
    public void saveProduct(ProductForm productForm, StorageService storageService) {
        Product p = Product.from(productForm);
        final Product product = saveProduct(p);
        logger.info(product.toString());

        saveProductImage(ProductImage.from(product, productForm));

        storageService.store(productForm.getMainImage(), "products");

        productForm.getOtherImages().forEach(image -> storageService.store(image, "products"));

        List<String> sizes = Stream.of(productForm.getSizes().split(",", -1)).toList();
        List<Long> colorIds = Stream.of(productForm.getColorIds().split(",", -1)).map(Long::valueOf).toList();

        List<Variant> variants = sizes.stream().flatMap((size) -> colorIds.stream().map((cId) -> {
            Variant v = new Variant();
            v.setProduct(product);
            v.setSize(size);
            Color c = colorRepository.findById(cId).orElseThrow();
            v.setColor(c);
            v.setStock((int) Math.round(Math.random() * 10));
            return v;
        })).toList();
        variantRepository.saveAll(variants);
    }

    public List<ProductImage> saveProductImage(List<ProductImage> productImages) {
        return productImageRepository.saveAll(productImages);
    }

    private List<ProductDto> mapProjectionToDto(List<? extends IProductProjection> projections) {
        Map<Long, ProductDto> map = new HashMap<>();
        projections.forEach(mediatorProduct -> {
            ProductDto p = map.get(mediatorProduct.getId());
            if (p == null) {
                p = ProductDto.from(mediatorProduct);
            } else {
                p.updateAll(mediatorProduct);
            }
            map.put(p.getId(), p);
        });
        List<ProductDto> ret = map.values().stream().toList();
        ret.forEach(this::appendPrefix);
        return ret;
    }

    private void appendPrefix(ProductDto dto) {
        if (prefix == null) createPrefix();
        dto.setMainImage(prefix + dto.getMainImage());
        dto.setImages(dto.getImages().stream().map(image -> prefix + image).collect(Collectors.toSet()));
    }

}
