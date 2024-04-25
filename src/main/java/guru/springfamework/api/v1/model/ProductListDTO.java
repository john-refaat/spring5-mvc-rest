package guru.springfamework.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author john
 * @since 25/04/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductListDTO {

    private List<ProductDTO> products;


}
