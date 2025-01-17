package com.nequi.db.adapters;

import com.nequi.db.entities.FranchiseEntity;
import com.nequi.db.exceptions.DBErrorMessage;
import com.nequi.db.exceptions.DBExceptions;
import com.nequi.db.mappers.IFranchiseEntityMapper;
import com.nequi.db.repositories.IFranchiseRepository;
import com.nequi.models.Franchise;
import com.nequi.ports.outbound.FranchiseRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DuplicateKeyException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class FranchiseR2dbcAdapter implements FranchiseRepositoryPort {

    private final IFranchiseRepository franchiseRepository;
    private final IFranchiseEntityMapper franchiseEntityMapper;
    @Override
    public Mono<Franchise> save(Franchise franchise) {
        FranchiseEntity entity = franchiseEntityMapper.toEntity(franchise);
        return franchiseRepository.findByName(entity.getName())
                .flatMap(existingBranch -> Mono.error(new DBExceptions(DBErrorMessage.BRANCH_ALREADY_EXISTS)))
                .defaultIfEmpty(entity)
                .flatMap(entityToSave ->
                        franchiseRepository.save(entity)
                                .map(franchiseEntityMapper::toModel)
                                .onErrorResume(DuplicateKeyException.class, ex -> Mono.error(new DBExceptions(DBErrorMessage.BRANCH_ALREADY_EXISTS)))
                                .onErrorResume(DataAccessResourceFailureException.class, ex -> Mono.error(new DBExceptions(DBErrorMessage.FAILED_CONNECTION)))
                );
    }

    @Override
    public Mono<Void> deleteFranchiseById(Integer id) {
        return franchiseRepository.findById(id)
                .flatMap(branch ->
                        franchiseRepository.deleteById(id)
                )
                .switchIfEmpty(Mono.error(new DBExceptions(DBErrorMessage.FRANCHISE_NOT_FOUND)));
    }
}
