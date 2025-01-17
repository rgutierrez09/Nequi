package com.nequi.api.controller;

import com.nequi.api.handler.ProductHandlerImpl;
import com.nequi.api.utils.AddRouterRestProductInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.nequi.api.constants.Constants.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterRestProduct {
    @Bean(PRODUCT_ROUTER_FUNCTION)
    @AddRouterRestProductInfo
    public RouterFunction<ServerResponse> routerFunction(ProductHandlerImpl handler) {
        return RouterFunctions.nest(
                path(PATH_PRODUCT),
                RouterFunctions.route(POST(PATH_PATTERN), handler::createProduct)
                        .andRoute(DELETE(PATH_DELETE_PATTERN), handler::deleteProduct)
        );
    }
}
