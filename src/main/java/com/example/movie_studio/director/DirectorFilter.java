package com.example.movie_studio.director;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor
@AllArgsConstructor
public class DirectorFilter implements Specification<Director> {
    private String name;
    private String gender;
    private String placeBirth;
    private String country;
    private Integer yearBirth;

    @Override
    public Predicate toPredicate(@NonNull Root<Director> root,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();
        if (name != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (gender != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("gender")), "%" + gender.toLowerCase() + "%"));
        }
        if (placeBirth != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("placeBirth")), "%" + placeBirth.toLowerCase() + "%"));
        }
        if (country != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("country"), "%" + country.toLowerCase() + "%"));
        }
        if (yearBirth != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("yearBirth"), yearBirth));
        }
        return predicate;
    }
}
