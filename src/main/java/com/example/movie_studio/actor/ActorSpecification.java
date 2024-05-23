package com.example.movie_studio.actor;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
@NoArgsConstructor
public class ActorSpecification implements Specification<Actor> {
    private Long id;
    private String name;
    private Integer codes;
    private String gender;
    private String nationality;
    private Integer yearOfBirth;

    @Override
    public Predicate toPredicate(@NonNull Root<Actor> root,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (id != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), id));
        }
        if (name != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }
        if (codes != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("codes"), codes));
        }
        if (gender != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("gender"), "%" + gender + "%"));
        }
        if (nationality != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("nationality"), "%" + nationality + "%"));
        }
        if (yearOfBirth != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("yearOfBirth"), yearOfBirth));
        }
        return predicate;
    }
}
