package com.nequi.usecases;

import com.nequi.models.Product;
import com.nequi.ports.inbound.ProductServicePort;
import com.nequi.ports.outbound.ProductRepositoryPort;
import com.nequi.validations.ProductBusinessValidations;
import reactor.core.publisher.Mono;

public class ProductUseCase implements ProductServicePort {

    private final ProductRepositoryPort productPersistencePort;
    private final ProductBusinessValidations productBusinessValidations;
    public ProductUseCase(ProductRepositoryPort productPersistencePort, ProductBusinessValidations productBusinessValidations) {
        this.productPersistencePort = productPersistencePort;
        this.productBusinessValidations = productBusinessValidations;
    }

    @Override
    public Mono<Product> createProduct(Product productMono) {
        return productBusinessValidations.validateProduct(productMono)
                .flatMap(productPersistencePort::save);
    }

    @Override
    public Mono<Void> deleteProductById(Integer productId) {
        return productPersistencePort.deleteProductById(productId);
    }

    @Override
    public Mono<Product> updateProduct(Product product) {
        return productPersistencePort.findProductById(product.getId())
                .flatMap(existingProduct -> {
                    existingProduct.setStock(product.getStock());
                    return productPersistencePort.save(existingProduct);
                });
    }

    @Override
    public Mono<Product> getProductById(Integer integer) {
        return productPersistencePort.findProductById(integer);
    }

}
