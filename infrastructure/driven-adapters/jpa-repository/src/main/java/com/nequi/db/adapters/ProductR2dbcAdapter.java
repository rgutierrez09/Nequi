package com.nequi.db.adapters;

import com.nequi.db.entities.ProductEntity;
import com.nequi.db.exceptions.DBErrorMessage;
import com.nequi.db.exceptions.DBExceptions;
import com.nequi.db.mappers.IProductEntityMapper;
import com.nequi.db.repositories.IProductRepository;
import com.nequi.models.Product;
import com.nequi.ports.outbound.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DuplicateKeyException;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
@Slf4j
public class ProductR2dbcAdapter implements ProductRepositoryPort {

    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;
    @Override
    public Mono<Product> save(Product product) {
        ProductEntity entity = productEntityMapper.toEntity(product);
        return productRepository.save(entity)
            .map(productEntityMapper::toModel)
            .onErrorResume(DuplicateKeyException.class, ex -> Mono.error(new DBExceptions(DBErrorMessage.PRODUCT_ALREADY_EXISTS)))
            .onErrorResume(DataAccessResourceFailureException.class, ex -> Mono.error(new DBExceptions(DBErrorMessage.FAILED_CONNECTION)));
    }

    @Override
    public Mono<Void> deleteProductById(Integer productId) {
        return productRepository.findById(productId)
                .flatMap(branch ->
                    productRepository.deleteById(productId)
                )
                .onErrorResume(ex -> Mono.error(new DBExceptions(DBErrorMessage.PRODUCT_NOT_FOUND)));
    }

    @Override
    public Mono<Product> findProductById(Integer id) {
        return productRepository.findById(id)
                .map(productEntityMapper::toModel)
                .onErrorResume(ex -> Mono.error(new DBExceptions(DBErrorMessage.PRODUCT_NOT_FOUND)));
    }
}
