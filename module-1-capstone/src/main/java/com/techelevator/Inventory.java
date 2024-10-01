package com.techelevator;

import com.techelevator.Products.*;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<Product> productlist = new ArrayList<Product>();

    public Inventory() {
    }

    public void displayAll(){
        System.out.println("Slot   Item Name    Price       Type        Stock");
        for(Product product : productlist){
            System.out.println(product.toString());
        }
    }

    public void addInventory(List<String> productsString){
        //for loop to turn string into products
        for(String productString : productsString) {
            if (productString.charAt(0) == 'A'){
                productlist.add(new Chip(productString));
            } else if (productString.charAt(0) == 'B'){
                productlist.add(new Candy(productString));
            } else if (productString.charAt(0) == 'C'){
                productlist.add(new Drink(productString));
            } else {
                productlist.add(new Gum(productString));
            }
        }
//        for(Product product : productlist) {
//            System.out.println(product.getName());
//            System.out.println(product.getPrice());
//            System.out.println(product.getTypeName());
//            System.out.println(product.getSlot());
//        }
    }

    public Product buyProduct(String slotID){
        for(Product product : productlist){
            if(product.getSlot().equalsIgnoreCase(slotID)){
                return product;
            }
        }
        return null;
    }

    public List<String> productReport(){
        List<String> products = new ArrayList<>();
        for(Product product : productlist){
            products.add(product.getName()+ "|" +(5-product.getAmt()));
        }
        return  products;
    }
}
