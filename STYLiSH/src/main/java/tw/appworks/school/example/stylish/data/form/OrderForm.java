package tw.appworks.school.example.stylish.data.form;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import tw.appworks.school.example.stylish.data.dto.ColorDto;

import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderForm {

    private String prime;

    private Order order;

    @Data
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Order {

        private String shipping;

        private String payment;

        private Integer subtotal;

        private Integer freight;

        private Integer total;

        private Recipient recipient;

        private List<OrderItem> list;

    }

    @Data
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Recipient {

        private String name;

        private String phone;

        private String email;

        private String address;

        private String time;

    }

    @Data
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class OrderItem {

        private String id;

        private String name;

        private Integer price;

        private ColorDto color;

        private String size;

        private Integer qty;

    }

}
