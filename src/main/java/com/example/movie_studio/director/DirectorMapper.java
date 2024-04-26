package com.example.movie_studio.director;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DirectorMapper {
    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "createdAt")
    @Mapping(ignore = true, target = "updatedAt")
    @Mapping(ignore = true, target = "deletedAt")
    Director toEntity(DirectorDto dto);

    DirectorDto toDto(Director director);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Director director, DirectorDto dto);
}
