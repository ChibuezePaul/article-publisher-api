package io.isoft.article.publisher.models.entity;

import io.isoft.article.publisher.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "articles")
public class Article extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @ElementCollection
    @Column(name = "authors", length = 100)
    private Set<String> authors = HashSet.newHashSet(0);

    @ElementCollection
    @Column(name = "publishers", length = 100)
    private Set<String> publishers = HashSet.newHashSet(0);

    @Column(name = "date_published")
    private LocalDate datePublished;

    @Column(name = "status", length = 50)
    @Enumerated(EnumType.STRING)
    private Status status;
}
