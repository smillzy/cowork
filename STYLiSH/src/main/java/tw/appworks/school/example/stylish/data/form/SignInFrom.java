package tw.appworks.school.example.stylish.data.form;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInFrom {

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("email")
    private String email;

    @Nullable
    @JsonProperty("password")
    private String password;

    @Nullable
    @JsonProperty("access_token")
    private String accessToken;

}
