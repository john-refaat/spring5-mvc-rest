package guru.springfamework.api.v1.mappers;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author john
 * @since 04/05/2024
 */
public class VendorMapperTest {

    VendorMapper mapper;
    private static final String NAME = "vendor";
    private static final String EMAIL = "vendor@gmail.com";
    private static final String PHONE = "1234567890";
    private static final Integer ID = 1;

    @Before
    public void setUp() throws Exception {
        mapper = new VendorMapperImpl();
    }

    @Test
    public void vendorToVendorDTO() {
        Vendor vendor = new Vendor(ID, NAME, EMAIL, PHONE);
        VendorDTO vendorDTO = mapper.vendorToVendorDTO(vendor);
        assertNotNull(vendorDTO);
        assertEquals(ID, vendorDTO.getId());
        assertEquals(NAME, vendorDTO.getName());
        assertEquals(EMAIL, vendorDTO.getEmail());
        assertEquals(PHONE, vendorDTO.getPhone());
    }

    @Test
    public void vendorDTOToVendor() {
        VendorDTO vendorDTO = new VendorDTO(ID, NAME, EMAIL, PHONE, "");
        Vendor vendor = mapper.vendorDTOToVendor(vendorDTO);
        assertNotNull(vendor);
        assertEquals(ID, vendor.getId());
        assertEquals(NAME, vendor.getName());
        assertEquals(EMAIL, vendor.getEmail());
        assertEquals(PHONE, vendor.getPhone());
    }
}