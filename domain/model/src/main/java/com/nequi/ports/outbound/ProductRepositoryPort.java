package com.nequi.ports.outbound;

import com.nequi.models.Product;
import reactor.core.publisher.Mono;

public interface ProductRepositoryPort {
    Mono<Product> save(Product product);
    Mono<Void> deleteProductById(Integer productId);

    Mono<Product> findProductById(Integer id);
}
