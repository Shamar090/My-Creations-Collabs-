package com.techelevator;

import com.techelevator.Products.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CoinboxTest {
    private Coinbox coinbox;
    private Inventory inventory;

    @Before
    public void setUp() {
        inventory = new Inventory(); // Assuming Inventory has a default constructor
        coinbox = new Coinbox(inventory);
    }

    @Test
    public void testAddFunds() {
        coinbox.addFunds(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(100), coinbox.getUserFunds());

        coinbox.addFunds(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(150), coinbox.getUserFunds());
    }
}
