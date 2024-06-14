package io.isoft.article.publisher.service.impl;

import io.isoft.article.publisher.commons.security.JwtService;
import io.isoft.article.publisher.exception.CustomException;
import io.isoft.article.publisher.models.dto.ApiResponse;
import io.isoft.article.publisher.models.dto.UserDto;
import io.isoft.article.publisher.models.entity.User;
import io.isoft.article.publisher.models.request.AuthenticationRequest;
import io.isoft.article.publisher.models.request.RegisterRequest;
import io.isoft.article.publisher.repository.UserRepository;
import io.isoft.article.publisher.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public ApiResponse<UserDto> register(RegisterRequest request) throws CustomException {
        String email = request.email().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "User Already Exists");
        }

        var user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(email)
                .password(passwordEncoder.encode(request.password()))
                .build();
        userRepository.save(user);

        var userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return new ApiResponse<>("User Successfully Created", userDto);
    }

    @Override
    public ApiResponse<UserDto> authenticate(AuthenticationRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email().toLowerCase(),
                        request.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        User user = (User) authenticate.getPrincipal();

        var token = jwtService.generateToken(user);
        var userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setToken(token);

        return new ApiResponse<>("Login Successful", userDto);
    }
}
