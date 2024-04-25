package guru.springfamework.services;

import guru.springfamework.api.v1.model.ProductDTO;

import java.util.List;

/**
 * @author john
 * @since 25/04/2024
 */
public interface ProductService {

    ProductDTO getProductByName(String name);
    List<ProductDTO> getAllProducts();
}
