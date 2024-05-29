package com.example.movie_studio.studio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface StudioRepository extends JpaRepository<Studio, Integer>, JpaSpecificationExecutor<Studio> {
    @Query("""
            select  s from Studio as s where s.id=?1 and s.deletedAt is null
            """)
    Optional<Studio> findStudioById(Integer id);

    @Query("""
            select s from Studio as s
            """)
    List<Studio> findAllStudios();

    Page<Studio> findAllByDeletedAtIsNull(Pageable pageable);

    @Query("""
            select s from Studio as s where s.id in (?1)
            """)
    List<Studio> getStudiosSomeIds(List<Integer> list);

    @Query(value = "select * from studio offset :p * :s limit :s",nativeQuery = true)
    List<Studio> getStudioPagesByList(@Param("p") Integer page, @Param("s") Integer size);
}
