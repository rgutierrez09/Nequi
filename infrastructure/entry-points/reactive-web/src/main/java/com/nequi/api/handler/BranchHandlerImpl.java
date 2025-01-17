package com.nequi.api.handler;

import com.nequi.ports.inbound.BranchProductServicePort;
import com.nequi.ports.inbound.BranchServicePort;
import com.nequi.ports.inbound.ProductServicePort;
import com.nequi.api.dto.*;
import com.nequi.api.mappers.IBranchProductRequestMapper;
import com.nequi.api.mappers.IBranchRequestMapper;
import com.nequi.api.mappers.IProductRequestMapper;
import com.nequi.exceptions.BusinessErrorMessage;
import com.nequi.exceptions.BusinessException;
import com.nequi.models.Branch;
import com.nequi.models.BranchProduct;
import com.nequi.models.Product;
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
public class BranchHandlerImpl implements IBranchHandler {
    private final BranchServicePort branchServicePort;
    private final BranchProductServicePort branchProductServicePort;
    private final ProductServicePort productServicePort;
    private final IBranchRequestMapper branchRequestMapper;
    private final IProductRequestMapper productRequestMapper;
    private final IBranchProductRequestMapper branchProductRequestMapper;

    @Override
    public Mono<ServerResponse> createBranch(ServerRequest request) {
        return request.bodyToMono(BranchProductCreateRequestDto.class)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.EMPTY_REQUEST_BODY)))
                .flatMap(this::processBranchCreation)
                .onErrorResume(BusinessException.class, this::handleBusinessError);
    }

    private Mono<ServerResponse> processBranchCreation(BranchProductCreateRequestDto branchRequest) {
        Branch branch = branchRequestMapper.toDomain(branchRequest);
        List<Integer> productIds = branchRequest.getProductIds();

        return branchServicePort.createBranch(branch)
                .flatMap(savedBranch -> associateProductsAndRespond(savedBranch, productIds))
                .onErrorResume(associationError -> rollbackBranchCreation(branch.getId(), associationError));
    }

    private Mono<ServerResponse> associateProductsAndRespond(Branch savedBranch, List<Integer> productIds) {
        BranchProductRequestDTO branchRequestDto = new BranchProductRequestDTO(savedBranch.getId(), productIds);
        return associateProductsWithBranch(branchRequestDto)
                .then(ServerResponse.status(HttpStatus.CREATED)
                        .bodyValue(new BranchCreatedResponseDto(savedBranch.getName())));
    }

    private Mono<ServerResponse> rollbackBranchCreation(Integer branchId, Throwable error) {
        log.error("Error associating products with branch: {}", error.getMessage());
        return branchServicePort.deleteBranchById(branchId)
                .then(Mono.error(new BusinessException(BusinessErrorMessage.ASSOCIATE_BRANCH_PRODUCT_ERROR)));
    }

    private Mono<Void> associateProductsWithBranch(BranchProductRequestDTO branchProductRequestDto) {
        List<BranchProduct> branchProducts = branchProductRequestMapper.toDomain(branchProductRequestDto);
        return Flux.fromIterable(branchProducts)
                .flatMap(branchProductServicePort::associateProductToBranch)
                .then();
    }

    @Override
    public Mono<ServerResponse> updateBranchProducts(ServerRequest request) {
        return request.bodyToMono(BranchProductUpdateRequestDto.class)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.EMPTY_REQUEST_BODY)))
                .flatMap(this::processBranchProductUpdate);
    }

    private Mono<ServerResponse> processBranchProductUpdate(BranchProductUpdateRequestDto branchProductUpdateRequest) {
        Integer branchId = branchProductUpdateRequest.getBranchId();
        List<ProductDto> productDtos = branchProductUpdateRequest.getProductList();

        List<Product> productsToUpdate = productDtos.stream()
                .map(productRequestMapper::toProductDomain)
                .toList();

        return getProductsByBranch(branchId)
                .flatMap(branchProducts -> validateAndUpdateProducts(branchId, productsToUpdate, branchProducts));
    }

    private Mono<ServerResponse> validateAndUpdateProducts(Integer branchId, List<Product> productsToUpdate, List<BranchProduct> branchProducts) {
        List<Product> validProductsToUpdate = filterValidProducts(productsToUpdate, branchProducts);

        return Flux.fromIterable(validProductsToUpdate)
                .flatMap(productServicePort::updateProduct)
                .then(ServerResponse.ok().build())
                .onErrorResume(BusinessException.class, e -> Mono.error(new BusinessException(BusinessErrorMessage.UPDATE_FAILED)));
    }

    private Mono<List<BranchProduct>> getProductsByBranch(Integer branchId) {
        return branchProductServicePort.getBranchProductByBranchId(branchId);
    }

    private List<Product> filterValidProducts(List<Product> productsToUpdate, List<BranchProduct> branchProducts) {
        return productsToUpdate.stream()
                .filter(product -> branchProducts.stream()
                        .anyMatch(branchProduct -> branchProduct.getProductId().equals(product.getId())))
                .toList();
    }

    @Override
    public Mono<ServerResponse> getBranchProductsByBranchId(ServerRequest request) {
        Integer branchId = Integer.valueOf(request.pathVariable("branchId"));

        return getBranchById(branchId)
                .zipWith(getBranchProducts(branchId))
                .flatMap(tuple -> buildBranchProductResponse(tuple.getT1(), tuple.getT2()))
                .flatMap(responseDto -> ServerResponse.ok().bodyValue(responseDto))
                .onErrorResume(this::handleError);
    }

    private Mono<Branch> getBranchById(Integer branchId) {
        return branchServicePort.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.BRANCH_NOT_FOUND)));
    }

    private Mono<List<BranchProduct>> getBranchProducts(Integer branchId) {
        return branchProductServicePort.getBranchProductByBranchId(branchId)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.PRODUCT_NOT_FOUND)));
    }

    private Mono<BranchProductResponseDto> buildBranchProductResponse(Branch branch, List<BranchProduct> branchProducts) {
        List<Integer> productIds = branchProducts.stream()
                .map(BranchProduct::getProductId)
                .toList();

        return getProductsByIds(productIds)
                .map(products -> createResponseDto(branch, products));
    }

    private Mono<List<Product>> getProductsByIds(List<Integer> productIds) {
        return Flux.fromIterable(productIds)
                .flatMap(productServicePort::getProductById)
                .collectList()
                .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.PRODUCT_NOT_FOUND)));
    }

    private BranchProductResponseDto createResponseDto(Branch branch, List<Product> products) {
        List<ProductResponseDto> productResponseDtos = products.stream()
                .map(this::mapToProductResponseDto)
                .toList();

        return new BranchProductResponseDto(branch.getName(), productResponseDtos);
    }

    private ProductResponseDto mapToProductResponseDto(Product product) {
        return new ProductResponseDto(product.getName(), product.getStock());
    }

    private Mono<ServerResponse> handleBusinessError(BusinessException e) {
        log.error("Business error: {}", e.getMessage());
        return ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(e.getMessage());
    }

    private Mono<ServerResponse> handleError(Throwable e) {
        if (e instanceof BusinessException) {
            return handleBusinessError((BusinessException) e);
        }
        log.error("Unexpected error: {}", e.getMessage());
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
