package com.enqidev.ecommerce.util;

import com.enqidev.ecommerce.entity.Cart;
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

    public static List<Cart> getCartList() {
        final List<Cart> cartList = new ArrayList<>();

        final String userId = "testUser";

        Cart exampleCart1 = new Cart(getProductList().get(0), userId);
        Cart exampleCart2 = new Cart(getProductList().get(1), userId);

        cartList.add(exampleCart1);
        cartList.add(exampleCart2);

        return cartList;
    }

}
