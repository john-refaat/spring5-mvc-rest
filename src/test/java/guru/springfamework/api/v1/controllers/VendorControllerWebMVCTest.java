package guru.springfamework.api.v1.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BasicBeanDescription;
import guru.springfamework.api.v1.mappers.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.services.VendorService;
import guru.springfamework.services.VendorServiceImpl;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

/**
 * @author john
 * @since 07/05/2024
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {VendorController.class})
public class VendorControllerWebMVCTest {

    @MockBean //Provided By Spring Context
    VendorService vendorService;

    @MockBean
    VendorMapper vendorMapper;

    @Autowired
    MockMvc mockMvc;

    VendorDTO vendorDTO_1;
    VendorDTO vendorDTO_2;
    VendorDTO vendorDTO_updated;
    VendorDTO vendorDTO_patch;

    @Before
    public void setUp() throws Exception {
        vendorDTO_1 = new VendorDTO(1, "Vendor 1", "vendor1@gmail.com", "2224567899", VendorController.BASE_PATH + "1");
        vendorDTO_patch = new VendorDTO(1, "foo", "vendor1@gmail.com", "2224567899", VendorController.BASE_PATH + "1");
        vendorDTO_updated = new VendorDTO(1, "Vendor Updated", "updated@gmail.com", "111222333444", VendorController.BASE_PATH + "1");
        vendorDTO_2 = new VendorDTO(2, "Vendor 2", "vendor2@gmail.com", "9998887654", VendorController.BASE_PATH + "2");
    }

    @Test
    public void getVendorList() throws Exception {
        VendorListDTO vendorListDTO = new VendorListDTO(List.of(vendorDTO_1, vendorDTO_2));
        //Given
        BDDMockito.given(vendorService.getVendorList()).willReturn(vendorListDTO);

        //When
        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendors", Matchers.hasSize(2)));

    }

    @Test
    public void getVendorById() throws Exception {
        //Given
        BDDMockito.given(vendorService.getVendorById(ArgumentMatchers.anyInt())).willReturn(vendorDTO_1);

        //When
        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_PATH+"1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.equalTo("Vendor 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.equalTo("vendor1@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", CoreMatchers.equalTo("2224567899")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.self_link", CoreMatchers.equalTo(VendorController.BASE_PATH + "1")));

        //Then
        Mockito.verify(vendorService, Mockito.times(1)).getVendorById(ArgumentMatchers.anyInt());
        Mockito.verifyNoMoreInteractions(vendorService);
    }

    @Test
    public void createVendor() throws Exception {
        //Given
        BDDMockito.given(vendorService.createVendor(ArgumentMatchers.any())).willReturn(vendorDTO_1);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post(VendorController.BASE_PATH).contentType(MediaType.APPLICATION_JSON).content(asJsonString(vendorDTO_1)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.equalTo("Vendor 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.equalTo("vendor1@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", CoreMatchers.equalTo("2224567899")));

        //Then
        Mockito.verify(vendorService, Mockito.times(1)).createVendor(ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(vendorService);
    }

    @Test
    public void updateVendor() throws Exception {
        // Given
        BDDMockito.given(vendorService.updateVendor(ArgumentMatchers.anyInt(), ArgumentMatchers.any(VendorDTO.class)))
                .willReturn(vendorDTO_updated);

        // When
        mockMvc.perform(MockMvcRequestBuilders.put(VendorController.BASE_PATH+"1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(vendorDTO_1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.equalTo(vendorDTO_updated.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.equalTo(vendorDTO_updated.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", CoreMatchers.equalTo(vendorDTO_updated.getPhone())));

        // Then
        Mockito.verify(vendorService, Mockito.times(1)).updateVendor(ArgumentMatchers.anyInt(), ArgumentMatchers.any(VendorDTO.class));
        Mockito.verifyNoMoreInteractions(vendorService);
    }

    @Test
    public void patchVendor() throws Exception {
        // Given
        BDDMockito.given(vendorService.patchVendor(ArgumentMatchers.anyInt(), ArgumentMatchers.any(VendorDTO.class)))
                .willReturn(vendorDTO_patch);

        // When
        mockMvc.perform(MockMvcRequestBuilders.patch(VendorController.BASE_PATH+"1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"foo\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.equalTo(vendorDTO_1.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.equalTo("foo")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.equalTo(vendorDTO_1.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", CoreMatchers.equalTo(vendorDTO_1.getPhone())));

        // Then
        Mockito.verify(vendorService, Mockito.times(1)).patchVendor(ArgumentMatchers.anyInt(), ArgumentMatchers.any(VendorDTO.class));
        Mockito.verifyNoMoreInteractions(vendorService);
    }

    @Test
    public void deleteVendorById() throws Exception {
        // Given

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete(VendorController.BASE_PATH+"1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Then
        Mockito.verify(vendorService, Mockito.times(1)).deleteVendorById(ArgumentMatchers.anyInt());
        Mockito.verifyNoMoreInteractions(vendorService);
    }

    private String asJsonString(VendorDTO vendorDTO1) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(vendorDTO1);
    }


}
