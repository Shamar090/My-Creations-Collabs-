package com.techelevator.Products;

public class Drink extends Product{

    public Drink(String productInformation) {
        super(productInformation);
    }

//    public Drink(long price, String name, String slot) {
//        super("Drink", price, name, slot);
//    }


    @Override
    public String purchaseMessage() {
        amtReduce();
        return "Glug Glug, Yum!";
    }
}
