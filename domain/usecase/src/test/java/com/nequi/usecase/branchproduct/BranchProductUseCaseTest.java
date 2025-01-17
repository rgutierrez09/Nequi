package com.nequi.usecase.branchproduct;

import com.nequi.exceptions.BusinessErrorMessage;
import com.nequi.exceptions.BusinessException;
import com.nequi.models.BranchProduct;
import com.nequi.ports.outbound.BranchProductRepositoryPort;
import com.nequi.usecases.BranchProductUseCase;
import com.nequi.validations.BranchProductBusinessValidations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchProductUseCaseTest {
    @Mock
    private BranchProductRepositoryPort branchProductPersistencePort;
    @Mock
    private BranchProductBusinessValidations branchProductBusinessValidations;
    @Mock
    private BranchProductUseCase branchProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        branchProductUseCase = new BranchProductUseCase(branchProductPersistencePort,branchProductBusinessValidations);
        BranchProduct branchProduct = new BranchProduct();
        branchProduct.setId(1);
        branchProduct.setBranchId(1);
        branchProduct.setProductId(1);

        lenient().when(branchProductBusinessValidations.validateProductBranch(any(BranchProduct.class)))
                .thenReturn(Mono.justOrEmpty(branchProduct));
        lenient().when(branchProductPersistencePort.saveProductBranch(any(BranchProduct.class)))
                .thenReturn(Mono.justOrEmpty(branchProduct));
    }

    @Test
    void createBranchProduct_Successfully() {
        BranchProduct branchProduct = new BranchProduct();
        branchProduct.setId(1);
        branchProduct.setBranchId(1);
        branchProduct.setProductId(1);

        Mono<BranchProduct> result = branchProductUseCase.associateProductToBranch(branchProduct);

        StepVerifier.create(result)
                .expectNextMatches(bp -> bp.getId() == 1 && bp.getBranchId() == 1 && bp.getProductId() == 1)
                .verifyComplete();

        verify(branchProductPersistencePort).saveProductBranch(any(BranchProduct.class));
    }
    @Test
    void createBranchProduct_IsNull_ThrowsMandatoryFieldException() {

        BranchProduct branchProduct = new BranchProduct();
        branchProduct.setId(1);
        branchProduct.setBranchId(0);
        branchProduct.setProductId(0);

        Mockito.when(branchProductBusinessValidations.validateProductBranch(any(BranchProduct.class)))
                .thenReturn(Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_DATA)));

        Mono<BranchProduct> result = branchProductUseCase.associateProductToBranch(branchProduct);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals(BusinessErrorMessage.MANDATORY_DATA.getMessage()))
                .verify();

        verify(branchProductPersistencePort, never()).saveProductBranch(any(BranchProduct.class));
    }

}