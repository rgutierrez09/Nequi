package com.nequi.api.utils;

import com.nequi.api.dto.BranchProductRequestDTO;
import com.nequi.api.dto.BranchRequestDTO;
import com.nequi.models.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.http.MediaType;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@RouterOperations({
        @RouterOperation(
                method = RequestMethod.POST,
                path = "/branchProduct/",
                operation =
                @Operation(
                        description = "Add BranchProduct",
                        operationId = "addBranchProduct",
                        tags = "branchProduct",
                        requestBody =
                        @RequestBody(
                                description = "BranchProduct to add",
                                required = true,
                                content = @Content(schema = @Schema(implementation = BranchProductRequestDTO.class,
                                        requiredProperties = {"branchId", "productId"}))),
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Add Branch response",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        schema = @Schema(implementation = Product.class))
                                        }),
                                @ApiResponse(
                                        responseCode = "400",
                                        description = "Bad Request response",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        schema = @Schema(implementation = ErrorResponse.class))
                                        })
                        }))
})
public @interface AddRouterBranchProductInfo {
}
