package io.isoft.article.publisher.controller;

import io.isoft.article.publisher.exception.CustomException;
import io.isoft.article.publisher.models.dto.ApiResponse;
import io.isoft.article.publisher.models.request.ArticleAuthorsRequest;
import io.isoft.article.publisher.models.request.ArticlePublishersRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    ResponseEntity<ApiResponse> createArticle(@Valid @RequestBody CreateArticleRequest articleRequest) {
        ApiResponse newArticle = articleService.createArticle(articleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newArticle);
    }

    @GetMapping
    ResponseEntity<ApiResponse> findAllArticle(SearchArticleRequest searchArticleRequest) {
        ApiResponse articles = articleService.findAllArticle(searchArticleRequest);
        return ResponseEntity.ok(articles);
    }

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse> updateArticle(
            @PathVariable Long id,
            @Valid @RequestBody UpdateArticleRequest updateArticleRequest
            ) throws CustomException {
        ApiResponse updatedArticle = articleService.updateArticle(id, updateArticleRequest);
        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse> deleteArticle(
            @PathVariable Long id
    ) throws CustomException {
        ApiResponse updatedArticle = articleService.deleteArticle(id);
        return ResponseEntity.ok(updatedArticle);
    }

    @PutMapping("/{id}/publish")
    ResponseEntity<ApiResponse> publishArticle(
            @PathVariable Long id
    ) throws CustomException {
        ApiResponse updatedArticle = articleService.publishArticle(id);
        return ResponseEntity.ok(updatedArticle);
    }

    @PutMapping("/{id}/unpublish")
    ResponseEntity<ApiResponse> unpublishArticle(
            @PathVariable Long id
    ) throws CustomException {
        ApiResponse updatedArticle = articleService.unpublishArticle(id);
        return ResponseEntity.ok(updatedArticle);
    }

    @PutMapping("/{id}/publishers")
    ResponseEntity<ApiResponse> addPublishersToArticle(
            @PathVariable Long id,
            @Valid @RequestBody ArticlePublishersRequest publishersRequest
    ) throws CustomException {
        ApiResponse updatedArticle = articleService.addPublishersToArticle(id, publishersRequest);
        return ResponseEntity.ok(updatedArticle);
    }

    @PutMapping("/{id}/authors/add")
    ResponseEntity<ApiResponse> addAuthorsToArticle(
            @PathVariable Long id,
            @Valid @RequestBody ArticleAuthorsRequest authorsRequest
    ) throws CustomException {
        ApiResponse updatedArticle = articleService.addAuthorsToArticle(id, authorsRequest);
        return ResponseEntity.ok(updatedArticle);
    }

    @PutMapping("/{id}/authors/remove")
    ResponseEntity<ApiResponse> removeAuthorFromArticle(
            @PathVariable Long id,
            @Valid @RequestBody RemoveAuthorRequest removeAuthorRequest
    ) throws CustomException {
        ApiResponse updatedArticle = articleService.removeAuthorFromArticle(id, removeAuthorRequest);
        return ResponseEntity.ok(updatedArticle);
    }
}
