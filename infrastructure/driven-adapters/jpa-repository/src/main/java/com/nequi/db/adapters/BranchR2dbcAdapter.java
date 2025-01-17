package com.nequi.db.adapters;

import com.nequi.db.entities.BranchEntity;
import com.nequi.db.exceptions.DBErrorMessage;
import com.nequi.db.exceptions.DBExceptions;
import com.nequi.db.mappers.IBranchEntityMapper;
import com.nequi.db.repositories.IBranchRepository;
import com.nequi.models.Branch;
import com.nequi.ports.outbound.BranchRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DuplicateKeyException;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
@Slf4j
public class BranchR2dbcAdapter implements BranchRepositoryPort {
    private final IBranchRepository branchRepository;
    private final IBranchEntityMapper branchEntityMapper;
    @Override
    public Mono<Branch> save(Branch branch) {
        BranchEntity entity = branchEntityMapper.toEntity(branch);
        return branchRepository.findByName(entity.getName())
                .flatMap(existingBranch -> Mono.error(new DBExceptions(DBErrorMessage.BRANCH_ALREADY_EXISTS)))
                .defaultIfEmpty(entity)
                .flatMap(entityToSave ->
                    branchRepository.save(entity)
                        .map(branchEntityMapper::toModel)
                        .onErrorResume(DuplicateKeyException.class, ex -> Mono.error(new DBExceptions(DBErrorMessage.BRANCH_ALREADY_EXISTS)))
                        .onErrorResume(DataAccessResourceFailureException.class, ex -> Mono.error(new DBExceptions(DBErrorMessage.FAILED_CONNECTION)))
                );
    }

    @Override
    public Mono<Void> deleteBranchById(Integer id) {
        return branchRepository.findById(id)
                .flatMap(branch ->
                        branchRepository.deleteById(id)
                )
                .onErrorResume(ex -> Mono.error(new DBExceptions(DBErrorMessage.BRANCH_NOT_FOUND)));
    }

    @Override
    public Mono<Branch> getBranchById(Integer branchId) {
        return branchRepository.findById(branchId)
                .map(branchEntityMapper::toModel);
    }
}
