package guru.springfamework.api.v1.controllers;

import guru.springfamework.api.v1.model.ProductDTO;
import guru.springfamework.api.v1.model.ProductListDTO;
import guru.springfamework.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author john
 * @since 25/04/2024
 */
@Controller
@RequestMapping("/api/v1/products/")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ProductListDTO> getProductList() {
        return ResponseEntity.ok(new ProductListDTO(productService.getAllProducts()));
    }

    @GetMapping("{name}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.getProductByName(name));
    }
}
