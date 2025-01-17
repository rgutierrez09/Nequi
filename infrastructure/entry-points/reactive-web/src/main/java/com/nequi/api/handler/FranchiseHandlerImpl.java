package com.nequi.api.handler;

import com.nequi.ports.inbound.FranchiseBranchServicePort;
import com.nequi.ports.inbound.FranchiseServicePort;
import com.nequi.api.dto.FranchiseBranchRequestDTO;
import com.nequi.api.dto.FranchiseCreateRequestDTO;
import com.nequi.api.dto.FranchiseCreatedResponseDTO;
import com.nequi.api.mappers.IFranchiseBranchRequestMapper;
import com.nequi.api.mappers.IFranchiseRequestMapper;
import com.nequi.exceptions.BusinessErrorMessage;
import com.nequi.exceptions.BusinessException;
import com.nequi.models.Franchise;
import com.nequi.models.FranchiseBranch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FranchiseHandlerImpl implements IFranchiseHandler {
    private final FranchiseServicePort franchiseServicePort;
    private final IFranchiseRequestMapper franchiseRequestMapper;
    private final FranchiseBranchServicePort franchiseBranchServicePort;
    private final IFranchiseBranchRequestMapper franchiseBranchRequestMapper;

    @Override
    public Mono<ServerResponse> createFranchise(ServerRequest request) {
        return request.bodyToMono(FranchiseCreateRequestDTO.class)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.EMPTY_REQUEST_BODY)))
                .flatMap(this::processFranchiseCreation)
                .onErrorResume(BusinessException.class, this::handleBusinessError)
                .onErrorResume(Exception.class, this::handleUnexpectedError);
    }

    private Mono<ServerResponse> processFranchiseCreation(FranchiseCreateRequestDTO franchiseRequest) {
        Franchise franchise = franchiseRequestMapper.toDomain(franchiseRequest);
        List<Integer> branchIds = franchiseRequest.getBranchIds();

        return franchiseServicePort.createFranchise(franchise)
                .flatMap(savedFranchise -> associateBranchesAndRespond(savedFranchise, branchIds))
                .onErrorResume(associationError -> rollbackOnError(franchise.getId(), associationError));
    }

    private Mono<ServerResponse> associateBranchesAndRespond(Franchise savedFranchise, List<Integer> branchIds) {
        FranchiseBranchRequestDTO franchiseBranchRequestDto = new FranchiseBranchRequestDTO(savedFranchise.getId(), savedFranchise.getName(), branchIds);

        return associateBranchesWithFranchise(franchiseBranchRequestDto)
                .then(ServerResponse.status(HttpStatus.CREATED)
                        .bodyValue(new FranchiseCreatedResponseDTO(savedFranchise.getName())));
    }

    private Mono<ServerResponse> rollbackOnError(Integer franchiseId, Throwable associationError) {
        log.error("Error associating branches with franchise: {}", associationError.getMessage());
        return franchiseServicePort.deleteFranchiseById(franchiseId)
                .then(Mono.error(new BusinessException(BusinessErrorMessage.ASSOCIATE_FRANCHISE_BRANCH_ERROR)));
    }

    private Mono<Void> associateBranchesWithFranchise(FranchiseBranchRequestDTO franchiseBranchRequestDto) {
        return Flux.fromIterable(franchiseBranchRequestMapper.toDomain(franchiseBranchRequestDto))
                .flatMap(franchiseBranchServicePort::associateBranchToFranchise)
                .then();
    }

    private Mono<ServerResponse> handleBusinessError(BusinessException e) {
        log.error("Business error creating franchise: {}", e.getMessage());
        return ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(e.getMessage());
    }

    private Mono<ServerResponse> handleUnexpectedError(Exception e) {
        log.error("Unexpected error creating franchise: {}", e.getMessage());
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .bodyValue("Error interno del servidor");
    }
}
