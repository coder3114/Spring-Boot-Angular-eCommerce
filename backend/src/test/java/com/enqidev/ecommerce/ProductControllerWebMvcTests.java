package com.enqidev.ecommerce;

import com.enqidev.ecommerce.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest
public class ProductControllerTests {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void givenListOfProducts_whenGetAllProducts_thenReturnProductsList() throws Exception {
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
