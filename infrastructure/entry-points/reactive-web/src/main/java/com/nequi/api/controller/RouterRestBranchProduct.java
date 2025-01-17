package com.nequi.api.controller;

import com.nequi.api.handler.BranchProductHandlerImpl;
import com.nequi.api.utils.AddRouterRestBranchInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.nequi.api.constants.Constants.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterRestBranchProduct {
    @Bean(name = BRANCH_PRODUCT_ROUTER_FUNCTION)
    @AddRouterRestBranchInfo
    public RouterFunction<ServerResponse> routerFunction(BranchProductHandlerImpl handler) {
        return RouterFunctions.nest(
                path(PATH_BRANCH_PRODUCT),
                RouterFunctions.route(POST(PATH_PATTERN), handler::associateBranchProduct)
        );
    }
}
