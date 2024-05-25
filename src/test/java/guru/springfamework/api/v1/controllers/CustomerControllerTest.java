package guru.springfamework.api.v1.controllers;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.services.CustomerService;
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

import java.util.List;

/**
 * @author john
 * @since 27/04/2024
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {
    private static final String BASE_PATH = CustomerController.BASE_PATH;
    public static final String PATH_1 = BASE_PATH + "1";

    private static final Integer ID_1 = 1;
    private static final Integer ID_2 = 2;

    private static final String FNAME_1 = "John";
    private static final String FNAME_2 = "Jane";

    private static final String LNAME_1 = "Smith";
    private static final String LNAME_2 = "Adam";

    private static final String SELF_Link_1 = PATH_1;
    private static final String SELF_Link_2 =BASE_PATH + "2";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        Mockito.when(customerService.getAllCustomers()).thenReturn(List.of(
                new CustomerDTO(ID_1, FNAME_1, LNAME_1, SELF_Link_1),
                new CustomerDTO(ID_2, FNAME_2, LNAME_2, SELF_Link_2)));
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customers", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customers[0].first_name", Matchers.is(FNAME_1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customers[1].first_name", Matchers.is(FNAME_2)));
        Mockito.verify(customerService, Mockito.times(1)).getAllCustomers();
    }

    @Test
    public void getCustomerById() throws Exception {
        //Given
        Mockito.when(customerService.getCustomerById(ID_1)).thenReturn(new CustomerDTO(ID_1, FNAME_1, LNAME_1, SELF_Link_1));

        //When
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_1)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(ID_1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", Matchers.is(FNAME_1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last_name", Matchers.is(LNAME_1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.selfLink", Matchers.is(SELF_Link_1)));

        //Then
        Mockito.verify(customerService, Mockito.times(1)).getCustomerById(ID_1);
        Mockito.verifyNoMoreInteractions(customerService);
    }

    @Test
    public void saveNewCustomer() throws Exception {
        //Given
        Mockito.when(customerService.saveNewCustomer(ArgumentMatchers.any(CustomerDTO.class)))
                .thenReturn(new CustomerDTO(ID_1, FNAME_1, LNAME_1, SELF_Link_1));

        //When
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH).accept(MediaType.APPLICATION_JSON)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("{\"name\":\"" + FNAME_1 + "\"}"))
               .andExpect(MockMvcResultMatchers.status().isCreated())
               .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(ID_1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", Matchers.is(FNAME_1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last_name", Matchers.is(LNAME_1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.selfLink", Matchers.is(SELF_Link_1)));

        //Then
        Mockito.verify(customerService, Mockito.times(1)).saveNewCustomer(ArgumentMatchers.any(CustomerDTO.class));
        Mockito.verifyNoMoreInteractions(customerService);
    }

    @Test
    public void updateCustomer() throws Exception {
        // Given
        Mockito.when(customerService.updateCustomer(ArgumentMatchers.anyInt(), ArgumentMatchers.any(CustomerDTO.class)))
                .thenReturn(new CustomerDTO(ID_1, FNAME_1, LNAME_1, SELF_Link_1));

        // When
        mockMvc.perform(MockMvcRequestBuilders.put(PATH_1).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"first_name\":\"" + FNAME_1  + "\", "
                        +"\"last_name\":\"" + LNAME_1  + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(ID_1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", Matchers.is(FNAME_1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last_name", Matchers.is(LNAME_1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.selfLink", Matchers.is(SELF_Link_1)));
        // Then
        Mockito.verify(customerService, Mockito.times(1)).updateCustomer(ArgumentMatchers.anyInt(), ArgumentMatchers.any(CustomerDTO.class));
        Mockito.verifyNoMoreInteractions(customerService);
    }

    @Test
    public void patchCustomer() throws Exception {
        // Given
        Mockito.when(customerService.patchCustomer(ArgumentMatchers.anyInt(), ArgumentMatchers.any(CustomerDTO.class)))
                .thenReturn(new CustomerDTO(ID_1, FNAME_2, LNAME_1, SELF_Link_1));

        // When
        mockMvc.perform(MockMvcRequestBuilders.patch(PATH_1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"first_name\":\"" + FNAME_1 + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(ID_1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", Matchers.is(FNAME_2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last_name", Matchers.is(LNAME_1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.selfLink", Matchers.is(SELF_Link_1)));

    }

    @Test
    public void deleteCustomerById() throws Exception {
        // Given
        Mockito.doNothing().when(customerService).deleteCustomerById(ArgumentMatchers.anyInt());

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH_1))
               .andExpect(MockMvcResultMatchers.status().isOk());

        // Then
        Mockito.verify(customerService, Mockito.times(1)).deleteCustomerById(ArgumentMatchers.anyInt());
        Mockito.verifyNoMoreInteractions(customerService);
    }

    @Test
    public void getCustomerById_NotFound() throws Exception {
        //Given
        Mockito.when(customerService.getCustomerById(ID_1)).thenThrow(new ResourceNotFoundException("Customer Not Found"));

        //When
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_1).contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isNotFound());


        //Then
        Mockito.verify(customerService, Mockito.times(1)).getCustomerById(ID_1);
        Mockito.verifyNoMoreInteractions(customerService);
    }


}