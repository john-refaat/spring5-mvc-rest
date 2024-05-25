package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
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

    @ApiModelProperty(value = "The name of the vendor", required = true)
    private String name;

    @ApiModelProperty(value = "The Email of the vendor")
    private String email;
    private String phone;

    @JsonProperty("self_link")
    private String selfLink;


}
