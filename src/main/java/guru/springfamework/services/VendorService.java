package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;

/**
 * @author john
 * @since 04/05/2024
 */
public interface VendorService {
    VendorListDTO getVendorList();
    VendorDTO getVendorById(Integer id);
    VendorDTO createVendor(VendorDTO vendorDTO);
    VendorDTO updateVendor(Integer id, VendorDTO vendorDTO);
    VendorDTO patchVendor(Integer id, VendorDTO vendorDTO);
    void deleteVendorById(Integer id);

}
