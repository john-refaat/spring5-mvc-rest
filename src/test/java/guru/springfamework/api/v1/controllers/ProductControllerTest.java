package guru.springfamework.api.v1.controllers;

import guru.springfamework.api.v1.model.ProductDTO;
import guru.springfamework.services.ProductService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

/**
 * @author john
 * @since 25/04/2024
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    private static final Integer ID_1 = 1;
    private static final Integer ID_2 = 2;
    private static final Integer ID_3 = 3;

    private static final String NAME_1 = "Oranges";
    private static final String NAME_2 = "Apples";
    private static final String NAME_3 = "Bananas";

    private static final String SELF_INK_1 = "/products/oranges";
    private static final String SELF_INK_2 = "/products/apples";
    private static final String SELF_INK_3 = "/products/bananas";

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void getProductList() throws Exception {
        //Given
        Mockito.when(productService.getAllProducts()).thenReturn(List.of(
                new ProductDTO(ID_1, NAME_1, SELF_INK_1),
                new ProductDTO(ID_2, NAME_2, SELF_INK_2),
                new ProductDTO(ID_3, NAME_3, SELF_INK_3)));

        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.products", Matchers.hasSize(3)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].name", Matchers.is(NAME_1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.products[1].name", Matchers.is(NAME_2)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.products[2].name", Matchers.is(NAME_3)));

        Mockito.verify(productService, Mockito.times(1)).getAllProducts();
    }

    @Test
    public void getProductByName() throws Exception {
        //Given

        Mockito.when(productService.getProductByName(NAME_1)).thenReturn(new ProductDTO(ID_1, NAME_1, SELF_INK_1));

        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/" + NAME_1)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(ID_1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(NAME_1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.selfLink", Matchers.is(SELF_INK_1)));

       //Then
        Mockito.verify(productService, Mockito.times(1)).getProductByName(NAME_1);
        Mockito.verifyNoMoreInteractions(productService);
    }
}