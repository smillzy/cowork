package tw.appworks.school.example.stylish.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReportPayment {

    @JsonProperty("user_id")
    private Long id;

    @JsonProperty("total_payment")
    private float totalPayment;

    @Override
    public String toString() {
        return "ReportPayment [id=" + id + ", totalPayment=" + totalPayment + "]";
    }

}
