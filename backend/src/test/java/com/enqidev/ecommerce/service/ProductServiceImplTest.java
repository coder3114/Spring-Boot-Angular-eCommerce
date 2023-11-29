package com.enqidev.ecommerce.service;

import com.enqidev.ecommerce.entity.Product;
import com.enqidev.ecommerce.exception.ResourceNotFoundException;
import com.enqidev.ecommerce.repository.ProductRepository;
import com.enqidev.ecommerce.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @Mock
    ProductRepository repository;

    @InjectMocks
    ProductServiceImpl service;

    @Test
    @DisplayName("should return list of all products")
    public void testGetAllProducts() {
        // Mock data for testing
        final List<Product> productList = TestUtil.getProductList();

        // Mock repository responses
        when(repository.findAll()).thenReturn(productList);

        // Perform the service method
        final List<Product> result = service.getAllProducts();

        // Verify interactions
        verify(repository, times(1)).findAll();

        // Assertions
        assertEquals(productList, result);
    }

    @Test
    @DisplayName("should return the new product when a product is created")
    public void testCreateProduct() {

        final Product saveProduct = TestUtil.getProductList().get(1);

        assertEquals(saveProduct, service.createProduct(saveProduct));

        verify(repository, times(1)).save(saveProduct);
    }

    @Test
    @DisplayName("should return the product when its id gets called")
    public void testGetProductById() {

        final Product searchProduct = TestUtil.getProductList().get(1);

        when(repository.findById(searchProduct.getId())).thenReturn(Optional.of(searchProduct));

        assertEquals(searchProduct, service.getProductById(searchProduct.getId()));
    }

    @Test
    @DisplayName("should throw error if product id not found")
    public void testGetProductByIdNotFound() {

        when(repository.findById(null)).thenReturn(Optional.empty());

        final Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.getProductById(null);
        });

        assertEquals("No product found for this id.", exception.getMessage());
    }

    @Test
    @DisplayName("should return the products with the search keyword in name/description")
    public void testGetProductByKeyword() {

        final List<Product> searchKeywordProduct = TestUtil.getProductList();
        final String keyword = "burger";

        when(repository.searchProducts(keyword)).thenReturn(searchKeywordProduct);

        assertEquals(searchKeywordProduct, service.searchProducts(keyword));
    }

    @Test
    @DisplayName("should throw error when no product found for the keyword")
    public void testGetProductByKeywordNotFound() {

        when(repository.searchProducts("exampleKeyword")).thenReturn(Collections.emptyList());

        final Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.searchProducts("exampleKeyword");
        });

        assertEquals("No product found, please try another keyword.", exception.getMessage());
    }

}
