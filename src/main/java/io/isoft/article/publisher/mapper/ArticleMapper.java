package io.isoft.article.publisher.mapper;

import io.isoft.article.publisher.models.dto.ArticleDto;
import io.isoft.article.publisher.models.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArticleMapper {

    ArticleDto toDto(Article user);
}
