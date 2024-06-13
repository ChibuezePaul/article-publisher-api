package io.isoft.article.publisher.models.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record UpdateArticleRequest(@NotBlank(message = "title is required") String title,
                                   @NotBlank(message = "title is required") String content,
                                   Set<String> authors,
                                   Set<String> publishers) {
}
