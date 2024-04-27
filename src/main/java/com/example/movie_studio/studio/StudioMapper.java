package com.example.movie_studio.studio;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface StudioMapper {

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "createdAt")
    @Mapping(ignore = true, target = "updatedAt")
    @Mapping(ignore = true, target = "deletedAt")
    @Mapping(ignore = true, target = "movies")
    Studio toEntity(StudioDto dto);

    @Mapping(ignore = true, target = "movies")
    StudioDto toDto(Studio studio);

    StudioDto toDtoWithMovie(Studio studio);

    @Mapping(ignore = true, target = "movies")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Studio studio, StudioDto dto);
}
