package io.isoft.article.publisher.service;

import io.isoft.article.publisher.controller.CreateArticleRequest;
import io.isoft.article.publisher.exception.CustomException;
import io.isoft.article.publisher.models.dto.ApiResponse;
import io.isoft.article.publisher.models.request.ArticleAuthorsRequest;
import io.isoft.article.publisher.models.request.ArticlePublishersRequest;
import io.isoft.article.publisher.models.request.RemoveAuthorRequest;
import io.isoft.article.publisher.models.request.SearchArticleRequest;
import io.isoft.article.publisher.models.request.UpdateArticleRequest;

public interface  ArticleService {
    ApiResponse createArticle(CreateArticleRequest articleRequest);

    ApiResponse addPublishersToArticle(long id, ArticlePublishersRequest publishersRequest) throws CustomException;

    ApiResponse addAuthorsToArticle(Long id, ArticleAuthorsRequest authorsRequest) throws CustomException;

    ApiResponse removeAuthorFromArticle(Long id, RemoveAuthorRequest removeAuthorRequest) throws CustomException;

    ApiResponse publishArticle(Long id) throws CustomException;

    ApiResponse unpublishArticle(Long id) throws CustomException;

    ApiResponse deleteArticle(Long id) throws CustomException;

    ApiResponse updateArticle(Long id, UpdateArticleRequest updateArticleRequest) throws CustomException;

    ApiResponse findAllArticle(SearchArticleRequest searchArticleRequest);
}
