package com.nequi.validations;

import com.nequi.exceptions.BusinessErrorMessage;
import com.nequi.exceptions.BusinessException;
import com.nequi.models.FranchiseBranch;
import reactor.core.publisher.Mono;

public class FranchiseBranchBusinessValidations {
    public Mono<FranchiseBranch> validateFranchiseBranch(FranchiseBranch franchiseBranch) {
        if (franchiseBranch.getFranchiseId() == null || franchiseBranch.getBranchId() == null) {
            return Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_DATA));
        }
        return Mono.just(franchiseBranch);
    }
}
