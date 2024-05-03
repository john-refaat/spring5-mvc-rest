package guru.springfamework.api.v1.mappers;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author john
 * @since 27/04/2024
 */
public class CustomerMapperTest {

    CustomerMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new CustomerMapperImpl();
    }

    @Test
    public void customerToCustomerDTO() {
        //given
        Customer customer = new Customer(1, "John", "Smith");

        //when
        CustomerDTO customerDTO = mapper.customerToCustomerDTO(customer);

        //then
        assertNotNull(customerDTO);
        assertEquals(1, customerDTO.getId().intValue());
        assertEquals("John", customerDTO.getFirstName());
        assertEquals("Smith", customerDTO.getLastName());

    }

    @Test
    public void customerToCustomerDTONull() {
        //given
        Customer customer = null;

        //when
        CustomerDTO customerDTO = mapper.customerToCustomerDTO(customer);

        //then
        assertNull(customerDTO);
    }

    @Test
    public void customerDTOToCustomer() {
        //given
        CustomerDTO customerDTO = new CustomerDTO(1, "John", "Smith", "/v1/customer/1");

        //when
        Customer customer = mapper.customerDTOToCustomer(customerDTO);

        //then
        assertNotNull(customer);
        assertEquals(1, customer.getId().intValue());
        assertEquals("John", customer.getFirstName());
        assertEquals("Smith", customer.getLastName());
    }

    @Test
    public void customerDTOToCustomerNull() {
        //given
        CustomerDTO customerDTO = null;

        //when
        Customer customer = mapper.customerDTOToCustomer(customerDTO);

        //then
        assertNull(customer);
    }
}
