package com.example.movie_studio.casts;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor
@AllArgsConstructor
public class CastsFilter implements Specification<Casts> {
    private Integer id;
    private Integer movieId;
    private Long actorId;
    private String roleType;


    @Override
    public Predicate toPredicate(@NonNull Root<Casts> root,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();
        if (id != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), id));
        }
        if (movieId != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("movieId"), movieId));
        }
        if (actorId != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("actorId"), actorId));
        }
        if (roleType != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("roleType")), "%" + roleType + "%"));
        }
        return predicate;
    }
}
