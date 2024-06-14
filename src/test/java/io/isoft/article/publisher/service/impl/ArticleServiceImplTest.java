package io.isoft.article.publisher.service.impl;

import io.isoft.article.publisher.enums.Status;
import io.isoft.article.publisher.exception.CustomException;
import io.isoft.article.publisher.models.dto.ApiResponse;
import io.isoft.article.publisher.models.dto.ArticleDto;
import io.isoft.article.publisher.models.entity.Article;
import io.isoft.article.publisher.models.request.ArticleAuthorsRequest;
import io.isoft.article.publisher.models.request.ArticlePublishersRequest;
import io.isoft.article.publisher.models.request.CreateArticleRequest;
import io.isoft.article.publisher.models.request.RemoveAuthorRequest;
import io.isoft.article.publisher.models.request.SearchArticleRequest;
import io.isoft.article.publisher.models.request.UpdateArticleRequest;
import io.isoft.article.publisher.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleServiceImpl underTest;

    @Test
    void createArticle_shouldCreateArticleSuccessfully_whenAllParametersArePassed() {
        var request = new CreateArticleRequest("title", "content", Collections.singleton("author1"));
        when(articleRepository.save(any(Article.class))).thenReturn(new Article());

        ApiResponse<ArticleDto> newArticle = underTest.createArticle(request);

        assertNotNull(newArticle);
        assertNotNull(newArticle.data());
    }

    @Test
    void addPublishersToArticle_shouldThrowException_whenArticleIsPublished() {
        long id = 1L;

        var article = new Article();
        article.setStatus(Status.PUBLISHED);
        ArticlePublishersRequest publishersRequest = new ArticlePublishersRequest(Collections.singleton("publisher1"));

        when(articleRepository.findById(id)).thenReturn(Optional.of(article));

        assertThrows(CustomException.class, () -> underTest.addPublishersToArticle(id, publishersRequest));
    }

    @Test
    void addPublishersToArticle_shouldAddPublisherToArticle_whenArticleIsNotPublished() throws CustomException {
        long id = 1L;

        ArticlePublishersRequest publishersRequest = new ArticlePublishersRequest(Collections.singleton("publisher1"));

        when(articleRepository.findById(id)).thenReturn(Optional.of(new Article()));

        ApiResponse<ArticleDto> response = underTest.addPublishersToArticle(id, publishersRequest);

        assertNotNull(response);
        assertNotNull(response.data());
        assertEquals(1, response.data().getPublishers().size());
    }

    @Test
    void addAuthorsToArticle_shouldThrowException_whenArticleIsNotNewlyCreated() {
        long id = 1L;

        var article = new Article();
        article.setStatus(Status.PUBLISHED);
        ArticleAuthorsRequest authorsRequest = new ArticleAuthorsRequest(Collections.singleton("author1"));

        when(articleRepository.findById(id)).thenReturn(Optional.of(article));

        assertThrows(CustomException.class, () -> underTest.addAuthorsToArticle(id, authorsRequest));
    }

    @Test
    void addAuthorsToArticle_shouldThrowException_whenArticleIsNewlyCreated() throws CustomException {
        long id = 1L;

        ArticleAuthorsRequest authorsRequest = new ArticleAuthorsRequest(Collections.singleton("author1"));

        var article = new Article();
        article.setStatus(Status.CREATED);

        when(articleRepository.findById(id)).thenReturn(Optional.of(article));

        ApiResponse<ArticleDto> response = underTest.addAuthorsToArticle(id, authorsRequest);

        assertNotNull(response);
        assertNotNull(response.data());
        assertEquals(1, response.data().getAuthors().size());
    }

    @Test
    void removeAuthorFromArticle_shouldThrowException_whenAuthorIsNotPartOfArticleAuthors() {
        long id = 1L;

        var request = new RemoveAuthorRequest("author1");

        when(articleRepository.findById(id)).thenReturn(Optional.of(new Article()));

        assertThrows(CustomException.class, () -> underTest.removeAuthorFromArticle(id, request));
    }

    @Test
    void removeAuthorFromArticle_shouldRemoveAuthor_whenAuthorIsPartOfArticleAuthors() throws CustomException {
        long id = 1L;

        String author1 = "author1";
        var request = new RemoveAuthorRequest(author1);

        Article article = new Article();
        HashSet<String> authors = new HashSet<>();
        authors.add(author1);
        article.setAuthors(authors);

        when(articleRepository.findById(id)).thenReturn(Optional.of(article));

        ApiResponse<ArticleDto> response = underTest.removeAuthorFromArticle(id, request);

        assertNotNull(response);
        assertNotNull(response.data());
        assertTrue(response.data().getAuthors().isEmpty());
    }

    @Test
    void publishArticle_shouldThrowException_whenArticleIsPublished() {
        long id = 1L;

        Article article = new Article();
        article.setStatus(Status.PUBLISHED);

        when(articleRepository.findById(id)).thenReturn(Optional.of(article));

        assertThrows(CustomException.class, () -> underTest.publishArticle(id));
    }

    @Test
    void publishArticle_shouldThrowException_whenArticleHasNoPublisher() {
        long id = 1L;

        Article article = new Article();
        article.setStatus(Status.CREATED);

        when(articleRepository.findById(id)).thenReturn(Optional.of(article));

        assertThrows(CustomException.class, () -> underTest.publishArticle(id));
    }

    @Test
    void publishArticle_shouldThrowException_whenArticleHasNoAuthors() {
        long id = 1L;

        Article article = new Article();
        article.setStatus(Status.CREATED);
        article.setPublishers(Collections.singleton("publisher1"));

        when(articleRepository.findById(id)).thenReturn(Optional.of(article));

        assertThrows(CustomException.class, () -> underTest.publishArticle(id));
    }

    @Test
    void publishArticle_shouldPublish_whenArticleHasMeetsAllRequirements() throws CustomException {
        long id = 1L;

        Article article = new Article();
        article.setStatus(Status.CREATED);
        article.setPublishers(Collections.singleton("publisher1"));
        article.setAuthors(Collections.singleton("author1"));

        when(articleRepository.findById(id)).thenReturn(Optional.of(article));

        ApiResponse<ArticleDto> response = underTest.publishArticle(id);

        assertNotNull(response);
        assertNotNull(response.data());
        assertEquals(1, response.data().getAuthors().size());
        assertEquals(1, response.data().getPublishers().size());
    }

    @Test
    void unpublishArticle_shouldThrowException_whenArticleHasNotBeenPublished() {
        long id = 1L;

        Article article = new Article();
        article.setStatus(Status.CREATED);

        when(articleRepository.findById(id)).thenReturn(Optional.of(article));

        assertThrows(CustomException.class, () -> underTest.unpublishArticle(id));
    }

    @Test
    void unpublishArticle_shouldUnpublish_whenArticleHasBeenPublished() throws CustomException {
        long id = 1L;

        Article article = new Article();
        article.setStatus(Status.PUBLISHED);

        when(articleRepository.findById(id)).thenReturn(Optional.of(article));

        ApiResponse<ArticleDto> response = underTest.unpublishArticle(id);

        assertNotNull(response);
        assertNotNull(response.data());
    }

    @Test
    void deleteArticle_shouldThrowException_whenArticleHasBeenPublished() {
        long id = 1L;

        Article article = new Article();
        article.setStatus(Status.PUBLISHED);

        when(articleRepository.findById(id)).thenReturn(Optional.of(article));

        assertThrows(CustomException.class, () -> underTest.deleteArticle(id));
    }

    @Test
    void deleteArticle_shouldDeleteArticle_whenArticleIsNotPublished() throws CustomException {
        long id = 1L;

        Article article = new Article();
        article.setStatus(Status.UNPUBLISHED);

        when(articleRepository.findById(id)).thenReturn(Optional.of(article));

        ApiResponse<Void> response = underTest.deleteArticle(id);

        assertNotNull(response);
        assertNull(response.data());
    }

    @Test
    void updateArticle_shouldThrowException_whenArticleHasBeenPublished() {
        long id = 1L;

        Article article = new Article();
        article.setStatus(Status.PUBLISHED);

        var request = new UpdateArticleRequest("title", "content", Collections.emptySet(), Collections.emptySet());

        when(articleRepository.findById(id)).thenReturn(Optional.of(article));

        assertThrows(CustomException.class, () -> underTest.updateArticle(id, request));
    }

    @Test
    void updateArticle_shouldThrowException_whenArticleIsNotPublished() throws CustomException {
        long id = 1L;

        Article article = new Article();
        article.setStatus(Status.UNPUBLISHED);

        var request = new UpdateArticleRequest("title", "content", Collections.emptySet(), Collections.emptySet());

        when(articleRepository.findById(id)).thenReturn(Optional.of(article));

        ApiResponse<ArticleDto> response = underTest.updateArticle(id, request);

        assertNotNull(response);
        assertNotNull(response.data());
    }

    @Test
    void findAllArticle_shouldRetrieveArticles_whenDatePublishedIsNotPassed() {
        var request = new SearchArticleRequest(0, 5, "author1", "publisher1", LocalDate.now());

        when(articleRepository.findArticleBySearchRequest(any(), any(), any(), any()))
                .thenReturn(Collections.singletonList(new Article()));

        ApiResponse<List<ArticleDto>> articles = underTest.findAllArticle(request);

        assertNotNull(articles);
        assertNotNull(articles.data());
        assertEquals(1, articles.data().size());
        verify(articleRepository, times(0)).findArticleBySearchRequest(any(), any(), any());

    }

    @Test
    void findAllArticle_shouldRetrieveArticles_whenDatePublishedIsPassed() {
        var request = new SearchArticleRequest(0, 5, "author1", "publisher1", null);

        when(articleRepository.findArticleBySearchRequest(any(), any(), any()))
                .thenReturn(Collections.singletonList(new Article()));

        ApiResponse<List<ArticleDto>> articles = underTest.findAllArticle(request);

        assertNotNull(articles);
        assertNotNull(articles.data());
        assertEquals(1, articles.data().size());
        verify(articleRepository, times(0)).findArticleBySearchRequest(any(), any(), any(), any());

    }
}