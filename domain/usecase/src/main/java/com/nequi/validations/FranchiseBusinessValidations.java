package com.nequi.validations;

import com.nequi.exceptions.BusinessErrorMessage;
import com.nequi.exceptions.BusinessException;
import com.nequi.models.Branch;
import com.nequi.models.Franchise;
import reactor.core.publisher.Mono;

public class FranchiseBusinessValidations {
    public Mono<Franchise> validateBranch(Franchise franchise) {
        return validateName(franchise)
                .then(Mono.just(franchise));
    }

    private Mono<Void> validateName(Franchise franchise) {
        if (franchise.getName().isEmpty()) {
            return Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_NAME));
        }
        return Mono.empty();
    }
}
