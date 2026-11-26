package com.substring.easybuy.cart_order.controller;

import com.substring.easybuy.cart_order.client.ProductClientTest;
import com.substring.easybuy.cart_order.dto.PagedResponse;
import com.substring.easybuy.cart_order.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("cart0rder/api/test")
public class TestController {

	@Autowired
	private ProductClientTest _productClientTest;

	@GetMapping("/{productId}")
	public ProductResponse testFeignClient(@PathVariable String productId)
	{
		return _productClientTest.getProductById(productId);
	}

	@GetMapping("/products")
	public PagedResponse<ProductResponse> testFeignClient2()
	{
		return _productClientTest.getProducts();
	}

	@GetMapping("/client/{productId}")
	public ResponseEntity<ProductSnapshot> getProductForProductClient(@PathVariable("productId") String productId){
		ResponseEntity<ProductSnapshot> forEntity = restTemplate.getForEntity("http://PRODUCT-SERVICE/api/products/" + productId, ProductSnapshot.class);
		return forEntity;
	}
}
