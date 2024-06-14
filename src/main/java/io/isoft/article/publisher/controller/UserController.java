package io.isoft.article.publisher.controller;

import io.isoft.article.publisher.exception.CustomException;
import io.isoft.article.publisher.models.dto.ApiResponse;
import io.isoft.article.publisher.models.dto.UserDto;
import io.isoft.article.publisher.models.request.AuthenticationRequest;
import io.isoft.article.publisher.models.request.RegisterRequest;
import io.isoft.article.publisher.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> register(@Valid @RequestBody RegisterRequest request) throws CustomException {
        ApiResponse<UserDto> newUser = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/auth")
    public ResponseEntity<ApiResponse<UserDto>> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        ApiResponse<UserDto> authenticate = userService.authenticate(request);
        return ResponseEntity.ok(authenticate);
    }

}
