package com.example.spring_gateway.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final PasswordEncoder passwordEncoder;
    private final ReactiveUserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;
    private final DecryptService decryptService;


    /*
    // 복호화 X //
   @PostMapping("api/login")
    Mono<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return userDetailsService.findByUsername(loginRequest.username())
                .filter(u -> passwordEncoder.matches(loginRequest.password(), u.getPassword()))
                .map(tokenProvider::generateToken)
                .map(LoginResponse::new)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
    }
    */

    // 복호화 O //
    @PostMapping("/api/login")
    Mono<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return decryptService.login(loginRequest.password())
                .flatMap(decryptedPassword -> userDetailsService.findByUsername(loginRequest.username())
                        .filter(user -> passwordEncoder.matches(decryptedPassword, user.getPassword()))
                        .map(tokenProvider::generateToken)
                        .map(LoginResponse::new)
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"))));
    }
}