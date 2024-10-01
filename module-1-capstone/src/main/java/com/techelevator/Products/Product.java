package com.techelevator.Products;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Product {
    private final String slot;
    private final String name;
    private final BigDecimal price;
    private final String typeName;
    private int amt;

    public Product(String productInformation) {
        String[] separateNames = productInformation.split("\\|");

        this.typeName = separateNames[3];
        this.price = BigDecimal.valueOf(Double.parseDouble(separateNames[2]));
        this.name = separateNames[1];
        this.slot = separateNames[0];
        amt=5;
    }

    public String getSlot() {
        return slot;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }


    public int getAmt() {
        return amt;
    }

    public void amtReduce(){
        amt=amt-1;
    }

    public abstract String purchaseMessage();

    @Override
    public String toString() {
        return  slot  +
                " " + name  +
                " $" + price.setScale(2, RoundingMode.HALF_UP) +
                " " + typeName +
                " " + (this.getAmt()>0? this.getAmt(): "Sold Out");
//        return "slot='" + slot  +
//                " name='" + name  +
//                " price=" + price +
//                " typeName='" + typeName + " " + (this.getAmt()>0? this.getAmt(): "Sold Out");

    }
}



//    public Product(String name, long price) {
//        this.name = name;
//        this.price = price;
//        this.typeName = "";
//        this.slot ="";
//    }

