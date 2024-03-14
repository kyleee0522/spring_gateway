package com.example.spring_gateway.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class G1Filter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("!#!#!#!#!#!#!#!# G1(-2) PRE 시작 #!#!#!#!#!#!#!#!");

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        log.info("Global Filter Start : request id = {}", request.getHeaders());


        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    System.out.println("!#!#!#!#!#!#!#!# G1(-2) POST 시작 #!#!#!#!#!#!#!#!");
                    log.info("Global Filter Start : response id = {}", response.getHeaders());
                }));
    }

    @Override
    public int getOrder() {

        return -2;
    }
}
