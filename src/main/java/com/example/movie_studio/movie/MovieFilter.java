package com.example.movie_studio.movie;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public class MovieFilter implements Specification<Movie> {

    private Set<Integer> ids;
    private Integer directorId;
    private Integer studioId;
    private String name;
    private String countryOfRelease;
    private String language;
    private String filmingLocation;
    private Integer yearOfRelease;
    private String category;

    @Override
    public Predicate toPredicate(@NonNull Root<Movie> root,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();
        if (ids != null) {
            predicate = criteriaBuilder.and(predicate, root.get("id").in(ids));
        }
        if (directorId != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("directorId"), directorId));
        }
        if (studioId != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("studioId"), studioId));
        }
        if (name != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (countryOfRelease != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("countryOfRelease")), "%" + countryOfRelease.toLowerCase() + "%"));
        }
        if (language != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("language")), "%" + language.toLowerCase() + "%"));
        }
        if (filmingLocation != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("filmingLocation")), "%" + filmingLocation.toLowerCase() + "%"));
        }
        if (yearOfRelease != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("yearOfRelease"), yearOfRelease));
        }
        if (category != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("category")), "%" + category.toLowerCase() + "%"));
        }
        return predicate;
    }
}
