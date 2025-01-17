package com.nequi.ports.inbound;

import com.nequi.models.Product;
import reactor.core.publisher.Mono;

public interface ProductServicePort {
    Mono<Product> createProduct(Product product);
    Mono<Void> deleteProductById(Integer productId);

    Mono<Product> updateProduct(Product product);

    Mono<Product> getProductById(Integer integer);
}
