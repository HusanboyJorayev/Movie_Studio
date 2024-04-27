package com.example.movie_studio.studio;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface StudioMapper {

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "createdAt")
    @Mapping(ignore = true, target = "updatedAt")
    @Mapping(ignore = true, target = "deletedAt")
    Studio toEntity(StudioDto dto);

    StudioDto toDto(Studio studio);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Studio studio, StudioDto dto);
}
