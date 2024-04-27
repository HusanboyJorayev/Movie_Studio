package com.example.movie_studio.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query("""
            select m from Movie m where m.id=?1 and m.deletedAt is null
            """)
    Optional<Movie> findByIdAndDeletedAtNull(Integer id);

    @Query("""
            select m from Movie m
            """)
    List<Movie> findAllMovies();

    @Query("""
            select m from Movie as m where m.id in(?1)
            """)
    List<Movie> getMoviesBySomeIds(List<Integer> list);


    @Query("""
            select m from Movie as m where m.studioId=?1
            """)
    List<Movie> findAllMoviesByStudioId(Integer id);


    @Query("""
            select m from Movie as m where m.directorId=?1
            """)
    List<Movie> findAllMoviesByDirectorId(Integer id);

    Page<Movie> findAllByDeletedAtIsNull(Pageable pageable);
}
