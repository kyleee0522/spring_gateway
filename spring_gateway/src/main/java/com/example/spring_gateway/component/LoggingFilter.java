package com.example.spring_gateway.component;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        GatewayFilter filter = new OrderedGatewayFilter((exchange,chain) -> {

            System.out.println("!#!#!#!#!#!#!#!# Logging PRE 시작 #!#!#!#!#!#!#!#!");

            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

                log.info("Logging PRE Filter Start: request id -> {}", request.getHeaders());
            System.out.println("!#!#!#!#!#!#!#!# Logging PRE 종료 #!#!#!#!#!#!#!#!");

            //Custom Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.out.println("!#!#!#!#!#!#!#!# Logging POST 시작 #!#!#!#!#!#!#!#!");
                    log.info("Logging POST Filter End: response code -> {} ", response.getHeaders());
                System.out.println("!#!#!#!#!#!#!#!# Logging POST 종료 #!#!#!#!#!#!#!#!");

            }));
        }, Ordered.LOWEST_PRECEDENCE); // 우선순위 지정할 수 있다
        //HIGHEST_PRECEDENCE

        return filter;
    }

    @Data
    public static class Config {
    }

}