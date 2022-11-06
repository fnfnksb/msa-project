package com.msa.controller;

import com.msa.domain.Product;
import com.msa.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class RestProductController {

    private final ProductService productService;

    @GetMapping("/greeting")
    public String welcome() {
        return "hello, product-api";
    }

    @GetMapping("{prdId}")
    public ResponseEntity<Product> getPrd(@PathVariable("prdId") String prdId) {
        Product prdInfo = productService.selectPrd(prdId);
        return ResponseEntity.status(HttpStatus.OK).body(prdInfo);
    }
}
