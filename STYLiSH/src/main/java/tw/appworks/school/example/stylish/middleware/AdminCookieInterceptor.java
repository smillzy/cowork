package tw.appworks.school.example.stylish.middleware;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class AdminCookieInterceptor extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AdminCookieInterceptor.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
        try {
            readServletCookie(request, "access-token").ifPresent(token -> {
                logger.debug("add token: " + token + " to header");
                mutableRequest.putHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            });
        } catch (Exception ignore) {
        } finally {
            filterChain.doFilter(mutableRequest, response);
        }
    }

    public Optional<String> readServletCookie(HttpServletRequest request, String name) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> name.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny();
    }

}
