package tw.appworks.school.example.stylish.repository.user;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsRepository {

    UserDetails getUserDetailsByToken(String token);

    UserDetails getUserDetails(String userName, String password, String role);

}
