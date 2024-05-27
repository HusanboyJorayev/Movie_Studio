package com.example.movie_studio.filter;

import com.example.movie_studio.actor.Actor;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
@NoArgsConstructor
public class ActorAndCastsSpecification implements Specification<Actor> {
    private Long id;
    private String name;
    private Integer codes;
    private String gender;
    private String nationality;
    private Integer yearOfBirth;


    /*private Long actorId;
    private String roleType;*/

    @Override
    public Predicate toPredicate(@NonNull Root<Actor> root,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (id != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), id));
        if (name != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        if (gender != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("gender")), "%" + gender.toLowerCase() + "%"));
        if (codes != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("codes"), codes));
        if (nationality != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("nationality")), "%" + nationality.toLowerCase() + "%"));
        if (yearOfBirth != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("yearOfBirth"), yearOfBirth));/*
        if (actorId != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("actorId"), actorId));
        if (roleType != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("roleType")), "%" + roleType.toLowerCase() + "%"));*/

        return predicate;
    }
}
