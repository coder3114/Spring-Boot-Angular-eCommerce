package com.enqidev.ecommerce.util;

import com.enqidev.ecommerce.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static List<Product> getProductList() {
        final List<Product> productList = new ArrayList<>();
        final Product exampleProduct1 = new Product("TestProduct");
        final Product exampleProduct2 = new Product("TestProduct");

        productList.add(exampleProduct1);
        productList.add(exampleProduct2);

        return productList;
    }


}
