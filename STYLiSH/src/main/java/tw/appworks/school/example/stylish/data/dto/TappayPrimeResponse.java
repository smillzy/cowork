package tw.appworks.school.example.stylish.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TappayPrimeResponse {

    private int status;

    private String msg;

    @JsonProperty("rec_trade_id")
    private String recTradeId;

    @JsonProperty("order_number")
    private String orderNumber;

}
