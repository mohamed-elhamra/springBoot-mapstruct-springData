package com.example.myApp.controllers;


import com.example.myApp.dtos.ProductDto;
import com.example.myApp.exceptions.ResourceNotFoundException;
import com.example.myApp.mapper.MyMapper;
import com.example.myApp.models.Product;
import com.example.myApp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/myapp")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MyMapper myMapper;

    @PostMapping("/products")
    public ProductDto saveProduct(@RequestBody ProductDto productDto){
        Product product = myMapper.toProduct(productDto);
        productRepository.save(product);
        return productDto;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductByID(@PathVariable Long id) throws ResourceNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with this id: " + id));
        ProductDto productDto = myMapper.toProductDto(product);
        return ResponseEntity.ok().body(productDto);
    }

    @GetMapping("/products")
    public List<ProductDto> getAllProducts(){
        return myMapper.toProductDtoList(productRepository.findAll());
    }

}
