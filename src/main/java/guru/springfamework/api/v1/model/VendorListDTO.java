package guru.springfamework.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author john
 * @since 04/05/2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class  VendorListDTO {

    private List<VendorDTO> vendors;
}
