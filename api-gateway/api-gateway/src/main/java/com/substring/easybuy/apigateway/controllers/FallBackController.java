package com.substring.easybuy.apigateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

@GetMapping("/product-fallback")
public Mono<String> productCircuitBreaker(){
    return Mono.just("Our product service is down, please contact support team. Helpline: 3569");
}
}
