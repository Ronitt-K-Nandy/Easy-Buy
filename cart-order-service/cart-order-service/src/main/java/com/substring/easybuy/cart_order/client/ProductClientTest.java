package com.substring.easybuy.cart_order.client;

import com.substring.easybuy.cart_order.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${product.service.id}")
public interface ProductClientTest {
    @GetMapping("/api/products/{productId}")
    ProductResponse getProductById(@PathVariable("productId") String productId);
}
