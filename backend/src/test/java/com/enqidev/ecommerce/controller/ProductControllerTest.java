package com.enqidev.ecommerce.controller;

import com.enqidev.ecommerce.entity.Product;
import com.enqidev.ecommerce.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Test
    @DisplayName("should return list of all products with 200 status code")
    public void testGetAllProducts() {
        final ProductService service = mock(ProductService.class);
        final ProductController controller = new ProductController(service);
        final List<Product> productList = new ArrayList<>();

        when(service.getAllProducts()).thenReturn(productList);

        final ResponseEntity expectedResponse = new ResponseEntity(productList, HttpStatus.OK);

        assertEquals(expectedResponse, controller.getAllProducts());
    }

    @Test
    @DisplayName("should call getAllProducts() when called with /api/products")
    public void testGetAllProductsPath() throws Exception {
        final ProductController controller = mock(ProductController.class);
        final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"));
        verify(controller, times(1)).getAllProducts();
    }

}
