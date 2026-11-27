package com.substring.easybuy.cart_order.controller;

import com.substring.easybuy.cart_order.client.ProductClientTest;
import com.substring.easybuy.cart_order.dto.PagedResponse;
import com.substring.easybuy.cart_order.dto.ProductResponse;
import com.substring.easybuy.cart_order.dto.ProductSnapshot;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@RestController
@Validated
@RequestMapping("cart0rder/api/test")
@Slf4j
public class TestController {

	@Autowired
	private ProductClientTest _productClientTest;

	@Autowired
	private RestTemplate restTemplate;


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

	@GetMapping("/retry/client/{productId}")
	@Retry(name="getProductForProductClientRetry", fallbackMethod = "testRetryForRestClientOrTemplateFallBack")
	public ResponseEntity<ProductSnapshot> testRetryForRestClientOrTemplate(@PathVariable("productId") String productId){
		log.info("Retrying...");
		if(true){
			throw new NullPointerException();
		}
		ResponseEntity<ProductSnapshot> forEntity = restTemplate.getForEntity("http://PRODUCT-SERVICE/api/products/" + productId, ProductSnapshot.class);
		return forEntity;
	}

	public ResponseEntity<ProductSnapshot> testRetryForRestClientOrTemplateFallBack(@PathVariable("productId") String productId, Throwable t){
		log.info("Retry Fallabck ..."+t.getMessage());
		return null;
	}

	@GetMapping("/cb/client/{productId}")
	@CircuitBreaker(name="getProductForProductClientCB", fallbackMethod ="fallBackForCBClientAndTemplate")
	public ResponseEntity<?> testCBForRestClientOrTemplate(@PathVariable("productId") String productId){
		ResponseEntity<ProductSnapshot> forEntity = restTemplate.getForEntity("http://PRODUCT-SERVICE/api/products/" + productId, ProductSnapshot.class);
		return forEntity;
	}

	public ResponseEntity<?> fallBackForCBClientAndTemplate(String productId, Exception ex){
		return ResponseEntity
				.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body("Server is down, please after sometime...");
	}
}
