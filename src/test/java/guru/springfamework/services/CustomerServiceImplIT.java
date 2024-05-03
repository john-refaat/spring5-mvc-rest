package guru.springfamework.services;

import guru.springfamework.api.v1.mappers.CustomerMapperImpl;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author john
 * @since 03/05/2024
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;

    CustomerService customerService;

    private static final String FIRST_NAME_1 = "Katherine";
    private static final String LAST_NAME_1 = "Smith";

    private static final String FIRST_NAME_2 = "Ann";
    private static final String LAST_NAME_2 = "Young";

    @Before
    public void setUp() {
        customerService = new CustomerServiceImpl(new CustomerMapperImpl(), customerRepository);
    }

    @Test
    public void patchCustomerUpdateFirstName() throws Exception {
        Customer savedCustomer = customerRepository.save(new Customer(null, FIRST_NAME_1, LAST_NAME_1));

        customerService.patchCustomer(savedCustomer.getId(), new CustomerDTO(null, FIRST_NAME_2, null, null));

        Customer updatedCustomer = customerRepository.findById(savedCustomer.getId()).get();

        Assert.assertNotNull(updatedCustomer);
        Assert.assertEquals(FIRST_NAME_2, updatedCustomer.getFirstName());
        Assert.assertEquals(LAST_NAME_1, updatedCustomer.getLastName());
        Assert.assertEquals(savedCustomer.getId(), updatedCustomer.getId());
    }

    @Test
    public void patchCustomerUpdateLastName() throws Exception {
        Customer savedCustomer = customerRepository.save(new Customer(null, FIRST_NAME_1, LAST_NAME_1));

        customerService.patchCustomer(savedCustomer.getId(), new CustomerDTO(null, null, LAST_NAME_2, null));

        Customer updatedCustomer = customerRepository.findById(savedCustomer.getId()).get();

        Assert.assertNotNull(updatedCustomer);
        Assert.assertEquals(FIRST_NAME_1, updatedCustomer.getFirstName());
        Assert.assertEquals(LAST_NAME_2, updatedCustomer.getLastName());
        Assert.assertEquals(savedCustomer.getId(), updatedCustomer.getId());
    }
}
