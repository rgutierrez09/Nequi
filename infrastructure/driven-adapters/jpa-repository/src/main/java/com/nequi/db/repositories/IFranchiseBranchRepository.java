package com.nequi.db.repositories;

import com.nequi.db.entities.FranchiseBranchEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IFranchiseBranchRepository extends ReactiveCrudRepository<FranchiseBranchEntity, Integer> {
}
