package com.example.spring_gateway.component;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GUIDWebFilter implements WebFilter {

    //private static final Logger log = LoggerFactory.getLogger(GUIDWebFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        System.out.println("!#!#!#!#!#!#!#!# PRE 시작 GUID WEB FILTER 시작 PRE #!#!#!#!#!#!#!#!");
        // GUID 생성
        String guid = UUID.randomUUID().toString();

        // 요청 헤더에 X-GUID 추가
        ServerHttpRequest requestGUID = exchange.getRequest().mutate()
                .header("GUID", guid)
                .build();

        // 로그에 요청 헤더 기록 (GUID 포함)
        log.info("GUID Header " + requestGUID.getHeaders());

        System.out.println("!#!#!#!#!#!#!#!# PRE 종료 GUID WEB FILTER 종료 PRE #!#!#!#!#!#!#!#!");
        // 새로운 ServerWebExchange 인스턴스에 변경된 요청 객체를 설정하고 필터 체인을 계속 진행
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    log.info("!#!#!#!#!#!#!#!# POST 시작 GUID WEB FILTER 시작 POST #!#!#!#!#!#!#!#!");

                    log.info("!#!#!#!#!#!#!#!# POST 종료 GUID WEB FILTER 종료 POST #!#!#!#!#!#!#!#!");
                }));

            /*
            // 요청 바디를 캡처하고 로깅합니다.
            return DataBufferUtils.join(exchange.getRequest().getBody())
                    .flatMap(dataBuffer -> {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        DataBufferUtils.release(dataBuffer);

                        String body = new String(bytes, StandardCharsets.UTF_8);
                        log.info("Request Body: {}", body);

                        // 캡처된 요청 바디를 다시 서버 요청에 추가합니다.
                        Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                            return Mono.just(buffer);
                        });

                        ServerHttpRequestDecorator mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return cachedFlux;
                            }
                        };

                        // 변경된 요청으로 필터 체인을 계속 진행하고, 요청 처리가 완료된 후에 로깅을 수행합니다.
                        return chain.filter(exchange.mutate().request(mutatedRequest).build())
                                .then(Mono.fromRunnable(() -> {
                                    log.info("!#!#!#!#!#!#!#!# POST 시작 GUID WEB FILTER 시작 POST #!#!#!#!#!#!#!#!");
                                    log.info("!#!#!#!#!#!#!#!# POST 종료 GUID WEB FILTER 종료 POST #!#!#!#!#!#!#!#!");
                                }));
                    });

             */
    }

}