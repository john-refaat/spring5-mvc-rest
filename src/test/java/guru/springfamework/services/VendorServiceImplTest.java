package guru.springfamework.services;

import guru.springfamework.api.v1.mappers.VendorMapper;
import guru.springfamework.api.v1.mappers.VendorMapperImpl;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author john
 * @since 04/05/2024
 */
@RunWith(MockitoJUnitRunner.class)
public class VendorServiceImplTest {

    private static final Integer ID_1 = 1;
    private static final String NAME_1 = "vendor";
    private static final String EMAIL_1 = "vendor@gmail.com";
    private static final String PHONE_1 = "1234567890";

    private static final Integer ID_2 = 2;
    private static final String NAME_2 = "abc";
    private static final String EMAIL_2 = "abc@gmail.com";
    private static final String PHONE_2 = "0987654321";


    VendorServiceImpl vendorService;

    @Mock
    VendorRepository vendorRepository;

    @Before
    public void setUp() throws Exception {
        vendorService = new VendorServiceImpl(vendorRepository, new VendorMapperImpl());
    }

    @Test
    public void getVendorList() {
        // Given
        List<Vendor> vendorList = List.of(new Vendor(ID_1, NAME_1, EMAIL_1, PHONE_1),
                new Vendor(ID_2, NAME_2, EMAIL_2, PHONE_2));
        Mockito.when(vendorRepository.findAll()).thenReturn(vendorList);

        // When
        VendorListDTO vendorListDTO = vendorService.getVendorList();

        // Then
        Assert.assertNotNull(vendorListDTO.getVendors());
        Assert.assertEquals(2, vendorListDTO.getVendors().size());
        Assert.assertEquals(ID_1, vendorListDTO.getVendors().get(0).getId());
        Assert.assertEquals(NAME_1, vendorListDTO.getVendors().get(0).getName());
        Assert.assertEquals(EMAIL_1, vendorListDTO.getVendors().get(0).getEmail());
        Assert.assertEquals(PHONE_1, vendorListDTO.getVendors().get(0).getPhone());
        Assert.assertEquals(ID_2, vendorListDTO.getVendors().get(1).getId());
        Assert.assertEquals(NAME_2, vendorListDTO.getVendors().get(1).getName());
        Assert.assertEquals(EMAIL_2, vendorListDTO.getVendors().get(1).getEmail());
        Assert.assertEquals(PHONE_2, vendorListDTO.getVendors().get(1).getPhone());
        Mockito.verify(vendorRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(vendorRepository);
    }

    @Test
    public void getVendorById() {
        // Given
        Vendor vendorById = new Vendor(ID_1, NAME_1, EMAIL_1, PHONE_1);
        Mockito.when(vendorRepository.findById(ID_1)).thenReturn(Optional.of(vendorById));

        // When
        VendorDTO vendorDTO = vendorService.getVendorById(ID_1);

        // Then
        Assert.assertNotNull(vendorDTO);
        Assert.assertEquals(ID_1, vendorDTO.getId());
        Assert.assertEquals(NAME_1, vendorDTO.getName());
        Assert.assertEquals(EMAIL_1, vendorDTO.getEmail());
        Assert.assertEquals(PHONE_1, vendorDTO.getPhone());
        Mockito.verify(vendorRepository, Mockito.times(1)).findById(ID_1);
        Mockito.verifyNoMoreInteractions(vendorRepository);
    }
}