package com.example.movie_studio.actor;

import com.example.movie_studio.casts.CastsMapper;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface ActorMapper {

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "createdAt")
    @Mapping(ignore = true, target = "updatedAt")
    @Mapping(ignore = true, target = "deletedAt")
    @Mapping(ignore = true, target = "casts")
    Actor toEntity(ActorDto dto);

    @Mapping(ignore = true, target = "casts")
    ActorDto toDto(Actor actor);

    ActorDto toDtoWithCasts(Actor actor);

    List<SomeActorFields> toActor(List<Actor> actor);

    @Mapping(ignore = true, target = "casts")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Actor actor, ActorDto dto);

}
