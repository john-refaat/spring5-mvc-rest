package guru.springfamework.services;

import guru.springfamework.api.v1.mappers.CustomerMapper;
import guru.springfamework.api.v1.mappers.CustomerMapperImpl;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.hibernate.validator.constraints.Mod10Check;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author john
 * @since 27/04/2024
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    private static final Integer ID_1 = 1;
    private static final Integer ID_2 = 2;

    private static final String NAME_1 = "John";
    private static final String NAME_2 = "Jane";

    private static final String SELF_Link_1 = "customers/1";
    private static final String SELF_Link_2 = "customers/2";


    @Mock
    CustomerRepository customerRepository;

    CustomerMapper customerMapper;

    CustomerService customerService;


    @Before
    public void setUp() throws Exception {
        customerMapper = new CustomerMapperImpl();
        customerService = new CustomerServiceImpl(customerMapper, customerRepository);
    }

    @Test
    public void getCustomerById() {
        Mockito.when(customerRepository.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(new Customer(ID_1, NAME_1, SELF_Link_1)));

        CustomerDTO customerById = customerService.getCustomerById(ID_1);

        assertEquals(ID_1, customerById.getId());
        assertEquals(NAME_1, customerById.getName());
        assertEquals(SELF_Link_1, customerById.getSelfLink());
    }

    @Test
    public void getAllCustomers() {
        Mockito.when(customerRepository.findAll()).thenReturn(List.of(
                new Customer(ID_1, NAME_1, SELF_Link_1),
                new Customer(ID_2, NAME_2, SELF_Link_2)));

        List<CustomerDTO> allCustomers = customerService.getAllCustomers();

        assertEquals(2, allCustomers.size());
        assertEquals(ID_1, allCustomers.get(0).getId());
        assertEquals(NAME_1, allCustomers.get(0).getName());
        assertEquals(SELF_Link_1, allCustomers.get(0).getSelfLink());
        assertEquals(ID_2, allCustomers.get(1).getId());
        assertEquals(NAME_2, allCustomers.get(1).getName());
        assertEquals(SELF_Link_2, allCustomers.get(1).getSelfLink());
    }
}
