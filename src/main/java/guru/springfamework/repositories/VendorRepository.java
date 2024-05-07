package guru.springfamework.repositories;

import guru.springfamework.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author john
 * @since 04/05/2024
 */
public interface VendorRepository extends JpaRepository<Vendor, Integer> {

}
