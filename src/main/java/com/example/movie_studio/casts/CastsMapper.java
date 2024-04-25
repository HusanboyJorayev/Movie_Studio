package com.example.movie_studio.casts;

import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = Collectors.class)
public interface CastsMapper {

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "createdAt")
    @Mapping(ignore = true, target = "updatedAt")
    @Mapping(ignore = true, target = "deletedAt")
    Casts toEntity(CastsDto dto);

    CastsDto toDto(Casts casts);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Casts casts, CastsDto dto);

}
