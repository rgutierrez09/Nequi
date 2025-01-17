package com.nequi.usecase.product;

import com.nequi.exceptions.BusinessErrorMessage;
import com.nequi.exceptions.BusinessException;
import com.nequi.models.Product;
import com.nequi.ports.outbound.ProductRepositoryPort;
import com.nequi.usecases.ProductUseCase;
import com.nequi.validations.ProductBusinessValidations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductUseCaseTest {
    @Mock
    private ProductUseCase productUseCase;
    @Mock
    private ProductRepositoryPort productPersistencePort;
    @Mock
    private ProductBusinessValidations productBusinessValidations;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productUseCase = new ProductUseCase(productPersistencePort,productBusinessValidations);

        Product product = new Product();
        product.setId(1);
        product.setName("ttt");
        product.setStock(1);

        lenient().when(productBusinessValidations.validateProduct(any(Product.class)))
                .thenReturn(Mono.just(product));
        lenient().when(productPersistencePort.save(any(Product.class)))
                .thenReturn(Mono.just(product));
    }

    @Test
    void createProduct_successful() {
        Product input = new Product();
        Mono<Product> result = productUseCase.createProduct(input);


        StepVerifier.create(result)
                .expectNextMatches(product ->
                        product.getId() == 1 &&
                                "ttt".equals(product.getName()) &&
                        product.getStock() == 1)
                .expectComplete()
                .verify();

    }

    @Test
    void createProduct_nullName_shouldThrowMandatoryNameError() {
        Product input = new Product();
        input.setStock(10);

        when(productBusinessValidations.validateProduct(any(Product.class)))
                .thenReturn(Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_NAME)));

        Mono<Product> result = productUseCase.createProduct(input);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals(BusinessErrorMessage.MANDATORY_NAME.getMessage()))
                .verify();
    }

    @Test
    void createProduct_zeroStock_shouldThrowMandatoryStock() {
        Product input = new Product();
        input.setName("Producto Test");
        input.setStock(0);

        when(productBusinessValidations.validateProduct(any(Product.class)))
                .thenReturn(Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_STOCK)));

        Mono<Product> result = productUseCase.createProduct(input);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals(BusinessErrorMessage.MANDATORY_STOCK.getMessage()))
                .verify();
    }

    @Test
    void deleteProductById_successful() {
        Integer productId = 1;

        when(productPersistencePort.deleteProductById(productId)).thenReturn(Mono.empty());

        Mono<Void> result = productUseCase.deleteProductById(productId);

        StepVerifier.create(result)
                .expectComplete()
                .verify();

        verify(productPersistencePort).deleteProductById(productId);
    }

    @Test
    void deleteProductById_notFound() {
        Integer productId = 1;

        when(productPersistencePort.deleteProductById(productId)).thenReturn(Mono.error(new BusinessException(BusinessErrorMessage.PRODUCT_NOT_FOUND)));

        Mono<Void> result = productUseCase.deleteProductById(productId);

        StepVerifier.create(result)
                .expectError(BusinessException.class)
                .verify();

        verify(productPersistencePort).deleteProductById(productId);
    }

}
