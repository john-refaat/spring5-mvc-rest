package guru.springfamework.api.v1.mappers;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.mapstruct.Mapper;

/**
 * @author john
 * @since 25/04/2024
 */
@Mapper
public interface CustomerMapper {
    CustomerDTO customerToCustomerDTO(Customer customer);
    Customer customerDTOToCustomer(CustomerDTO customerDTO);
}
