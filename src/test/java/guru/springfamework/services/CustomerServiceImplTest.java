package guru.springfamework.services;

import guru.springfamework.api.v1.mappers.CustomerMapper;
import guru.springfamework.api.v1.mappers.CustomerMapperImpl;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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

    private static final String FNAME_1 = "John";
    private static final String FNAME_2 = "Jane";

    private static final String LNAME_1 = "Smith";
    private static final String LNAME_2 = "Adam";

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
                .thenReturn(Optional.of(new Customer(ID_1, FNAME_1, LNAME_1)));

        CustomerDTO customerById = customerService.getCustomerById(ID_1);

        assertEquals(ID_1, customerById.getId());
        assertEquals(FNAME_1, customerById.getFirstName());
        assertEquals(LNAME_1, customerById.getLastName());
    }

    @Test
    public void getAllCustomers() {
        Mockito.when(customerRepository.findAll()).thenReturn(List.of(
                new Customer(ID_1, FNAME_1, LNAME_1),
                new Customer(ID_2, FNAME_2, LNAME_2)));

        List<CustomerDTO> allCustomers = customerService.getAllCustomers();

        assertEquals(2, allCustomers.size());
        assertEquals(ID_1, allCustomers.get(0).getId());
        assertEquals(FNAME_1, allCustomers.get(0).getFirstName());
        assertEquals(LNAME_1, allCustomers.get(0).getLastName());
        assertEquals(ID_2, allCustomers.get(1).getId());
        assertEquals(FNAME_2, allCustomers.get(1).getFirstName());
        assertEquals(LNAME_2, allCustomers.get(1).getLastName());
    }

    @Test
    public void saveNewCustomer() {
        //given
        CustomerDTO customerDTO = new CustomerDTO(null, FNAME_1, LNAME_1, SELF_Link_1);

        //when
        Mockito.when(customerRepository.save(ArgumentMatchers.any(Customer.class)))
                .thenReturn(new Customer(1, FNAME_1, LNAME_1));
        CustomerDTO savedCustomer = customerService.saveNewCustomer(customerDTO);

        //then
        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getId());
        assertEquals(ID_1, savedCustomer.getId());
        assertEquals(FNAME_1, savedCustomer.getFirstName());
        assertEquals(LNAME_1, savedCustomer.getLastName());
        assertNull(SELF_Link_1, savedCustomer.getSelfLink());
    }

    @Test
    public void updateCustomer() {
        //given
        CustomerDTO customerDTO = new CustomerDTO(null, FNAME_1, LNAME_1, LNAME_1);

        //when
        Mockito.when(customerRepository.save(ArgumentMatchers.any(Customer.class)))
                .thenReturn(new Customer(ID_1, FNAME_1, LNAME_1));
        CustomerDTO savedCustomer = customerService.updateCustomer(ID_1, customerDTO);

        //then
        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getId());
        assertEquals(ID_1, savedCustomer.getId());
        assertEquals(FNAME_1, savedCustomer.getFirstName());
        assertEquals(LNAME_1, savedCustomer.getLastName());
        assertNull(savedCustomer.getSelfLink());
    }

    @Test
    public void patchCustomer() {
        //given
        CustomerDTO customerDTO = new CustomerDTO(null, FNAME_2, null, null);

        //when
        Mockito.when(customerRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(new Customer(ID_1, FNAME_1, LNAME_1)));
        Mockito.when(customerRepository.save(ArgumentMatchers.any(Customer.class))).thenReturn(new Customer(ID_1, FNAME_2, LNAME_1));

        CustomerDTO savedCustomer = customerService.patchCustomer(ID_1, customerDTO);

        //then
        Mockito.verify(customerRepository, Mockito.times(1)).findById(ArgumentMatchers.anyInt());
        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        Mockito.verify(customerRepository, Mockito.times(1)).save(captor.capture());
        assertNotNull(captor.getValue());
        assertEquals(FNAME_2, captor.getValue().getFirstName());
        assertNotEquals(FNAME_1, captor.getValue().getFirstName());

        assertEquals(LNAME_1, captor.getValue().getLastName());
        assertEquals(ID_1, captor.getValue().getId());

        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getId());
        assertEquals(ID_1, savedCustomer.getId());
        assertEquals(FNAME_2, savedCustomer.getFirstName());
        assertEquals(LNAME_1, savedCustomer.getLastName());
        assertNull(savedCustomer.getSelfLink());

    }

    @Test
    public void deleteCustomerById() {
        customerService.deleteCustomerById(ID_1);
        Mockito.verify(customerRepository, Mockito.times(1)).deleteById(ArgumentMatchers.anyInt());
    }
}
