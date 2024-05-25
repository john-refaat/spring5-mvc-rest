package guru.springfamework.api.v1.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import guru.springfamework.api.v1.mappers.VendorMapper;
import guru.springfamework.api.v1.mappers.VendorMapperImpl;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import guru.springfamework.services.VendorServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

/**
 * @author john
 * @since 04/05/2024
 */
@RunWith(MockitoJUnitRunner.class)
public class VendorControllerTest {

    public static final Integer ID_1 = 1;
    public static final String NAME_1 = "vendor";
    public static final String EMAIL_1 = "vendor@gmail.com";
    public static final String PHONE_1 = "1234567890";

    public static final Integer ID_2 = 2;
    public static final String NAME_2 = "abc";
    public static final String EMAIL_2 = "abc@gmail.com";
    public static final String PHONE_2 = "0987654321";


    @Mock
    private VendorService vendorService;

    @InjectMocks
    private VendorController vendorController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController).build();
    }

    @Test
    public void getVendorList() throws Exception {
        //Given
        Mockito.when(vendorService.getVendorList()).thenReturn(new VendorListDTO(List.of(
                new VendorDTO(ID_1, NAME_1, EMAIL_1, PHONE_1, null),
                new VendorDTO(ID_2, NAME_2, EMAIL_2, PHONE_2, null)
        )));

        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vendors/")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.vendors", Matchers.hasSize(2)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.vendors[0].name", Matchers.is(NAME_1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.vendors[1].name", Matchers.is(NAME_2)));

        //Then
        Mockito.verify(vendorService, Mockito.times(1)).getVendorList();
        Mockito.verifyNoMoreInteractions(vendorService);

    }

    @Test
    public void getVendorById() throws Exception {
        //Given
        Mockito.when(vendorService.getVendorById(ArgumentMatchers.anyInt()))
                .thenReturn(new VendorDTO(ID_1, NAME_1, EMAIL_1, PHONE_1, ""));

        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vendors/1")
                .accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(NAME_1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(EMAIL_1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.phone", Matchers.is(PHONE_1)));

        //Then
        Mockito.verify(vendorService, Mockito.times(1)).getVendorById(ArgumentMatchers.anyInt());
        Mockito.verifyNoMoreInteractions(vendorService);
    }

    @Test
    public void createVendor() throws Exception {
        // Given
        Mockito.when(vendorService.createVendor(ArgumentMatchers.any(VendorDTO.class)))
                .thenReturn(new VendorDTO(ID_1, NAME_1, EMAIL_1, PHONE_1, ""));
        VendorDTO vendorDTO = new VendorDTO(null, NAME_1, EMAIL_1, PHONE_1, null);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/vendors/").accept(MediaType.APPLICATION_JSON)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(vendorDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(ID_1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(NAME_1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(EMAIL_1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", Matchers.is(PHONE_1)));

        Mockito.verify(vendorService, Mockito.times(1)).createVendor(ArgumentMatchers.any());
    }

    private String asJsonString(VendorDTO vendorDTO) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(vendorDTO);
    }
}
