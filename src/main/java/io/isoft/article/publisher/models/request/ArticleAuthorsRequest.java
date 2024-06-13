package io.isoft.article.publisher.models.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record ArticleAuthorsRequest(@NotEmpty(message = "authors can not be empty") Set<String> authors) {
}
