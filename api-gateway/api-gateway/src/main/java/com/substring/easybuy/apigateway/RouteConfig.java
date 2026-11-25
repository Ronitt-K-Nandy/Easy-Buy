package com.substring.easybuy.apigateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {


    private final String productServiceId;
    private final String cartOrderServiceId;
    private final String inventoryServiceId;

    public RouteConfig(@Value("${PRODUCT_APPLICATION_NAME}") String productServiceId, @Value("${CART_ORDER_APPLICATION_NAME}") String cartOrderServiceId
            , @Value("${INVENTORY_APPLICATION_NAME}") String inventoryServiceId) {
        this.productServiceId = productServiceId;
        this.cartOrderServiceId = cartOrderServiceId;
        this.inventoryServiceId = inventoryServiceId;
    }

    @Bean
    public RouteLocator route(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-route", route -> route
                        .path("/products", "/products/**")
                        .filters(f -> f
                                .addRequestHeader("x-api-gateway", "value from api gateway")
                                .rewritePath("^/products$", "/api/products")
                                .rewritePath("^/products/(?<remaining>.*)", "/api/products/${remaining}"))
                        .uri("lb://"+productServiceId))
                .route("cart-order-route", route -> route
                        .path("/carts","/carts/**")
                        .filters(f -> f
                        .rewritePath("^/carts$", "/api/carts")
                        .rewritePath("^/carts/(?<remaining>.*)", "/api/carts/${remaining}"))
                        .uri("lb://"+cartOrderServiceId))
                .route("inventory-route", route -> route
                        .path("/inventories","/inventories/**")
                        .filters(f -> f
                        .rewritePath("^/inventories$", "/api/inventories")
                        .rewritePath("^/inventories/(?<remaining>.*)", "/api/inventories/${remaining}"))
                        .uri("lb://"+inventoryServiceId))
                .build();
    }

}
