package com.nequi.api.handler;

import com.nequi.api.dto.ProductRequestDTO;
import com.nequi.api.mappers.IProductRequestMapper;
import com.nequi.ports.inbound.ProductServicePort;
import com.nequi.exceptions.BusinessException;
import com.nequi.exceptions.BusinessErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.nequi.api.constants.Constants.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductHandlerImpl implements IProductHandler {
    private final ProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;

    @Override
    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(ProductRequestDTO.class)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.EMPTY_REQUEST_BODY)))
                .map(productRequestMapper::toDomain)
                .flatMap(productServicePort::createProduct)
                .flatMap(result -> ServerResponse.status(HttpStatus.CREATED).bodyValue(result))
                .onErrorResume(BusinessException.class, e -> handleError(HttpStatus.BAD_REQUEST, e.getMessage()))
                .onErrorResume(Exception.class, e -> handleError(HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE_INTERNAL_SERVER_ERROR));
    }

    @Override
    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        return Mono.justOrEmpty(request.pathVariable(PATH_DELETE_VARIABLE))
                .map(Integer::valueOf)
                .flatMap(productServicePort::deleteProductById)
                .then(ServerResponse.status(HttpStatus.NO_CONTENT).build())
                .onErrorResume(BusinessException.class, e -> handleError(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    private Mono<ServerResponse> handleError(HttpStatus status, String message) {
        log.error("Error: {}", message);
        return ServerResponse.status(status).bodyValue(message);
    }
}
