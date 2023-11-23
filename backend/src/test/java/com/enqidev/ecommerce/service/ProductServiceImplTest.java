package com.enqidev.ecommerce.service;

import com.enqidev.ecommerce.entity.Product;
import com.enqidev.ecommerce.repository.ProductRepository;
import com.enqidev.ecommerce.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

}
