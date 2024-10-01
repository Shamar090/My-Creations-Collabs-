package com.techelevator.Products;

public class Snack extends Product {
    public Snack(String productInformation) {
        super(productInformation);
    }

    @Override
    public String purchaseMessage() {
        return "Enjoy your snack!";
    }
}

