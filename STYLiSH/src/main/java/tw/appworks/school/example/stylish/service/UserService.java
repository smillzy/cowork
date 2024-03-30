package tw.appworks.school.example.stylish.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import tw.appworks.school.example.stylish.data.dto.SignInDto;
import tw.appworks.school.example.stylish.data.dto.UserDto;
import tw.appworks.school.example.stylish.data.form.SignupForm;
import tw.appworks.school.example.stylish.model.user.User;

public interface UserService extends UserDetailsService {

    String NATIVE = "native";

    String FACEBOOK = "facebook";

    sealed class UserException extends
            Exception permits UserExistException, UserNotExistException,
            UserPasswordMismatchException, RoleNotFoundException {

        public UserException(String message) {
            super(message);
        }

    }

    final class UserExistException extends UserException {

        public UserExistException(String message) {
            super(message);
        }

    }

    final class UserNotExistException extends UserException {

        public UserNotExistException(String message) {
            super(message);
        }

    }

    final class UserPasswordMismatchException extends UserException {

        public UserPasswordMismatchException(String message) {
            super(message);
        }

    }

    final class RoleNotFoundException extends UserException {

        public RoleNotFoundException(String message) {
            super(message);
        }

    }

    UserDto getUserDtoByToken(String token) throws UserNotExistException;

    User getUserByToken(String token) throws UserNotExistException;

    SignInDto signup(SignupForm signupForm, String role) throws UserExistException, RoleNotFoundException;

    SignInDto nativeSign(String email, String password) throws UserNotExistException, UserPasswordMismatchException;

    SignInDto facebookSign(String token, String role) throws UserNotExistException, RoleNotFoundException;

}
