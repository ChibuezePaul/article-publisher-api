package io.isoft.article.publisher.service.impl;

import io.isoft.article.publisher.controller.CreateArticleRequest;
import io.isoft.article.publisher.enums.Status;
import io.isoft.article.publisher.exception.CustomException;
import io.isoft.article.publisher.models.dto.ApiResponse;
import io.isoft.article.publisher.models.dto.ArticleDto;
import io.isoft.article.publisher.models.entity.Article;
import io.isoft.article.publisher.models.request.ArticleAuthorsRequest;
import io.isoft.article.publisher.models.request.ArticlePublishersRequest;
import io.isoft.article.publisher.models.request.RemoveAuthorRequest;
import io.isoft.article.publisher.models.request.SearchArticleRequest;
import io.isoft.article.publisher.models.request.UpdateArticleRequest;
import io.isoft.article.publisher.repository.ArticleRepository;
import io.isoft.article.publisher.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public ApiResponse createArticle(CreateArticleRequest articleRequest) {
        Article article = Article.builder()
                .title(articleRequest.title())
                .content(articleRequest.content())
                .authors(articleRequest.authors())
                .status(Status.CREATED)
                .build();

        articleRepository.save(article);

        return new ApiResponse("Article Created Successfully", toDto(article));
    }

    @Override
    public ApiResponse addPublishersToArticle(long id, ArticlePublishersRequest publishersRequest) throws CustomException {
        Article article = findArticleById(id);

        if (Status.PUBLISHED.equals(article.getStatus())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Article Is Already Published");
        }

        article.getPublishers().addAll(publishersRequest.publishers());
        articleRepository.save(article);

        return new ApiResponse("Publishers Successfully Added To Article", toDto(article));
    }

    @Override
    public ApiResponse addAuthorsToArticle(Long id, ArticleAuthorsRequest authorsRequest) throws CustomException {
        Article article = findArticleById(id);

        if (!Status.CREATED.equals(article.getStatus())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Authors Can Only Be Added To Articles That Has Not Been Published");
        }

        article.getAuthors().addAll(authorsRequest.authors());
        articleRepository.save(article);

        return new ApiResponse("Authors Successfully Added To Article", toDto(article));
    }

    @Override
    public ApiResponse removeAuthorFromArticle(Long id, RemoveAuthorRequest removeAuthorRequest) throws CustomException {
        Article article = findArticleById(id);
        String authorToBeRemoved = removeAuthorRequest.author().trim();
        if (!article.getAuthors().contains(authorToBeRemoved)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Specified Author Was Not Found For Article");
        }
        article.getAuthors().removeIf(author -> author.trim().equalsIgnoreCase(authorToBeRemoved));
        articleRepository.save(article);

        return new ApiResponse("Author Successfully Removed From Article", toDto(article));
    }

    @Override
    public ApiResponse publishArticle(Long id) throws CustomException {
        Article article = findArticleById(id);

        if (Status.PUBLISHED.equals(article.getStatus())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Article Is Already Published");
        }

        if (article.getPublishers().isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Kindly Add Publishers To Article");
        }

        if (article.getAuthors().isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Kindly Add Authors To Article");
        }

        article.setDatePublished(LocalDate.now());
        article.setStatus(Status.PUBLISHED);
        articleRepository.save(article);

        return new ApiResponse("Article Successfully Published", toDto(article));
    }

    @Override
    public ApiResponse unpublishArticle(Long id) throws CustomException {
        Article article = findArticleById(id);

        if (!Status.PUBLISHED.equals(article.getStatus())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Article Has Not Been Published");
        }
        article.setStatus(Status.UNPUBLISHED);
        articleRepository.save(article);
        return new ApiResponse("Article Successfully Unpublished", toDto(article));
    }

    @Override
    public ApiResponse deleteArticle(Long id) throws CustomException {
        Article article = findArticleById(id);

        if (Status.PUBLISHED.equals(article.getStatus())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Article Is Published");
        }

        articleRepository.delete(article);
        return new ApiResponse("Article Successfully Deleted", null);
    }

    @Override
    public ApiResponse updateArticle(Long id, UpdateArticleRequest updateArticleRequest) throws CustomException {
        Article article = findArticleById(id);

        if (Status.PUBLISHED.equals(article.getStatus())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Article Is Published");
        }

        article.setTitle(updateArticleRequest.title());
        article.setContent(updateArticleRequest.content());

        if (!updateArticleRequest.authors().isEmpty()) {
            article.getAuthors().addAll(updateArticleRequest.authors());
        }

        if (!updateArticleRequest.publishers().isEmpty()) {
            article.getPublishers().addAll(updateArticleRequest.publishers());
        }

        articleRepository.save(article);
        return new ApiResponse("Article Successfully Updated", null);
    }

    @Override
    public ApiResponse findAllArticle(SearchArticleRequest searchArticleRequest) {
//        Example<Article> of = Example.of(new Article(), ExampleMatcher.matching()
//                .withIgnoreCase()
//                        .withMatcher(searchArticleRequest.author())
//                .withNullHandler(ExampleMatcher.NullHandler.IGNORE));
        PageRequest pageRequest = PageRequest.of(searchArticleRequest.page(), searchArticleRequest.size());

        Page<Article> articles = articleRepository
                .findArticleByDatePublishedAndAuthorsContainsIgnoreCaseAndPublishersContainsIgnoreCase(
                searchArticleRequest.datePublished(),
                searchArticleRequest.author(),
                searchArticleRequest.publisher(),
                pageRequest
        );
        return new ApiResponse("Article Successfully Retrieved", articles);
    }

    private Article findArticleById(Long id) throws CustomException {
        return articleRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Article Does Not Exist"));
    }

    private static ArticleDto toDto(Article article) {
        var articleDto = new ArticleDto();
        BeanUtils.copyProperties(article, articleDto);
        return articleDto;
    }
}
