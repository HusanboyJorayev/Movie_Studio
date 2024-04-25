package com.example.movie_studio.actor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    @Query("""
            select a from Actor as a where a.id=?1 and a.deletedAt is null\s
            """)
    Optional<Actor> findActorByIdAndDeletedAtIsNull(@Param("id") Long id);

    @Query("""
            select a from Actor as a
            """)
    List<Actor> findAllActors();

    @Query("""
            delete from Actor as a where a.codes=?1
            """)
    @Modifying
    @Transactional
    Integer deleteActorByQueryByCode(@Param("code") Integer code);

    @Query("""
                   select new com.example.movie_studio.actor.SomeActorFields(a.name,a.codes,a.gender,a.nationality,a.yearOfBirth) from Actor as a
            """)
    List<SomeActorFields> someActorFields();
}
