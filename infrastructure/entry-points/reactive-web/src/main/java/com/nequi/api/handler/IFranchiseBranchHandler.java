package com.nequi.api.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IFranchiseBranchHandler {
    Mono<ServerResponse> associateFranchiseToBranch(ServerRequest request);
}
