package tw.appworks.school.example.stylish.data.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TappayRequestForm {

    private String prime;

    @JsonProperty("partner_key")
    private String partnerKey;

    @JsonProperty("merchant_id")
    private String merchantId;

    private String details;

    private int amount;

    @JsonProperty("order_number")
    private String orderNumber;

    private Cardholder cardholder;

    private boolean remember;

    @Data
    @AllArgsConstructor
    public static class Cardholder {

        @JsonProperty("phone_number")
        private String phoneNumber;

        private String name;

        private String email;

    }

    public static TappayRequestForm from(String tappayKey, String tappayId, OrderForm orderForm, String orderNumber)
            throws JsonProcessingException {
        Cardholder cardholder = new Cardholder(orderForm.getOrder().getRecipient().getPhone(),
                orderForm.getOrder().getRecipient().getName(), orderForm.getOrder().getRecipient().getEmail());
        return new TappayRequestForm(orderForm.getPrime(), tappayKey, tappayId, "Stylish Payment",
                orderForm.getOrder().getTotal(), orderNumber, cardholder, false);
    }

}
