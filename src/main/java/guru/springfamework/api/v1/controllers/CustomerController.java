package guru.springfamework.api.v1.controllers;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.services.CustomerService;
import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author john
 * @since 25/04/2024
 */
@RequestMapping("/api/v1/customers")
@Controller
public class CustomerController {
    public static final String BASE_PATH = CustomerController.class.getAnnotation(RequestMapping.class).value()[0]+"/";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerListDTO> getAllCustomers() {
        return ResponseEntity.ok(new CustomerListDTO(customerService.getAllCustomers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id) {
        CustomerDTO customerById = customerService.getCustomerById(id) ;
        customerById.setSelfLink(BASE_PATH+customerById.getId());
        return ResponseEntity.ok(customerById);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> saveNewCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO savedCustomer = customerService.saveNewCustomer(customerDTO);
        savedCustomer.setSelfLink(BASE_PATH+savedCustomer.getId());
        return new ResponseEntity<CustomerDTO>(savedCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Integer id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO savedCustomer = customerService.updateCustomer(id, customerDTO);
        savedCustomer.setSelfLink(BASE_PATH+savedCustomer.getId());
        return new ResponseEntity<CustomerDTO>(savedCustomer, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Integer id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO savedCustomer = customerService.patchCustomer(id, customerDTO);
        savedCustomer.setSelfLink(BASE_PATH+savedCustomer.getId());
        return new ResponseEntity<CustomerDTO>(savedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Integer id) {
        customerService.deleteCustomerById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
