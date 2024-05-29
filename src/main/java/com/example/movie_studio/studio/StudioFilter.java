package com.example.movie_studio.studio;

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
public class StudioFilter implements Specification<Studio> {
    private String companyName;
    private String city;
    private Integer founded;
    private String companyType;

    @Override
    public Predicate toPredicate(@NonNull Root<Studio> root,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();
        if (companyName != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("companyName")), "%" + companyName.toLowerCase() + "%"));
        }
        if (city != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), "%" + city.toLowerCase() + "%"));
        }
        if (founded != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("founded"), founded));
        }
        if (companyType != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("companyType")), "%" + companyType.toLowerCase() + "%"));
        }
        return predicate;
    }
}
