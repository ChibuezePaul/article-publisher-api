package io.isoft.article.publisher.models.request;

import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

public record SearchArticleRequest(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String author,
        @RequestParam(required = false) String publisher,
        @RequestParam(required = false) LocalDate datePublished
) {
}
