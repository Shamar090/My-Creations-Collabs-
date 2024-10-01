package com.techelevator.Products;

public class Candy extends Product{


//    public Candy(long price, String name, String slot) {
//        super("Candy", price, name, slot);
//    }

    public Candy(String productString) {
        super(productString);
    }

    @Override
    public String purchaseMessage() {
        amtReduce();
        return "Munch Munch, Yum!";
    }
}
