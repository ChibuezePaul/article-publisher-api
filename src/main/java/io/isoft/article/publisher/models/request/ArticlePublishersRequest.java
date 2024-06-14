package io.isoft.article.publisher.models.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record ArticlePublishersRequest(@NotEmpty(message = "publishers can not be empty") Set<String> publishers) {
}
