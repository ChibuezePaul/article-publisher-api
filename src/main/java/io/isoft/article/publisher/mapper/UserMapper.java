package io.isoft.article.publisher.mapper;

import io.isoft.article.publisher.models.dto.UserDto;
import io.isoft.article.publisher.models.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDto toDto(User user);
}
