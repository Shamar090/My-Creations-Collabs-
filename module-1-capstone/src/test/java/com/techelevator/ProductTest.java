package com.techelevator.Products;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ProductTest {
    private Product product;

    @Before
    public void setUp() {
        product = new Snack("A1|Chips|1.50|Snack");
    }

    @Test
    public void testProductInitialization() {
        assertEquals("A1", product.getSlot());
        assertEquals("Chips", product.getName());
        assertEquals(1.50, product.getPrice(), 0);
        assertEquals("Snack", product.getTypeName());
        assertEquals(5, product.getAmt());
    }

    @Test
    public void testAmtReduce() {
        product.amtReduce();
        assertEquals(4, product.getAmt());
    }

    @Test
    public void testPurchaseMessage() {
        assertEquals("Enjoy your snack!", product.purchaseMessage());
    }

    @Test
    public void testToString() {
        String expected = "A1 Chips $1.5 Snack 5";
        assertEquals(expected, product.toString());
        // Test when amount is 0
        for (int i = 0; i < 5; i++) {
            product.amtReduce();
        }
        expected = "A1 Chips $1.5 Snack Sold Out";
        assertEquals(expected, product.toString());
    }
}
