package com.enqidev.ecommerce.controller;

import com.enqidev.ecommerce.entity.Product;
import com.enqidev.ecommerce.repository.ProductRepository;
import com.enqidev.ecommerce.service.ProductService;
import com.enqidev.ecommerce.service.ProductServiceImpl;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;

public class ProductControllerTest {

    final ProductService service = mock(ProductService.class);

    final ProductController controller = mock(ProductController.class);

    final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    @Test
    @DisplayName("should return list of all products with 200 status code")
    public void testGetAllProducts() {

        final ProductController controller = new ProductController(service);
        final List<Product> productList = new ArrayList<>();

        when(service.getAllProducts()).thenReturn(productList);

        final ResponseEntity expectedResponse = new ResponseEntity(productList, HttpStatus.OK);

        assertEquals(expectedResponse, controller.getAllProducts());
    }

    @Test
    @DisplayName("should call getAllProducts() when called with /api/products")
    public void testGetAllProductsPath() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"));

        verify(controller, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("should return the new product when a product is created")
    public void testCreateProduct() {

        final ProductController controller = new ProductController(service);
        final Product saveProduct = new Product("TestProduct");

        when(service.createProduct(saveProduct)).thenReturn(saveProduct);

        assertEquals(saveProduct, controller.createProduct(saveProduct));
    }

    @Test
    @DisplayName("should call createProduct() when called post with /api/products")
    public void testCreateProductPath() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")).andExpect(handler().methodName("createProduct"));
    }

    @Test
    @DisplayName("should return the product with id and 200 status code")
    public void testGetProductById() {

        final ProductController controller = new ProductController(service);
        final Product searchIdProduct = new Product("TestProduct");

        when(service.getProductById(searchIdProduct.getId())).thenReturn(searchIdProduct);

        final ResponseEntity expectedResponse = new ResponseEntity(searchIdProduct, HttpStatus.OK);

        assertEquals(expectedResponse, controller.getProductById(searchIdProduct.getId()));
    }

    @Test
    @DisplayName("should throw an exception when product id not found")
    public void testGetProductByIdThrowsException() {

        final ProductService service = new ProductServiceImpl(mock(ProductRepository.class));
        final ProductController controller = new ProductController(service);

        final ResponseEntity expectedResponse = ResponseEntity.notFound().build();

        assertEquals(expectedResponse, controller.getProductById(null));
    }

    @Test
    @DisplayName("should call getProductById() when called post with /api/products/{id}")
    public void testGetProductByIdPath() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/1")).andExpect(handler().methodName("getProductById"));
    }

    @Test
    @DisplayName("should return the products with the search keyword in name/description and 200 status code")
    public void testGetProductByKeyword() {

        final ProductController controller = new ProductController(service);
        final List<Product> searchKeywordProduct = new ArrayList<>();
        final String keyword = "keyword";

        when(service.searchProducts(keyword)).thenReturn(searchKeywordProduct);

        final ResponseEntity expectedResponse = new ResponseEntity(searchKeywordProduct, HttpStatus.OK);

        assertEquals(expectedResponse, controller.searchProducts(keyword));
    }

    @Test
    @DisplayName("should throw an exception when no product found for the keyword")
    public void testGetProductByKeywordThrowsException() {

        final ProductService service = new ProductServiceImpl(mock(ProductRepository.class));
        final ProductController controller = new ProductController(service);

        final ResponseEntity expectedResponse = ResponseEntity.notFound().build();

        assertEquals(expectedResponse, controller.searchProducts(null));
    }

    @Test
    @DisplayName("should call getProductById() when called get with /api/search/{keyword}")
    public void testGetProductByKeywordPath() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/search/exampleKeyword")).andExpect(handler().methodName("searchProducts"));
    }
}
