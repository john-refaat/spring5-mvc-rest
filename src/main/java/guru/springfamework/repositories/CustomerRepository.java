package guru.springfamework.repositories;

import guru.springfamework.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author john
 * @since 25/04/2024
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
