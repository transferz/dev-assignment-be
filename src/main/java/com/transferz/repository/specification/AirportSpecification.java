package com.transferz.repository.specification;


import com.transferz.dao.Airport;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Map;

public class AirportSpecification {

    private static final String CODE = "code";
    private static final String NAME = "name";

    public static Specification<Airport> filterAirport(Map<String, String> parameters) {
        return (root, query, criteriaBuilder) -> {

            Predicate predicate = criteriaBuilder.conjunction();

            if (parameters == null) {
                return predicate;
            }

            if (parameters.containsKey(CODE)) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get(CODE), parameters.get(CODE)));
            }

            if (parameters.containsKey(NAME)) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get(NAME), parameters.get(NAME)));
            }

            return predicate;
        };
    }
}
