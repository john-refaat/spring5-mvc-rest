package guru.springfamework.services;

import guru.springfamework.api.v1.mappers.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author john
 * @since 25/04/2024
 */
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDTO getCustomerById(Integer id) {
        log.info("Getting Customer ID: {}", id);
        return customerRepository.findById(id).stream()
                .map(customerMapper::customerToCustomerDTO).findFirst().orElseThrow(RuntimeException::new);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        log.info("Getting All Customers");
        return customerRepository.findAll().stream().map(customerMapper::customerToCustomerDTO).collect(Collectors.toList());
    }
}
