package tw.appworks.school.example.stylish.controller.v1;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.example.stylish.data.StylishResponse;
import tw.appworks.school.example.stylish.data.dto.SignInDto;
import tw.appworks.school.example.stylish.data.form.SignInFrom;
import tw.appworks.school.example.stylish.data.form.SignupForm;
import tw.appworks.school.example.stylish.error.ErrorResponse;
import tw.appworks.school.example.stylish.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("api/1.0/user")
public class UserController {

    private static final String _native = UserService.NATIVE;

    private static final String _facebook = UserService.FACEBOOK;

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signup", consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<?> signUp(@RequestBody SignupForm signupForm, HttpServletResponse response) {
        try {
            SignInDto dto = userService.signup(signupForm, "user");
            return getBodyBuilder(dto).body(new StylishResponse<>(dto));
        } catch (UserService.UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping(value = "/signin", consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<?> signIn(@RequestBody SignInFrom signInFrom, HttpServletResponse response) {
        try {
            final String provider = getSupportedProvider(signInFrom);
            switch (provider) {
                case _native -> {
                    SignInDto dto = userService.nativeSign(signInFrom.getEmail(), signInFrom.getPassword());
                    return getBodyBuilder(dto).body(new StylishResponse<>(dto));
                }
                case _facebook -> {
                    SignInDto dto = userService.facebookSign(signInFrom.getAccessToken(), "user");
                    return getBodyBuilder(dto).body(new StylishResponse<>(dto));

                }
                default -> throw new ServletRequestBindingException("Bad Request: missing parameter");
            }
        } catch (UserService.UserException | ServletRequestBindingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/profile")
    @ResponseBody
    public ResponseEntity<?> getProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        try {
            String token = authorization.split(" ")[1].trim();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new StylishResponse<>(userService.getUserDtoByToken(token)));
        } catch (UserService.UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    private String getSupportedProvider(SignInFrom signInFrom) throws ServletRequestBindingException {
        try {
            if (!signInFrom.getProvider().matches("^(%s|%s)$".formatted(_native, _facebook))) {
                throw new ServletRequestBindingException("Bad Request: not supported provider");
            }

            if (signInFrom.getProvider().equals(_native)
                    && (signInFrom.getPassword() == null || signInFrom.getEmail() == null)) {
                throw new ServletRequestBindingException("Bad Request: missing parameter");
            }

            if (signInFrom.getProvider().equals(_facebook) && signInFrom.getAccessToken() == null) {
                throw new ServletRequestBindingException("Bad Request: missing parameter");
            }
        } catch (NullPointerException e) {
            throw new ServletRequestBindingException("Bad Request: missing parameter");
        }
        return signInFrom.getProvider();
    }

    private ResponseEntity.BodyBuilder getBodyBuilder(SignInDto dto) {
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.OK);
        Optional<ResponseCookie> rc = createCookie(dto.getAccessToken());
        if (rc.isPresent()) {
            builder = builder.header(HttpHeaders.SET_COOKIE, rc.get().toString());
        }
        return builder;
    }

    private Optional<ResponseCookie> createCookie(String token) {
        if (token != null) {
            logger.info("set cookie: " + token);
            ResponseCookie springCookie = ResponseCookie.from("access-token", token)
                    .httpOnly(true)
                    .path("/admin/")
                    .build();
            return Optional.of(springCookie);
        }
        return Optional.empty();
    }

}
