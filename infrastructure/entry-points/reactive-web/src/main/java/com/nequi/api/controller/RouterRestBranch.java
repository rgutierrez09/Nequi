package com.nequi.api.controller;

import com.nequi.api.handler.BranchHandlerImpl;
import com.nequi.api.utils.AddRouterRestBranchInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static com.nequi.api.constants.Constants.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterRestBranch {
    @Bean(name = BRANCH_ROUTER_FUNCTION)
    @AddRouterRestBranchInfo
    public RouterFunction<ServerResponse> routerFunction(BranchHandlerImpl handler) {
        return RouterFunctions.nest(
                path(PATH_BRANCH),
                RouterFunctions.route(POST(PATH_PATTERN), handler::createBranch)
                        .andRoute(PUT(PATH_UPDATE_PATTERN), handler::updateBranchProducts)
                        .andRoute(GET(PATH_GET_BRANCH_PATTERN), handler::getBranchProductsByBranchId)
        );
    }


}
