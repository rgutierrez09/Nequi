package com.nequi.usecase.branch;

import com.nequi.exceptions.BusinessErrorMessage;
import com.nequi.exceptions.BusinessException;
import com.nequi.models.Branch;
import com.nequi.models.Product;
import com.nequi.ports.outbound.BranchRepositoryPort;
import com.nequi.usecases.BranchUseCase;
import com.nequi.validations.BranchBusinessValidations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchUseCaseTest {
    @Mock
    private BranchRepositoryPort branchPersistencePort;
    @Mock
    private BranchBusinessValidations branchBusinessValidations;
    @Mock
    private BranchUseCase branchUseCase;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        branchUseCase = new BranchUseCase(branchPersistencePort,branchBusinessValidations);
        List<Product> products = Arrays.asList(
                new Product(1, "aaa", 1),
                new Product(2, "bbb", 1),
                new Product(3, "ccc",1)
        );

        Branch branch = new Branch();
        branch.setId(1);
        branch.setName("ttt");
        branch.setProductIds(products);

        lenient().when(branchBusinessValidations.validateBranch(any(Branch.class)))
                .thenReturn(Mono.just(branch));
        lenient().when(branchPersistencePort.save(any(Branch.class)))
                .thenReturn(Mono.just(branch));
    }
    @Test
    void createBranch_Successfully() {

        List<Product> products = Arrays.asList(
            new Product(1, "aaa", 1),
            new Product(2, "bbb", 1),
            new Product(3, "ccc",1)
        );

        Branch branch = new Branch();
        branch.setId(1);
        branch.setName("ttt");
        branch.setProductIds(products);

        when(branchPersistencePort.save(any(Branch.class))).thenReturn(Mono.just(branch));

        // Act
        Mono<Branch> result = branchUseCase.createBranch(branch);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(b -> b.getId() == 1 && "ttt".equals(b.getName()) && b.getProductIds().equals(products))
                .verifyComplete();

        verify(branchPersistencePort).save(any(Branch.class));
    }

    @Test
    void createBranch_NameIsNull_ThrowsMandatoryFieldException() {

        Branch branch = new Branch();
        branch.setId(1);
        branch.setName("");
        branch.setProductIds(Arrays.asList(
                new Product(1, "aaa", 1),
                new Product(2, "bbb", 1),
                new Product(3, "ccc", 1)
        ));

        when(branchBusinessValidations.validateBranch(any(Branch.class)))
                .thenReturn(Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_NAME)));

        Mono<Branch> result = branchUseCase.createBranch(branch);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                                throwable.getMessage().equals(BusinessErrorMessage.MANDATORY_NAME.getMessage()))
                .verify();

        verify(branchPersistencePort, never()).save(any(Branch.class));
    }

    @Test
    void deleteBranchById() {
        Integer branchId = 1;

        when(branchPersistencePort.deleteBranchById(branchId)).thenReturn(Mono.empty());

        Mono<Void> result = branchUseCase.deleteBranchById(branchId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(branchPersistencePort).deleteBranchById(branchId);
    }

    @Test
    void deleteProductById_notFound() {
        Integer branchId = 1;

        when(branchPersistencePort.deleteBranchById(branchId)).thenReturn(Mono.error(new BusinessException(BusinessErrorMessage.BRANCH_NOT_FOUND)));

        Mono<Void> result = branchUseCase.deleteBranchById(branchId);

        StepVerifier.create(result)
                .expectError(BusinessException.class)
                .verify();

        verify(branchPersistencePort).deleteBranchById(branchId);
    }

}