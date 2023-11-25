package com.enqidev.ecommerce;

import com.enqidev.ecommerce.entity.Product;
import com.enqidev.ecommerce.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@Builder
public class ProductControllerWebMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;


    @BeforeEach
//    void init() {
//        product = new Product(1L, "name", "description",
//                "imageUrl", 9.99, false, "ingredients", 15, "2023-11-22", "mealType", false, false, false, false, false);
//    }

    @AfterEach
    void tearDown() {
        product = null;
    }

    @ParameterizedTest
    @DisplayName("Test for getAllProducts()")
    public void givenListOfProducts_whenGetAllProducts_thenReturnProductsList() throws Exception {
//        // given - precondition or setup
//        List<Product> listOfProducts = new ArrayList<>(Arrays.asList(new Product(1L, "Quinoa Salad with Avocado", "A light and healthy quinoa salad packed with fresh vegetables and tossed in a refreshing lemon vinaigrette.",
//                "assets/images/products/Quinoa-Salad-with-Avocado.jpeg", 9.99, true, "Quinoa, Avocado, Cherry Tomatoes, Cucumber, Red Onion, Parsley, Lemon Vinaigrette", 15, "2023-11-22", "Lunch", false, false, true, true, true)));
////
//        given(productService.getAllProducts()).willReturn(listOfProducts);
//
//        // when -  action or the behaviour that we are going test
//        ResultActions response = mockMvc.perform(get("/api/products"));
//
//        // then - verify the result or output using assert statements
//        response.andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$.size()",
//                        is(listOfProducts.size())));
    }

    @Test
    public void givenProductsObject_whenCreateProduct_thenReturnSavedProduct() throws Exception {
    }

    @Test
    public void givenProductId_whenGetProductById_thenReturnProductObject() throws Exception {
    }

    @Test
    public void givenInvalidProductId_whenGetProductById_thenReturnEmpty() throws Exception {
    }

    @Test
    public void givenUpdatedProduct_whenUpdateProduct_thenReturnUpdateProductObject() throws Exception {
    }

    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
    }


}
