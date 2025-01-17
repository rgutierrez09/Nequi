package com.nequi.config;

import com.nequi.ports.inbound.BranchServicePort;
import com.nequi.ports.inbound.FranchiseBranchServicePort;
import com.nequi.ports.inbound.FranchiseServicePort;
import com.nequi.ports.inbound.BranchProductServicePort;
import com.nequi.db.adapters.BranchR2dbcAdapter;
import com.nequi.db.adapters.FranchiseBranchR2DbcAdapter;
import com.nequi.db.adapters.FranchiseR2dbcAdapter;
import com.nequi.db.adapters.BranchProductR2DbcAdapter;
import com.nequi.db.adapters.ProductR2dbcAdapter;
import com.nequi.db.mappers.IBranchEntityMapper;
import com.nequi.db.mappers.IFranchiseBranchEntityMapper;
import com.nequi.db.mappers.IFranchiseEntityMapper;
import com.nequi.db.mappers.IBranchProductEntityMapper;
import com.nequi.db.mappers.IProductEntityMapper;
import com.nequi.db.repositories.IBranchRepository;
import com.nequi.db.repositories.IFranchiseBranchRepository;
import com.nequi.db.repositories.IFranchiseRepository;
import com.nequi.db.repositories.IBranchProductRepository;
import com.nequi.db.repositories.IProductRepository;
import com.nequi.ports.inbound.ProductServicePort;
import com.nequi.ports.outbound.BranchRepositoryPort;
import com.nequi.ports.outbound.FranchiseBranchRepositoryPort;
import com.nequi.ports.outbound.FranchiseRepositoryPort;
import com.nequi.ports.outbound.BranchProductRepositoryPort;
import com.nequi.ports.outbound.ProductRepositoryPort;
import com.nequi.usecases.BranchUseCase;
import com.nequi.usecases.FranchiseBranchUseCase;
import com.nequi.usecases.FranchiseUseCase;
import com.nequi.usecases.BranchProductUseCase;
import com.nequi.usecases.ProductUseCase;
import com.nequi.validations.BranchBusinessValidations;
import com.nequi.validations.FranchiseBranchBusinessValidations;
import com.nequi.validations.FranchiseBusinessValidations;
import com.nequi.validations.BranchProductBusinessValidations;
import com.nequi.validations.ProductBusinessValidations;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;
    private final IBranchEntityMapper branchEntityMapper;
    private final IBranchRepository branchRepository;
    private final IFranchiseRepository franchiseRepository;
    private final IFranchiseEntityMapper franchiseEntityMapper;
    private final IBranchProductRepository productBranchRepository;
    private final IBranchProductEntityMapper productBranchEntityMapper;
    private final IFranchiseBranchRepository franchiseBranchRepository;
    private final IFranchiseBranchEntityMapper franchiseBranchEntityMapper;

    @Bean
    public ProductRepositoryPort productPersistencePort() {
        return new ProductR2dbcAdapter(productRepository, productEntityMapper);
    }
    @Bean
    public BranchRepositoryPort branchPersistencePort() {
        return new BranchR2dbcAdapter(branchRepository, branchEntityMapper);
    }
    @Bean
    public FranchiseRepositoryPort franchisePersistencePort() {
        return new FranchiseR2dbcAdapter(franchiseRepository, franchiseEntityMapper);
    }
    @Bean
    public BranchProductRepositoryPort productBranchPersistencePort() {
        return new BranchProductR2DbcAdapter(productBranchRepository, productBranchEntityMapper);
    }
    @Bean
    public FranchiseBranchRepositoryPort franchiseBranchPersistencePort() {
        return new FranchiseBranchR2DbcAdapter(franchiseBranchRepository, franchiseBranchEntityMapper);
    }
    @Bean
    public ProductBusinessValidations productBusinessValidations() {
        return new ProductBusinessValidations();
    }
    @Bean
    public BranchBusinessValidations branchBusinessValidations() {
        return new BranchBusinessValidations();
    }
    @Bean
    public FranchiseBusinessValidations franchiseBusinessValidations() {
        return new FranchiseBusinessValidations();
    }
    @Bean
    public BranchProductBusinessValidations productBranchBusinessValidations() {
        return new BranchProductBusinessValidations();
    }
    @Bean
    public FranchiseBranchBusinessValidations franchiseBranchBusinessValidations() {
        return new FranchiseBranchBusinessValidations();
    }
    @Bean
    public ProductServicePort productServicePort(ProductRepositoryPort productPersistencePort, ProductBusinessValidations productBusinessValidations) {
        return new ProductUseCase(productPersistencePort, productBusinessValidations);
    }
    @Bean
    public BranchServicePort branchServicePort(BranchRepositoryPort branchPersistencePort, BranchBusinessValidations branchBusinessValidations) {
        return new BranchUseCase(branchPersistencePort, branchBusinessValidations);
    }
    @Bean
    public FranchiseServicePort franchiseServicePort(FranchiseRepositoryPort franchisePersistencePort, FranchiseBusinessValidations franchiseBusinessValidations) {
        return new FranchiseUseCase(franchisePersistencePort, franchiseBusinessValidations);
    }
    @Bean
    public BranchProductServicePort productBranchServicePort(BranchProductRepositoryPort productBranchPersistencePort, BranchProductBusinessValidations branchProductBusinessValidations) {
        return new BranchProductUseCase(productBranchPersistencePort, branchProductBusinessValidations);
    }
    @Bean
    public FranchiseBranchServicePort franchiseBranchServicePort(FranchiseBranchRepositoryPort franchiseBranchPersistencePort, FranchiseBranchBusinessValidations franchiseBranchBusinessValidations) {
        return new FranchiseBranchUseCase(franchiseBranchPersistencePort, franchiseBranchBusinessValidations);
    }
}
