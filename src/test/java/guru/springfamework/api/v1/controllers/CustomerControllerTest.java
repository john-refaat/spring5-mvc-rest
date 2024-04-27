package guru.springfamework.api.v1.controllers;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    private static final Integer ID_1 = 1;
    private static final Integer ID_2 = 2;

    private static final String NAME_1 = "John";
    private static final String NAME_2 = "Jane";

    private static final String SELF_Link_1 = "customers/1";
    private static final String SELF_Link_2 = "customers/2";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        Mockito.when(customerService.getAllCustomers()).thenReturn(List.of(
                new CustomerDTO(ID_1, NAME_1, SELF_Link_1),
                new CustomerDTO(ID_2, NAME_2, SELF_Link_2)));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customers", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customers[0].name", Matchers.is(NAME_1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customers[1].name", Matchers.is(NAME_2)));
        Mockito.verify(customerService, Mockito.times(1)).getAllCustomers();
    }

    @Test
    public void getCustomerById() throws Exception {
        //Given
        Mockito.when(customerService.getCustomerById(ID_1)).thenReturn(new CustomerDTO(ID_1, NAME_1, SELF_Link_1));

        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/" + ID_1).contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(ID_1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(NAME_1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.selfLink", Matchers.is(SELF_Link_1)));

        //Then
        Mockito.verify(customerService, Mockito.times(1)).getCustomerById(ID_1);
        Mockito.verifyNoMoreInteractions(customerService);
    }
}