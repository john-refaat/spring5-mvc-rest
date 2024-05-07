package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author john
 * @since 04/05/2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {
    private Integer id;
    private String name;
    private String email;
    private String phone;

    @JsonProperty("self_link")
    private String selfLink;


}
