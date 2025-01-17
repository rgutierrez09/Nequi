package com.nequi.api.handler;

import com.nequi.ports.inbound.FranchiseBranchServicePort;
import com.nequi.api.dto.FranchiseBranchRequestDTO;
import com.nequi.exceptions.BusinessErrorMessage;
import com.nequi.exceptions.BusinessException;
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
public class FranchiseBranchHandlerImpl implements IFranchiseBranchHandler{
    private final FranchiseBranchServicePort franchiseBranchServicePort;
    @Override
    public Mono<ServerResponse> associateFranchiseToBranch(ServerRequest request) {
        return request.bodyToMono(FranchiseBranchRequestDTO.class)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.EMPTY_REQUEST_BODY)))
                .flatMap(franchiseBranchRequestDTO -> {
                    Integer franchiseId = franchiseBranchRequestDTO.getFranchiseId();
                    List<Integer> branchIds = franchiseBranchRequestDTO.getBranchIds();

                    return Flux.fromIterable(branchIds)
                            .flatMap(branchId -> {
                                FranchiseBranch franchiseBranch = new FranchiseBranch(null, franchiseId, branchId);
                                return franchiseBranchServicePort.associateBranchToFranchise(franchiseBranch);
                            })
                            .then(ServerResponse.status(HttpStatus.CREATED).build());
                });
    }
}
