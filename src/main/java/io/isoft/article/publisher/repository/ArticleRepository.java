package io.isoft.article.publisher.repository;

import io.isoft.article.publisher.models.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ArticleRepository extends JpaRepository<Article, Long> {
//    @Query("select * from articles where (:author is null or authors contains )")
//    Page<Article> findArticleBySearchRequest(String author, String publisher, LocalDate datePublished, Pageable pageable);

    Page<Article> findArticleByDatePublishedAndAuthorsContainsIgnoreCaseAndPublishersContainsIgnoreCase(LocalDate datePublished, String author, String publisher, Pageable pageable);
}