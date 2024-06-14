package io.isoft.article.publisher.service.impl;

import io.isoft.article.publisher.commons.security.JwtService;
import io.isoft.article.publisher.exception.CustomException;
import io.isoft.article.publisher.models.dto.ApiResponse;
import io.isoft.article.publisher.models.dto.UserDto;
import io.isoft.article.publisher.models.entity.User;
import io.isoft.article.publisher.models.request.AuthenticationRequest;
import io.isoft.article.publisher.models.request.RegisterRequest;
import io.isoft.article.publisher.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserServiceImpl underTest;

    @Test
    void register_shouldThrowException_whenEmailExists() throws CustomException {
        String email = "cp@email.com";
        RegisterRequest request = new RegisterRequest("paul", "chibueze", email, "****");

        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertThrows(CustomException.class, () -> underTest.register(request));
    }

    @Test
    void register_shouldCreateUserSuccessfully_whenEmailDoesNotExist() throws CustomException {
        String email = "cp@email.com";
        var request = new RegisterRequest("paul", "chibueze", email, "****");
        var user = new User("****", email, "chibueze", "****");

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        ApiResponse<UserDto> newUser = underTest.register(request);

        assertNotNull(newUser);
        assertNotNull(newUser.data());
        assertEquals(user.getEmail(), newUser.data().getEmail());
    }

    @Test
    void authenticate_shouldThrowException_whenLoginCredentialsAreIncorrect() {
        var request = new AuthenticationRequest("email", "***");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        assertThrows(BadCredentialsException.class, () -> underTest.authenticate(request));
    }

    @Test
    void authenticate_shouldLoginSuccessfully_whenLoginCredentialsAreCorrect() {
        String email = "email";
        var request = new AuthenticationRequest(email, "***");
        var user = new User("****", email, "chibueze", "****");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(user, "***"));

        ApiResponse<UserDto> authenticate = underTest.authenticate(request);

        assertNotNull(authenticate);
        assertNotNull(authenticate.data());
        assertEquals(user.getEmail(), authenticate.data().getEmail());
    }
}