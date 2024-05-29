package com.example.movie_studio.director;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface DirectorRepository extends JpaRepository<Director, Integer>, JpaSpecificationExecutor<Director> {
    @Query("""
            select d from Director as d where d.id=?1 and d.deletedAt is null
            """)
    Optional<Director> getDirectorById(Integer id);

    @Query("""
            select d from Director d
            """)
    List<Director> getAllDirectors();

    Page<Director> findAllByDeletedAtIsNull(Pageable pageable);

    @Query("""
            select d from Director as d where d.id in (?1)
            """)
    List<Director> getDirectorsBySomeIds(List<Integer> list);
}
