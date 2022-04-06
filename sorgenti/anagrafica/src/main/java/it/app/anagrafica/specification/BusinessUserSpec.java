package it.app.anagrafica.specification;

import it.app.anagrafica.dto.BusinessUserFilterDTO;
import it.app.anagrafica.entity.BusinessUser;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class BusinessUserSpec {
    public static Specification<BusinessUser> getUsersByFilter(BusinessUserFilterDTO filter) {
        return (root, query, criteriBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getLastName() != null) {
                Predicate lastNamePredicate = criteriBuilder.equal(root.get("lastName"), filter.getLastName());
                predicates.add(lastNamePredicate);
            }

            if (filter.getFiscalCode() != null) {
                Predicate fiscalCodePredicate = criteriBuilder.equal(root.get("fiscalCode"), filter.getFiscalCode());
                predicates.add(fiscalCodePredicate);
            }

            Predicate[] arrayPredicate = new Predicate[predicates.size()];

            for (int i = 0; i < arrayPredicate.length; i++) {
                arrayPredicate[i] = predicates.get(i);
            }

            return criteriBuilder.and(arrayPredicate);
        };
    }
}
