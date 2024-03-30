package tw.appworks.school.example.stylish.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import tw.appworks.school.example.stylish.data.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public class ProductDtoGenerator {

    public static final String PRODUCT_JSON = """
                {
                    "data": {
                        "id": 201807201824,
                        "category": "women",
                        "title": "前開衩扭結洋裝",
                        "description": "厚薄：薄彈性：無",
                        "price": 799,
                        "texture": "棉 100%",
                        "wash": "手洗，溫水",
                        "place": "中國",
                        "note": "實品顏色依單品照為主",
                        "story": "O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.",
                        "main_image": "scheme://server:3000/assets/201807201824/main.jpg",
                        "images": [
                          "scheme://server:3000/assets/201807201824/0.jpg",
                          "scheme://server:3000/assets/201807201824/1.jpg"
                        ],
                        "variants": [
                          {
                            "color_code": "FFFFFF",
                            "size": "S",
                            "stock": 2
                          },
                          {
                            "color_code": "FFFFFF",
                            "size": "M",
                            "stock": 1
                          },
                          {
                            "color_code": "FFFFFF",
                            "size": "L",
                            "stock": 2
                          },
                          {
                            "color_code": "DDFFBB",
                            "size": "S",
                            "stock": 9
                          },
                          {
                            "color_code": "DDFFBB",
                            "size": "M",
                            "stock": 0
                          },
                          {
                            "color_code": "DDFFBB",
                            "size": "L",
                            "stock": 5
                          },
                          {
                            "color_code": "CCCCCC",
                            "size": "S",
                            "stock": 8
                          },
                          {
                            "color_code": "CCCCCC",
                            "size": "M",
                            "stock": 5
                          },
                          {
                            "color_code": "CCCCCC",
                            "size": "L",
                            "stock": 9
                          }
                        ],
                        "colors": [
                          {
                            "code": "FFFFFF",
                            "name": "白色"
                          },
                          {
                            "code": "DDFFBB",
                            "name": "亮綠"
                          },
                          {
                            "code": "CCCCCC",
                            "name": "淺灰"
                          }
                        ],
                        "sizes": [
                          "S",
                          "M",
                          "L"
                        ]
                      }
                }
            """;

    public static final String PRODUCTS_JSON = """
                {
                    "data": [
                      {
                        "id": 201807201824,
                        "category": "women",
                        "title": "前開衩扭結洋裝",
                        "description": "厚薄：薄彈性：無",
                        "price": 799,
                        "texture": "棉 100%",
                        "wash": "手洗，溫水",
                        "place": "中國",
                        "note": "實品顏色依單品照為主",
                        "story": "O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.",
                        "main_image": "scheme://server:3000/assets/201807201824/main.jpg",
                        "images": [
                          "scheme://server:3000/assets/201807201824/0.jpg",
                          "scheme://server:3000/assets/201807201824/1.jpg"
                        ],
                        "variants": [
                          {
                            "color_code": "FFFFFF",
                            "size": "S",
                            "stock": 2
                          },
                          {
                            "color_code": "FFFFFF",
                            "size": "M",
                            "stock": 1
                          },
                          {
                            "color_code": "FFFFFF",
                            "size": "L",
                            "stock": 2
                          },
                          {
                            "color_code": "DDFFBB",
                            "size": "S",
                            "stock": 9
                          },
                          {
                            "color_code": "DDFFBB",
                            "size": "M",
                            "stock": 0
                          },
                          {
                            "color_code": "DDFFBB",
                            "size": "L",
                            "stock": 5
                          },
                          {
                            "color_code": "CCCCCC",
                            "size": "S",
                            "stock": 8
                          },
                          {
                            "color_code": "CCCCCC",
                            "size": "M",
                            "stock": 5
                          },
                          {
                            "color_code": "CCCCCC",
                            "size": "L",
                            "stock": 9
                          }
                        ],
                        "colors": [
                          {
                            "code": "FFFFFF",
                            "name": "白色"
                          },
                          {
                            "code": "DDFFBB",
                            "name": "亮綠"
                          },
                          {
                            "code": "CCCCCC",
                            "name": "淺灰"
                          }
                        ],
                        "sizes": [
                          "S",
                          "M",
                          "L"
                        ]
                      }
                    ]
                }
            """;

    public static List<ProductDto> getMockProducts() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new ClassPathResource("./test/products.json").getFile(), new TypeReference<>() {
        });
    }

    public static ProductDto getMockProduct() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new ClassPathResource("./test/product.json").getFile(), ProductDto.class);
    }
}
