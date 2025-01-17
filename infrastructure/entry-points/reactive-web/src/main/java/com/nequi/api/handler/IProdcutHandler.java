package com.nequi.api.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IProdcutHandler {
    Mono<ServerResponse> createProduct(ServerRequest request);

    Mono<ServerResponse> deleteProduct(ServerRequest request);
}
