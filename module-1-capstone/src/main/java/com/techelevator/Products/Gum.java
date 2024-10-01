package com.techelevator.Products;

public class Gum extends Product{

    public Gum(String productInformation) {
        super(productInformation);
    }
//    public Gum(long price, String name, String slot) {
//        super("Gum", price, name, slot);
//    }

    @Override
    public String purchaseMessage() {
        amtReduce();
        return "Chew Chew, Yum!";
    }
}
