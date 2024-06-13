package io.isoft.article.publisher.controller;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record CreateArticleRequest(@NotBlank(message = "title is required") String title,
                                   @NotBlank(message = "title is required") String content,
                                   Set<String> authors) {
}
