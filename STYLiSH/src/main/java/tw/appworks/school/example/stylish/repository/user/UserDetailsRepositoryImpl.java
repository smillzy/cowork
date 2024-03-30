package tw.appworks.school.example.stylish.repository.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import tw.appworks.school.example.stylish.middleware.JwtTokenUtil;

public class UserDetailsRepositoryImpl implements UserDetailsRepository {

    private final JwtTokenUtil jwtTokenUtil;

    public UserDetailsRepositoryImpl(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public UserDetails getUserDetailsByToken(String token) {
        return User.builder()
                .username(jwtTokenUtil.getUsernameFromToken(token))
                .password("")
                .authorities(jwtTokenUtil.getUserAuthoritiesFromToken(token))
                .build();
    }

    @Override
    public UserDetails getUserDetails(String userName, String password, String role) {
        return User.builder().username(userName).password(password).roles(role).build();
    }

}
