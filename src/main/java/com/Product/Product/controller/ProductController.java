package com.Product.Product.controller;

import com.Product.Product.models.Product;
import com.Product.Product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products/")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping("/addProduct")
    public Product addProduct(@RequestBody Product product){
        return service.saveProduct(product);
    }
    @GetMapping("/getProducts")
    public Page<Product> getProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return service.getAllProducts(pageable);
    }
    @GetMapping("/getProductById/{id}")
    public Product getProductById(@PathVariable long id){
        return service.getProductById(id);
    }
    @GetMapping("/getProductByName/{name}")
    public Product getProductByName(@PathVariable String name){
        return service.getProductByName(name);
    }
    @PutMapping("/updateProduct")
    public Product updateProduct(@RequestBody Product product){
        return service.updateProduct(product);
    }
    @PatchMapping("/partialUpdate/{id}")
    public Product partialUpdate(@PathVariable long id, @RequestBody Product product){
            return service.partialProductUpdate(id, product);
    }
    @DeleteMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable long id){
        return service.deleteProduct(id);
    }
}
