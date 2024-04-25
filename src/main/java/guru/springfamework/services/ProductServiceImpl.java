package guru.springfamework.services;

import guru.springfamework.api.v1.mappers.ProductMapper;
import guru.springfamework.api.v1.model.ProductDTO;
import guru.springfamework.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author john
 * @since 25/04/2024
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO getProductByName(String name) {
        return productMapper.productToProductDTO(productRepository.findByName(name));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::productToProductDTO).collect(Collectors.toList());
    }
}
