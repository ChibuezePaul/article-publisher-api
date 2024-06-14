package io.isoft.article.publisher.repository;

import io.isoft.article.publisher.models.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("""
            select a from articles a where (:author is null or :author member of a.authors)
            and (:publisher is null or :publisher member of a.publishers)
            and  (:datePublished = a.datePublished)
            """)
    List<Article> findArticleBySearchRequest(String author, String publisher, LocalDate datePublished, Pageable pageable);

    @Query("""
            select a from articles a where (:author is null or :author member of a.authors)
            and (:publisher is null or :publisher member of a.publishers)
            """)
    List<Article> findArticleBySearchRequest(String author, String publisher, Pageable pageable);
}