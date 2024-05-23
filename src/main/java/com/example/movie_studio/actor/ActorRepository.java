package com.example.movie_studio.actor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Query("""
            select a
            from Actor as a
            where a.id = ?1
               or a.name = ?2 or a.name like concat(substring(?2,0,2),'','%')
               or a.codes = ?3
               or a.gender = ?4 or a.gender like concat(substring(?4,0,2),'','%')
               or a.nationality = ?5 or  a.nationality like concat(substring(?5,0,2),'','%')
               or a.yearOfBirth = ?6""")
    List<Actor> getAllFilterActors(Long id, String name, Integer codes,
                                   String gender, String nationality, Integer yearOfBirth);

    @Query("""
            select a from Actor as a where a.id in (?1)
            """)
    List<Actor> getManyActorsById(Set<Long> id);
}
