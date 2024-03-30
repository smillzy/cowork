package tw.appworks.school.example.stylish.middleware;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tw.appworks.school.example.stylish.repository.user.UserRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.micrometer.common.util.StringUtils.isEmpty;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    private final JwtTokenUtil jwtTokenUtil;

    private final ObjectMapper jsonObjectMapper = new ObjectMapper();

    public static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    public JwtTokenFilter(UserRepository userRepository, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            final String token = retrieveToken(request);
            if (token == null || !jwtTokenUtil.validate(token)) {
                chain.doFilter(request, response);
                return;
            }
            UserDetails userDetails = userRepository.getUserDetailsByToken(token);
            UsernamePasswordAuthenticationToken authAfterSuccessLogin = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authAfterSuccessLogin.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authAfterSuccessLogin);
            logger.debug("token verify OK: " + userDetails.getUsername());
            chain.doFilter(request, response);
        } catch (Exception e) {
            Map<String, String> errorMsg = new HashMap<>();
            errorMsg.put("error", e.getMessage());
            handleException(response, HttpStatus.UNAUTHORIZED.value(), errorMsg);
        }
    }

    @Nullable
    public String retrieveToken(HttpServletRequest request) {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.split(" ")[1].trim();
    }

    private void handleException(HttpServletResponse response, int status, Map<String, String> message)
            throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        jsonObjectMapper.writeValue(response.getWriter(), message);
    }

}
