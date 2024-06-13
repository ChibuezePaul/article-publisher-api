package io.isoft.article.publisher.service;

import io.isoft.article.publisher.exception.CustomException;
import io.isoft.article.publisher.models.dto.ApiResponse;
import io.isoft.article.publisher.models.request.AuthenticationRequest;
import io.isoft.article.publisher.models.request.RegisterRequest;

public interface UserService {

    ApiResponse register(RegisterRequest request) throws CustomException;

    ApiResponse authenticate(AuthenticationRequest request);
}
