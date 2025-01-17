package com.nequi.db.adapters;

import com.nequi.db.entities.FranchiseBranchEntity;
import com.nequi.db.mappers.IFranchiseBranchEntityMapper;
import com.nequi.db.repositories.IFranchiseBranchRepository;
import com.nequi.models.FranchiseBranch;
import com.nequi.ports.outbound.FranchiseBranchRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class FranchiseBranchR2DbcAdapter implements FranchiseBranchRepositoryPort {
    private final IFranchiseBranchRepository franchiseBranchRepository;
    private final IFranchiseBranchEntityMapper franchiseBranchEntityMapper;

    @Override
    public Mono<FranchiseBranch> saveFranchiseBranch(FranchiseBranch franchiseBranch) {
        FranchiseBranchEntity entity = franchiseBranchEntityMapper.toEntity(franchiseBranch);
        return franchiseBranchRepository.save(entity)
                .map(franchiseBranchEntityMapper::toModel);
    }
}
