package guru.springfamework.services;

import guru.springfamework.api.v1.mappers.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.exceptions.ResourceNotFoundException;
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
                .map(customerMapper::customerToCustomerDTO).findFirst().orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        log.info("Getting All Customers");
        return customerRepository.findAll().stream().map(customerMapper::customerToCustomerDTO).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) {
        log.info("Saving Customer: {}", customerDTO);
        return customerMapper.customerToCustomerDTO(customerRepository.save(customerMapper.customerDTOToCustomer(customerDTO)));
    }

    @Override
    public CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO) {
        log.info("Updating Customer: {}", customerDTO);
        customerDTO.setId(id);
        return customerMapper.customerToCustomerDTO(customerRepository.save(customerMapper.customerDTOToCustomer(customerDTO)));
    }

    @Override
    public CustomerDTO patchCustomer(Integer id, CustomerDTO customerDTO) {
        log.info("Patching Customer: {}", customerDTO);
        Customer patchCustomer = customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        patchCustomer.setId(id);
        if (customerDTO.getFirstName()!= null) {
            patchCustomer.setFirstName(customerDTO.getFirstName());
        }
        if (customerDTO.getLastName()!= null) {
            patchCustomer.setLastName(customerDTO.getLastName());
        }
        return customerMapper.customerToCustomerDTO(customerRepository.save(patchCustomer));
    }

    @Override
    public void deleteCustomerById(Integer id) {
        log.info("Deleting Customer ID: {}", id);
        customerRepository.deleteById(id);
    }

}
