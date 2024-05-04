package guru.springfamework.api.v1.controllers;

import guru.springfamework.api.v1.model.ProductDTO;
import guru.springfamework.api.v1.model.ProductListDTO;
import guru.springfamework.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author john
 * @since 25/04/2024
 */
@RestController
@RequestMapping("/api/v1/products/")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductListDTO getProductList() {
        return new ProductListDTO(productService.getAllProducts());
    }

    @GetMapping("{name}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

}