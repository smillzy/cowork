package tw.appworks.school.example.stylish.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import tw.appworks.school.example.stylish.StylishApplication;
import tw.appworks.school.example.stylish.data.dto.SignInDto;
import tw.appworks.school.example.stylish.data.dto.UserDto;
import tw.appworks.school.example.stylish.data.form.SignupForm;
import tw.appworks.school.example.stylish.middleware.JwtTokenUtil;
import tw.appworks.school.example.stylish.model.user.Role;
import tw.appworks.school.example.stylish.model.user.User;
import tw.appworks.school.example.stylish.repository.user.RoleRepository;
import tw.appworks.school.example.stylish.repository.user.UserRepository;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StylishApplication.class, properties = {
        "spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false",
        "spring.jpa.hibernate.ddl-auto=none"
})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Mock
    private WebClient webClient;

    private User fakeUser;
    private Role fakeRole;

    @Before
    public void init() {
        fakeRole = new Role();
        fakeRole.setId(1);
        fakeRole.setName("fakeRoleName");

        fakeUser = new User();
        fakeUser.setId(100000L);
        fakeUser.setName("fake name");
        fakeUser.setEmail("fake@email");
        fakeUser.setPassword("fakePassword");
        fakeUser.setProvider("fakeProvider");
        fakeUser.setAccessToken("fakeToken");
        fakeUser.setPicture("fakePicture");
        fakeUser.setAccessExpired(123456L);
        fakeUser.setRole(fakeRole);
    }

    @Test
    public void get_user_by_token_success() throws UserService.UserNotExistException {

        when(userRepository.findUserByEmail("fake@email")).thenReturn(fakeUser);
        when(jwtTokenUtil.getUsernameFromToken("fakeToken")).thenReturn(fakeUser.getEmail());
        User u = userService.getUserByToken("fakeToken");
        assertEquals("fake@email", u.getEmail());
        assertEquals("fakeRoleName", u.getRole().getName());
    }

    @Test
    public void get_user_by_token_failed() {

        when(userRepository.findUserByEmail("fake@email")).thenReturn(null);
        when(jwtTokenUtil.getUsernameFromToken("fakeToken")).thenReturn(fakeUser.getEmail());

        UserService.UserNotExistException e = assertThrows(
                UserService.UserNotExistException.class, () -> {
                    userService.getUserByToken("fakeToken");
                }
        );
        String actualMessage = e.getMessage();
        assertEquals("User Not Found with username : " + fakeUser.getEmail(), actualMessage);
    }

    @Test
    public void get_userDto_by_token_success() throws UserService.UserNotExistException {
        when(userRepository.findUserByEmail("fake@email")).thenReturn(fakeUser);
        when(jwtTokenUtil.getUsernameFromToken("fakeToken")).thenReturn(fakeUser.getEmail());

        UserDto userDto = userService.getUserDtoByToken("fakeToken");
        assertEquals("fake name", userDto.getName());
        assertEquals("fake@email", userDto.getEmail());
        assertEquals(100000L, userDto.getId().longValue());
    }

    @Test
    public void get_userDto_by_token_failed() {
        when(userRepository.findUserByEmail("fake@email")).thenReturn(null);
        when(jwtTokenUtil.getUsernameFromToken("fakeToken")).thenReturn(fakeUser.getEmail());


        UserService.UserNotExistException e = assertThrows(
                UserService.UserNotExistException.class, () -> {
                    userService.getUserDtoByToken("fakeToken");
                }
        );
        String actualMessage = e.getMessage();
        assertEquals("User Not Found with username : fake@email", actualMessage);
    }

    @Test
    public void signup_success() throws UserService.UserExistException, UserService.RoleNotFoundException {
        SignupForm signupForm = new SignupForm("fakeName", "fake@email", "fakePassword");

        when(jwtTokenUtil.generateToken(any(), any())).thenReturn("fakeToken");
        when(jwtTokenUtil.getExpirationDateFromToken(any())).thenReturn(new Date());
        when(userRepository.findUserByEmail(any())).thenReturn(null);
        when(roleRepository.findRoleByName(any())).thenReturn(fakeRole);
        when(userRepository.save(any())).thenReturn(fakeUser);


        SignInDto signInDto = userService.signup(signupForm, "fakeRoleName");
        assertSignInDto(signInDto);
    }

    @Test
    public void signup_failed_user_exist() {
        SignupForm signupForm = new SignupForm("fakeName", "fake2@email", "fakePassword");
        when(userRepository.findUserByEmail(any())).thenReturn(fakeUser);

        UserService.UserExistException e = assertThrows(
                UserService.UserExistException.class, () -> {
                    userService.signup(signupForm, "fakeRoleName");
                }
        );
        String actualMessage = e.getMessage();
        assertEquals("fake2@email is already exist", actualMessage);

    }

    @Test
    public void signup_failed_role_not_found() {
        SignupForm signupForm = new SignupForm("fakeName", "fake2@email", "fakePassword");
        when(userRepository.findUserByEmail(any())).thenReturn(null);
        when(roleRepository.findRoleByName(any())).thenReturn(null);

        UserService.RoleNotFoundException e = assertThrows(
                UserService.RoleNotFoundException.class, () -> {
                    userService.signup(signupForm, "fakeRoleName");
                }
        );
        String actualMessage = e.getMessage();
        assertEquals("fakeRoleName not found", actualMessage);

    }

    @Test
    public void native_sign_in_success() throws UserService.UserNotExistException, UserService.UserPasswordMismatchException {
        when(userRepository.findUserByEmail(any())).thenReturn(fakeUser);
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        SignInDto signInDto = userService.nativeSign("", "");
        assertSignInDto(signInDto);
    }

    @Test
    public void native_sign_in_failed_user_not_found() {
        when(userRepository.findUserByEmail(any())).thenReturn(null);

        UserService.UserNotExistException e = assertThrows(
                UserService.UserNotExistException.class, () -> {
                    userService.nativeSign("mail", "password");
                }
        );
        String actualMessage = e.getMessage();
        assertEquals("User Not Found with email : mail", actualMessage);

    }

    @Test
    public void native_sign_in_failed_password_mismatch() {
        when(userRepository.findUserByEmail(any())).thenReturn(fakeUser);
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        UserService.UserPasswordMismatchException e = assertThrows(
                UserService.UserPasswordMismatchException.class, () -> {
                    userService.nativeSign("mail", "password");
                }
        );
        String actualMessage = e.getMessage();
        assertEquals("Wrong Password", actualMessage);

    }

    private void assertSignInDto(SignInDto signInDto) {
        assertEquals("fakeToken", signInDto.getAccessToken());
        assertEquals(123456L, signInDto.getAccessExpired().longValue());
        assertEquals("fake name", signInDto.getUser().getName());
        assertEquals("fake@email", signInDto.getUser().getEmail());
        assertEquals(100000L, signInDto.getUser().getId().longValue());
        assertEquals("fakeProvider", signInDto.getUser().getProvider());
        assertEquals("fakePicture", signInDto.getUser().getPicture());
    }

}