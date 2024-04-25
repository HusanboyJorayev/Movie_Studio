package com.example.movie_studio.casts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CastsRepository extends JpaRepository<Casts, Integer> {
    @Query("""
            select  c from Casts as c where c.id=?1
            """)
    Optional<Casts> findByCastsById(Integer id);

    @Query("""
            select  c from Casts as c where c.actorId=?1
            """)
    List<Casts> findByCastsByActorId(Long id);

    @Query("""
            select c from Casts as c
            """)
    List<Casts> findAllCasts();

}
