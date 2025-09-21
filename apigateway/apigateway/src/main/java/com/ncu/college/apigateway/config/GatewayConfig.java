package com.ncu.college.apigateway.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {


    @Bean
public RouteLocator routes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("userservice", r -> r.path("/users/**")
            .uri("lb://userservice"))
        .route("notificationservice", r -> r.path("/notifications/**")
            .uri("lb://notificationservice"))
        .route("verificationservice", r -> r.path("/verification/**")
            .uri("lb://verificationservice"))
        .route("documentservice", r -> r.path("/documents/**")
            .uri("lb://documentservice"))
        .build();
}

//http://localhost:8005/users
//http://localhost:8005/notifications
//http://localhost:8005/verification
//http://localhost:8005/documents/


}
