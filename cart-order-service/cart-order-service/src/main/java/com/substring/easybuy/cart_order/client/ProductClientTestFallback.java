package com.substring.easybuy.cart_order.client;

import com.substring.easybuy.cart_order.dto.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductClientTestFallback implements ProductClientTest{

    @Override
    public ProductResponse getProductById(String productId) {
        log.error("Fallback triggered for product ID: {}", productId);
        System.out.println("Product Client Test Fallback");
        return null;
    }
}
