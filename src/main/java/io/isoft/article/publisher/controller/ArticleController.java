package io.isoft.article.publisher.controller;

import io.isoft.article.publisher.exception.CustomException;
import io.isoft.article.publisher.models.dto.ApiResponse;
import io.isoft.article.publisher.models.dto.ArticleDto;
import io.isoft.article.publisher.models.request.ArticleAuthorsRequest;
import io.isoft.article.publisher.models.request.ArticlePublishersRequest;
import io.isoft.article.publisher.models.request.CreateArticleRequest;
import io.isoft.article.publisher.models.request.RemoveAuthorRequest;
import io.isoft.article.publisher.models.request.SearchArticleRequest;
import io.isoft.article.publisher.models.request.UpdateArticleRequest;
import io.isoft.article.publisher.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    ResponseEntity<ApiResponse<ArticleDto>> createArticle(@Valid @RequestBody CreateArticleRequest articleRequest) {
        ApiResponse<ArticleDto> newArticle = articleService.createArticle(articleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newArticle);
    }

    @GetMapping
    ResponseEntity<ApiResponse<List<ArticleDto>>> findAllArticle(SearchArticleRequest searchArticleRequest) {
        ApiResponse<List<ArticleDto>> articles = articleService.findAllArticle(searchArticleRequest);
        return ResponseEntity.ok(articles);
    }

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<ArticleDto>> updateArticle(
            @PathVariable Long id,
            @Valid @RequestBody UpdateArticleRequest updateArticleRequest
            ) throws CustomException {
        ApiResponse<ArticleDto> updatedArticle = articleService.updateArticle(id, updateArticleRequest);
        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<Void>> deleteArticle(
            @PathVariable Long id
    ) throws CustomException {
        ApiResponse<Void> updatedArticle = articleService.deleteArticle(id);
        return ResponseEntity.ok(updatedArticle);
    }

    @PutMapping("/{id}/publish")
    ResponseEntity<ApiResponse<ArticleDto>> publishArticle(
            @PathVariable Long id
    ) throws CustomException {
        ApiResponse<ArticleDto> updatedArticle = articleService.publishArticle(id);
        return ResponseEntity.ok(updatedArticle);
    }

    @PutMapping("/{id}/unpublish")
    ResponseEntity<ApiResponse<ArticleDto>> unpublishArticle(
            @PathVariable Long id
    ) throws CustomException {
        ApiResponse<ArticleDto> updatedArticle = articleService.unpublishArticle(id);
        return ResponseEntity.ok(updatedArticle);
    }

    @PutMapping("/{id}/publishers")
    ResponseEntity<ApiResponse<ArticleDto>> addPublishersToArticle(
            @PathVariable Long id,
            @Valid @RequestBody ArticlePublishersRequest publishersRequest
    ) throws CustomException {
        ApiResponse<ArticleDto> updatedArticle = articleService.addPublishersToArticle(id, publishersRequest);
        return ResponseEntity.ok(updatedArticle);
    }

    @PutMapping("/{id}/authors/add")
    ResponseEntity<ApiResponse<ArticleDto>> addAuthorsToArticle(
            @PathVariable Long id,
            @Valid @RequestBody ArticleAuthorsRequest authorsRequest
    ) throws CustomException {
        ApiResponse<ArticleDto> updatedArticle = articleService.addAuthorsToArticle(id, authorsRequest);
        return ResponseEntity.ok(updatedArticle);
    }

    @PutMapping("/{id}/authors/remove")
    ResponseEntity<ApiResponse<ArticleDto>> removeAuthorFromArticle(
            @PathVariable Long id,
            @Valid @RequestBody RemoveAuthorRequest removeAuthorRequest
    ) throws CustomException {
        ApiResponse<ArticleDto> updatedArticle = articleService.removeAuthorFromArticle(id, removeAuthorRequest);
        return ResponseEntity.ok(updatedArticle);
    }
}
