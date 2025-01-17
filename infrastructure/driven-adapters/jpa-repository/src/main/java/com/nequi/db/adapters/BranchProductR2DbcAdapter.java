package com.nequi.db.adapters;

import com.nequi.db.entities.BranchProductEntity;
import com.nequi.db.exceptions.DBErrorMessage;
import com.nequi.db.exceptions.DBExceptions;
import com.nequi.db.mappers.IBranchProductEntityMapper;
import com.nequi.db.repositories.IBranchProductRepository;
import com.nequi.models.BranchProduct;
import com.nequi.ports.outbound.BranchProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Slf4j
public class BranchProductR2DbcAdapter implements BranchProductRepositoryPort {
    private final IBranchProductRepository productBranchRepository;
    private final IBranchProductEntityMapper productBranchEntityMapper;
    @Override
    public Mono<BranchProduct> saveProductBranch(BranchProduct branchProduct) {
        BranchProductEntity entity = productBranchEntityMapper.toEntity(branchProduct);
        return productBranchRepository.save(entity)
                .map(productBranchEntityMapper::toModel);
    }

    @Override
    public Mono<List<BranchProduct>> findBranchProductByBranchId(Integer branchId) {
        return productBranchRepository.findByBranchId(branchId)
                .collectList()
                .flatMap(branchProductEntities -> {
                    if (branchProductEntities.isEmpty()) {
                        return Mono.error(new DBExceptions(DBErrorMessage.PRODUCT_NOT_FOUND));
                    }
                    List<BranchProduct> branchProducts = branchProductEntities.stream()
                            .map(productBranchEntityMapper::toModel)
                            .collect(toList());
                    return Mono.just(branchProducts);
                });
    }

}
