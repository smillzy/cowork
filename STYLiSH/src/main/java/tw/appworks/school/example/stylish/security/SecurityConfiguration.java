package tw.appworks.school.example.stylish.security;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import tw.appworks.school.example.stylish.middleware.AdminCookieInterceptor;
import tw.appworks.school.example.stylish.middleware.JwtTokenFilter;
import tw.appworks.school.example.stylish.service.UserService;

@Configuration
public class SecurityConfiguration {

    private final UserService userService;

    private final JwtTokenFilter jwtTokenFilter;

    private final AdminCookieInterceptor adminCookieInterceptor;

    private final PasswordEncoder passwordEncoder;

    public static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    public SecurityConfiguration(UserService userService, JwtTokenFilter jwtTokenFilter,
                                 AdminCookieInterceptor adminCookieInterceptor, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTokenFilter = jwtTokenFilter;
        this.adminCookieInterceptor = adminCookieInterceptor;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);

        http.cors(
                        httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((e) -> e.authenticationEntryPoint((request, response, ex) -> {
                    logger.warn("ex: " + ex);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
                }))
                .authorizeHttpRequests(requests -> requests.requestMatchers("/admin", "/admin/**")
                        .hasAuthority("admin")
                        .requestMatchers("/api/1.0/user/signup")
                        .permitAll()
                        .requestMatchers("/api/1.0/user/signin")
                        .permitAll()
                        .anyRequest()
                        .permitAll())
                .addFilterBefore(adminCookieInterceptor, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtTokenFilter, AdminCookieInterceptor.class);

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
