package com.substring.easybuy.products.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshController {

@PostMapping("actuator/refresh")
public void refresh() throws Exception {
System.out.println("Refreshed");
}
}
