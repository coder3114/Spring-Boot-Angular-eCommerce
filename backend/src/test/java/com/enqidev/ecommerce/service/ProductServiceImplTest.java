package com.enqidev.ecommerce.service;

import com.enqidev.ecommerce.entity.Product;
import com.enqidev.ecommerce.exception.ResourceNotFoundException;
import com.enqidev.ecommerce.repository.ProductRepository;
import com.enqidev.ecommerce.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {

    @Test
    public void testGetAllProducts() {
        final ProductRepository repository = mock(ProductRepository.class);
        final ProductServiceImpl service = new ProductServiceImpl(repository);
        final List<Product> productList = TestUtil.getProductList();

        when(repository.findAll()).thenReturn(productList);

        assertEquals(productList, service.getAllProducts());
    }

    @Test
    @DisplayName("should return the new product when a product is created")
    public void testCreateProduct() {
        final ProductRepository repository = mock(ProductRepository.class);
        final ProductServiceImpl service = new ProductServiceImpl(repository);
        final Product saveProduct = new Product();

        when(repository.save(saveProduct)).thenReturn(saveProduct);

        assertEquals(saveProduct, service.createProduct(saveProduct));
    }

    @Test
    @DisplayName("should return the product when its id gets called")
    public void testGetProductById() {
        final ProductRepository repository = mock(ProductRepository.class);
        final ProductServiceImpl service = new ProductServiceImpl(repository);
        final Product searchProduct = new Product();

        when(repository.findById(searchProduct.getId())).thenReturn(Optional.of(searchProduct));

        assertEquals(searchProduct, service.getProductById(searchProduct.getId()));
    }
    
    @Test
    @DisplayName("should throw error if product id not found")
    public void testGetProductByIdNotFound() {
        final ProductRepository repository = mock(ProductRepository.class);
        final ProductServiceImpl service = new ProductServiceImpl(repository);

        when(repository.findById(null)).thenReturn(Optional.empty());

        final Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.getProductById(null);
        });
        assertEquals("Errorrrr", exception.getMessage());
    }

}
