package guru.springfamework.bootstrap;

import guru.springfamework.domain.Product;
import guru.springfamework.repositories.ProductRepository;
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

    private ProductRepository categoryRepository;

    public Bootstrap(ProductRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
        categoryRepository.saveAll(products);

        System.out.println("Data Loaded = " + categoryRepository.count() );

    }
}
