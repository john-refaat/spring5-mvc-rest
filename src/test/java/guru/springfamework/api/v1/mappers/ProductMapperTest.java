package guru.springfamework.api.v1.mappers;

import guru.springfamework.api.v1.model.ProductDTO;
import guru.springfamework.domain.Product;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author john
 * @since 24/04/2024
 */
public class ProductMapperTest {

    @Test
    public void productToProductDTO() {
        //given
        ProductMapper productMapper = new ProductMapperImpl();

        //when
        ProductDTO productDTO = productMapper.productToProductDTO(new Product(1, "Oranges", "/products/1"));

        //then
        assertNotNull(productDTO);
        assertEquals(1, productDTO.getId().intValue());
        assertEquals("Oranges", productDTO.getName());
        assertEquals("/products/1", productDTO.getSelfLink());
    }
}