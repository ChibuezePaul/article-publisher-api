package io.isoft.article.publisher.service;

import io.isoft.article.publisher.models.dto.ArticleDto;
import io.isoft.article.publisher.models.request.CreateArticleRequest;
import io.isoft.article.publisher.exception.CustomException;
import io.isoft.article.publisher.models.dto.ApiResponse;
import io.isoft.article.publisher.models.request.ArticleAuthorsRequest;
import io.isoft.article.publisher.models.request.ArticlePublishersRequest;
import io.isoft.article.publisher.models.request.RemoveAuthorRequest;
import io.isoft.article.publisher.models.request.SearchArticleRequest;
import io.isoft.article.publisher.models.request.UpdateArticleRequest;

import java.util.List;

public interface  ArticleService {
    ApiResponse<ArticleDto> createArticle(CreateArticleRequest articleRequest);

    ApiResponse<ArticleDto> addPublishersToArticle(long id, ArticlePublishersRequest publishersRequest) throws CustomException;

    ApiResponse<ArticleDto> addAuthorsToArticle(Long id, ArticleAuthorsRequest authorsRequest) throws CustomException;

    ApiResponse<ArticleDto> removeAuthorFromArticle(Long id, RemoveAuthorRequest removeAuthorRequest) throws CustomException;

    ApiResponse<ArticleDto> publishArticle(Long id) throws CustomException;

    ApiResponse<ArticleDto> unpublishArticle(Long id) throws CustomException;

    ApiResponse<Void> deleteArticle(Long id) throws CustomException;

    ApiResponse<ArticleDto> updateArticle(Long id, UpdateArticleRequest updateArticleRequest) throws CustomException;

    ApiResponse<List<ArticleDto>> findAllArticle(SearchArticleRequest searchArticleRequest);
}
