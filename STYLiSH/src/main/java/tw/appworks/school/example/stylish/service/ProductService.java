package tw.appworks.school.example.stylish.service;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import tw.appworks.school.example.stylish.data.dto.ProductDto;
import tw.appworks.school.example.stylish.data.form.ProductForm;
import tw.appworks.school.example.stylish.model.product.Product;

import java.util.List;

public interface ProductService {

    List<ProductDto> getProducts(@Nonnull String category, int pagingSize, int paging);

    List<ProductDto> getProducts(@Nonnull String category, int paging);

    @Nullable
    ProductDto getProduct(long id);

    List<ProductDto> searchProducts(@Nonnull String title, int paging);

    Product saveProduct(Product product);

    void saveProduct(ProductForm productForm, StorageService storageService);

}
