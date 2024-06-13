package io.isoft.article.publisher.models.dto;

import io.isoft.article.publisher.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class ArticleDto {
    private String title;
    private String content;
    private Set<String> authors;
    private Set<String> publishers;
    private LocalDate datePublished;
    private Status status;
}
