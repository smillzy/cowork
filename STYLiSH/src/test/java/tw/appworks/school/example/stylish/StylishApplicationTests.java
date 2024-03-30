package tw.appworks.school.example.stylish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tw.appworks.school.example.stylish.controller.AdminPageController;
import tw.appworks.school.example.stylish.controller.v1.*;
import tw.appworks.school.example.stylish.controller.v2.ReportControllerV2;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false",
        "spring.jpa.hibernate.ddl-auto=none"
})
class StylishApplicationTests {

    @Autowired
    private AdminApiController adminApiController;

    @Autowired
    private MarketingController marketingController;

    @Autowired
    private OrderController orderController;

    @Autowired
    private ProductsController productsController;

    @Autowired
    private ReportController reportController;

    @Autowired
    private ReportControllerV2 reportControllerV2;

    @Autowired
    private UserController userController;

    @Autowired
    private AdminPageController adminPageController;

    @Test
    void contextLoads() {
        assertThat(adminApiController).isNotNull();
        assertThat(marketingController).isNotNull();
        assertThat(orderController).isNotNull();
        assertThat(productsController).isNotNull();
        assertThat(reportController).isNotNull();
        assertThat(reportControllerV2).isNotNull();
        assertThat(userController).isNotNull();
        assertThat(adminPageController).isNotNull();
    }

}