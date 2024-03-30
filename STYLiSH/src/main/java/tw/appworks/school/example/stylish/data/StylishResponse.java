package tw.appworks.school.example.stylish.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StylishResponse<T> {

    @JsonProperty("data")
    T data;

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("next_paging")
    public Integer nextPaging;

    public StylishResponse(T data) {
        this(data, null);
    }

    public StylishResponse(T data, Integer nextPaging) {
        this.data = data;
        this.nextPaging = nextPaging;
    }

}
