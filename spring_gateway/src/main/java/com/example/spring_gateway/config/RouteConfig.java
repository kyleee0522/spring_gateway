package com.example.spring_gateway.config;


import com.example.spring_gateway.component.GUIDWebFilter;
import com.example.spring_gateway.component.L1Filter;
import com.example.spring_gateway.component.LoggingFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class RouteConfig {

/* Method로 route
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, L1Filter l1Filter) {
        return builder.routes()
                .route("login_com", r -> r.path("/api/login/**")
                        .uri("http://localhost:8081"))
                .route("login_qur", r -> r.path("/api/login/**")
                        .uri("http://localhost:8082"))
                .route("post", r -> r
                        .method("POST")
                        .filters(f -> f.filter(l1Filter.apply(new L1Filter.Config(true, true))))
                        .uri("http://localhost:8081"))
                .route("get", r -> r
                        .method("GET")
                        .filters(f -> f.addRequestHeader("Hello", "world"))
                        .uri("http://localhost:8082"))
                .build();
    }

*/

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {

        LoggingFilter loggingFilter = new LoggingFilter();

        return builder.routes()
                // appName이 command일 때 8081로 라우팅
                .route("route_command", r -> r
                        .header("appName", "command")
                        .filters(f -> f.addRequestHeader("Hello", "world"))
                        //.filters(f -> f.filter(l1Filter.apply(new L1Filter.Config(true, true))))
                        //.filters(f -> f.filter(loggingFilter))
                        .uri("http://localhost:8081"))
                // appName이 query일 때 8082로 라우팅
                .route("route_query", r -> r
                        .header("appName", "query")
                        //.filters(f -> f.filter(loggingFilter.apply(new LoggingFilter.Config())).addRequestHeader("appName", "query"))
                        // LoggingFilter 적용
                        .uri("http://localhost:8082"))
                .build();


    }

}