package guru.springfamework.services;

import guru.springfamework.api.v1.mappers.ProductMapper;
import guru.springfamework.api.v1.mappers.ProductMapperImpl;
import guru.springfamework.api.v1.model.ProductDTO;
import guru.springfamework.domain.Product;
import guru.springfamework.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author john
 * @since 25/04/2024
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    ProductMapper productMapper;

    ProductService productService;

    @Before
    public void setUp() throws Exception {
        productService = new ProductServiceImpl(productRepository, new ProductMapperImpl());
    }

    @Test
    public void getProductByName() {
        //Given
        String name = "Oranges";
        Mockito.when(productRepository.findByName(name)).thenReturn(new Product(1, "Oranges", "Oranges"));

        //When
        ProductDTO productByName = productService.getProductByName(name);

        //Then
        Mockito.verify(productRepository,  Mockito.times(1)).findByName(name);
        assertNotNull(productByName);
        assertEquals("Oranges", productByName.getName());
    }

    @Test
    public void getAllProducts() {
        //Given
        List<Product> products = List.of(new Product(1, "Oranges", "Oranges"),
                new Product(2, "Apples", "Apples"),
                new Product(3, "Bananas", "Bananas"));
        Mockito.when(productRepository.findAll()).thenReturn(products);

        //When
        List<ProductDTO> productDTOList = productService.getAllProducts();

        //Then
        Mockito.verify(productRepository,  Mockito.times(1)).findAll();
        assertEquals(3, productDTOList.size());
    }
}