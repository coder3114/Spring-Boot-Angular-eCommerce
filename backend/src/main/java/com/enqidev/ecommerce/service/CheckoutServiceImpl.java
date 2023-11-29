package com.enqidev.ecommerce.service;

import com.enqidev.ecommerce.dto.PaymentInfo;
import com.enqidev.ecommerce.exception.ResourceNotFoundException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    public CheckoutServiceImpl(@Value("${stripe.apiKey}") String secretKey) {
        Stripe.apiKey = secretKey;
    }

    @Override
    public Session createPaymentSession(PaymentInfo paymentInfo) {
        try {
            Product product = createProduct(paymentInfo);
            List<Object> lineItems = createLineItems(product);
            Map<String, Object> params = createPaymentParams(lineItems, paymentInfo);

            return Session.create(params);
        } catch (StripeException e) {
            throw new ResourceNotFoundException("Error creating payment session");
        }
    }

    // create a Product using the Stripe API to represent the item being sold
    private Product createProduct(PaymentInfo paymentInfo) throws StripeException {
        Map<String, Object> productParams = new HashMap<>();
        Map<String, Object> defaultPriceParams = new HashMap<>();
        defaultPriceParams.put("currency", paymentInfo.getCurrency());
        defaultPriceParams.put("unit_amount_decimal", paymentInfo.getAmount() * 100);
        productParams.put("name", "Your healthy food");
        productParams.put("default_price_data", defaultPriceParams);

        return Product.create(productParams);
    }

    // line items are a workaround with a single item
    // TODO: dynamically generate line items based on the user's cart at checkout
    private List<Object> createLineItems(Product product) {
        List<Object> lineItems = new ArrayList<>();
        Map<String, Object> lineItem = new HashMap<>();
        lineItem.put("price", product.getDefaultPrice());
        lineItem.put("quantity", 1);
        lineItems.add(lineItem);

        return lineItems;
    }

    private Map<String, Object> createPaymentParams(List<Object> lineItems, PaymentInfo paymentInfo) {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");
        Map<String, Object> params = new HashMap<>();
        params.put("line_items", lineItems);
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("success_url", "http://localhost:4200");
        params.put("mode", "payment");

        return params;
    }

    // Method 1 (ideal from Stripe perspective, but not ideal from SQL perspective)
    // 1. Add each product from SQL product table into Stripe with its own price
    // 2. Add the id of each product in Stripe to SQL product table under a new column called product_id
    // 3. Checkout from front-end raises a request to checkout providing user id only
    // 4. Look up the SQL products in the cart for the given user id
    // 5. Using the product_id reference to create the "line_items" by looking up the price id
    //      via the product_id - Product.retrieve(product_id).getDefaultPrice()
    // 6. Create and return session
    // Outcome - the checkout page will list every item bought and the quantity of each with the prices
    //      taken from Stripe.
    // Cons - Stripe and SQL prices are not in sync.

    // Method 2 (not ideal but can drive the prices from SQL table rather than Stripe)
    // Difference to method 1:
    // 1. Skip step 1
    // 2. Skip step 2
    // 3.4. Same as above
    // 5. Each time when checkout, create the product, using the information about the product in SQL
    //      rather than looking up a product pre-made in Stripe
    // Outcome - price specified in SQl table will be the price used at checkout, no need to keep
    //      both Stripe and SQL prices in sync.
    // Cons - will create duplicate products many many times.

    // Method 3 (in between method 1 and 2 - Create the products, but verify the price matches SQL and update if necessary)
    // Difference to method 1:
    // 5. Whilst looking up the product, look up the price too and if it is not matching the price in SQL,
    //      update it using - Price.retrieve("price_id").setUnitAmountDecimal("price from SQL");
    //      then use the price id reference as before

}
