package com.example.movie_studio.director;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DirectorMapper {
    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "createdAt")
    @Mapping(ignore = true, target = "updatedAt")
    @Mapping(ignore = true, target = "deletedAt")
    @Mapping(ignore = true, target = "movies")
    Director toEntity(DirectorDto dto);

    @Mapping(ignore = true, target = "movies")
    DirectorDto toDto(Director director);

    DirectorDto toDtoWithMovie(Director director);


    @Mapping(ignore = true, target = "movies")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Director director, DirectorDto dto);
}
