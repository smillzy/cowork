package tw.appworks.school.example.stylish.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.appworks.school.example.stylish.model.user.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInDto {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("access_expired")
    private Long accessExpired;

    @JsonProperty("user")
    private UserDto user;

    public static SignInDto from(User user) {
        SignInDto dto = new SignInDto();
        dto.setAccessToken(user.getAccessToken());
        dto.setAccessExpired(user.getAccessExpired());
        UserDto userDto = UserDto.from(user);
        dto.setUser(userDto);
        return dto;
    }

}
