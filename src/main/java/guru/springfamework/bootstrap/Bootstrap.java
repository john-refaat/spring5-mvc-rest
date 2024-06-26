package guru.springfamework.bootstrap;

import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Product;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.ProductRepository;
import guru.springfamework.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jt on 9/24/17.
 */
@Slf4j
@Component
public class Bootstrap implements CommandLineRunner{

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(ProductRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.productRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Started in Bootstrap");
        List<Product> products = new ArrayList<>();

        products.add(new Product(18, "Apple-Orange", "/shop/v2/products/18"));
        products.add(new Product(1, "Banana", "/shop/v2/products/1"));
        products.add(new Product(2, "Blackberry", "/shop/v2/products/2"));
        products.add(new Product(3, "Cherry", "/shop/v2/products/3"));
        products.add(new Product(4, "Coconut", "/shop/v2/products/4"));
        products.add(new Product(5, "Dragon-Fruit", "/shop/v2/products/5"));
        products.add(new Product(6, "Fig", "/shop/v2/products/6"));
        products.add(new Product(7, "Gac-Fruit", "/shop/v2/products/7"));
        products.add(new Product(8, "Grapes", "/shop/v2/products/8"));
        products.add(new Product(9, "Horn-Cucumber", "/shop/v2/products/9"));
        products.add(new Product(10, "Lychee", "/shop/v2/products/10"));
        products.add(new Product(11, "Orange", "/shop/v2/products/11"));
        products.add(new Product(13, "Papaya", "/shop/v2/products/13"));
        products.add(new Product(14, "Persimmon", "/shop/v2/products/14"));
        products.add(new Product(15, "Physalis", "/shop/v2/products/15"));
        products.add(new Product(16, "Pineapple", "/shop/v2/products/16"));
        products.add(new Product(17, "Pineapple-Slice", "/shop/v2/products/17"));
        products.add(new Product(12, "Rambutan", "/shop/v2/products/12"));
        productRepository.saveAll(products);
        log.info("Products saved successfully");
        log.info("Products Loaded = {}", productRepository.count());


        List<Customer> customers = new ArrayList<Customer>();
        customers.add(new Customer(1, "John", "Smith"));
        customers.add(new Customer(2, "Jane", "Adam"));
        customers.add(new Customer(3, "Mary", "Frank"));
        customers.add(new Customer(4, "Peter", "Guzman"));
        customerRepository.saveAll(customers);
        log.info("Customers saved successfully");
        log.info("Customers Loaded = {}", customerRepository.count());

        List<Vendor> vendors = new ArrayList<Vendor>();
        vendors.add(new Vendor(1, "Exotics Fruit Lair Ltd.", "exotics.fruits@example.com", "7778889994"));
        vendors.add(new Vendor(2, "Max Obsthof GmbHs", "max.obsthof@example.com", "4445556789"));
        vendors.add(new Vendor(3, "True Fruits Inc.", "truefruits@example.com", "2223334567"));
        vendorRepository.saveAll(vendors);
        log.info("Vendors saved successfully");
        log.info("Vendors Loaded = {}", vendorRepository.count());
        log.info("Finished in Bootstrap");
    }
}
