package guru.springfamework.api.v1.mappers;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.mapstruct.Mapper;

/**
 * @author john
 * @since 04/05/2024
 */
@Mapper
public interface VendorMapper {
    VendorDTO vendorToVendorDTO(Vendor vendor);
    Vendor vendorDTOToVendor(VendorDTO vendorDTO);
}
