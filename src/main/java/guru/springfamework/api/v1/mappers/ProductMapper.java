package guru.springfamework.api.v1.mappers;

import guru.springfamework.api.v1.model.ProductDTO;
import guru.springfamework.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author john
 * @since 24/04/2024
 */
@Mapper
public interface ProductMapper {

    ProductDTO productToProductDTO(Product product);
}
