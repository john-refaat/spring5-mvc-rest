package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;

import java.util.List;

/**
 * @author john
 * @since 25/04/2024
 */
public interface CustomerService {

    CustomerDTO getCustomerById(Integer id);
    List<CustomerDTO> getAllCustomers();
    CustomerDTO saveNewCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO);
    CustomerDTO patchCustomer(Integer id, CustomerDTO customerDTO);
    void deleteCustomerById(Integer id);
}
