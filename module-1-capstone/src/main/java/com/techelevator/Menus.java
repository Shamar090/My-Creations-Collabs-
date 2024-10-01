package com.techelevator;

import java.math.BigDecimal;
import java.util.Scanner;

public class Menus {
    private final Scanner input = new Scanner(System.in);
    private int option;
    private int total = 0;




    public int mainMenu(){
        System.out.println("\n\n****************************************************************************");
        System.out.println("OUR VENDING MACHINE");
        System.out.println("****************************************************************************\n");
        System.out.println("(1) Display Vending Machine Items");
        System.out.println("(2) Purchase");
        System.out.println("(3) Exit");
        System.out.print("Please enter your option: ");

        if(input.hasNextInt()){
            option = input.nextInt();
            input.nextLine();
        }else {
            System.out.println(input.nextLine()+ " is an invalid selection");
            mainMenu();
        }

        return option;
    }
    public String buyProduct(){
        System.out.println("Please select the slot of the product you want to buy");
        return input.nextLine();
    }

    public int purchaseMenu(BigDecimal userMoney){
        System.out.println("\n\n****************************************************************************");
        System.out.println("OUR VENDING MACHINE");
        System.out.println("****************************************************************************\n");
        System.out.println("Current Money Provided: $" + userMoney);

        System.out.println("(1) Feed Money");
        System.out.println("(2) Select Product");
        System.out.println("(3) Finish Transaction");
        System.out.print("Please enter your option: ");
        if(input.hasNextInt()){
            option = input.nextInt();
            input.nextLine();
        }else {
            System.out.println(input.nextLine()+ " is an invalid selection");
            purchaseMenu(userMoney);
        }


        return option;
    }

    public void pause(){
        System.out.print("\n\nPlease press enter to continue: ");
        input.nextLine();
    }


    public int feedMenu(){
        int holder=0;
        System.out.println("Please enter $1 $5 $10 $20 $100 one at a time/nAnything letters to exit");

        while (input.hasNextInt()){

            holder = input.nextInt();
            if(!(holder==1||holder==5||holder==10||holder==20||holder==100)){
                System.out.println(holder + " is an invalid money amount");
                continue;
            }

            total = total + holder;
            input.nextLine();
        }
        input.nextLine();
        return total;
    }


}
