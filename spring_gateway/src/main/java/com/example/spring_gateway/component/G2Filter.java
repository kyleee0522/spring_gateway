package com.example.spring_gateway.component;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class G2Filter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("!#!#!#!#!#!#!#!# G2(-1) PRE 시작 #!#!#!#!#!#!#!#!");

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    System.out.println("!#!#!#!#!#!#!#!# G2(-1) POST 시작 #!#!#!#!#!#!#!#!");
                }));
    }

    @Override
    public int getOrder() {

        return -1;
    }
}