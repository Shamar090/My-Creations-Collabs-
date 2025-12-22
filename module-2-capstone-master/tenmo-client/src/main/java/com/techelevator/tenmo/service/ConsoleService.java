package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

// ConsoleService handles all user interface interactions through the console
public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    // Prompts user for menu selection and returns the selected option as an integer
    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    // Displays the welcome message for TEnmo application
    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    // Prints the login menu options
    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    // Prints the main menu options after successful login
    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("6: Log out");
        System.out.println("0: Exit");
        System.out.println();
    }

    // Prompts for and returns user credentials (username and password)
    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    // Prompts for and returns a string input from the user
    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // Prompts for and returns an integer input from the user, with error handling
    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    // Prompts for and returns a BigDecimal input from the user, with error handling
    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    // Pauses the program and waits for user to press Enter
    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    // Prints a generic error message
    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    // Displays the current account balance
    public void printBalance(BigDecimal balance) {
        System.out.printf("Your current account balance is: $%.2f\n", balance);
    }

    // Displays a list of transfers
    public void printTransfers() {
        System.out.println("-------------------------------------------");
        System.out.println("Transfers");
        System.out.println("ID          From/To                 Amount");
        System.out.println("-------------------------------------------");
    }


    public void printTransfers(int id, String descrip, String username, BigDecimal amount) {
        System.out.print(id + "          ");
        System.out.print(descrip+": " + username);
        System.out.println("          $ " +amount);
    }


    public void printTransfer(int id, String uFrom, String uTo, String type, String status, BigDecimal amount) {
        System.out.println("Id: "+id );
        System.out.println("From: "+uFrom);
        System.out.println("To: "+ uTo);
        System.out.println("Type: " + type);
        System.out.println("Status: "+ status);
        System.out.println("Amount: $"+ amount);
    }








    public void printTransferHistory(List<Transfer> transfers) {
        System.out.println("-------------------------------------------");
        System.out.println("Transfers");
        System.out.println("ID          From/To                 Amount");
        System.out.println("-------------------------------------------");
        for (Transfer transfer : transfers) {
            System.out.printf("%-12d%-22s$%.2f\n", transfer.getTransferId(), transfer.getAccountFrom() + "/" + transfer.getAccountTo(), transfer.getAmount());
        }
        System.out.println("-------------------------------------------");
    }

    // Displays a list of pending transfer requests
    public void printPendingRequests(List<Transfer> pendingTransfers) {
        System.out.println("-------------------------------------------");
        System.out.println("Pending Transfers");
        System.out.println("ID          To                     Amount");
        System.out.println("-------------------------------------------");
        for (Transfer transfer : pendingTransfers) {
            System.out.printf("%-12d%-22s$%.2f\n", transfer.getTransferId(), transfer.getAccountTo(), transfer.getAmount());
        }
        System.out.println("-------------------------------------------");
    }

    public void printPendingTransfers() {
        System.out.println("-------------------------------------------");
        System.out.println("Pending Transfers");
        System.out.println("ID          To                     Amount");
        System.out.println("-------------------------------------------");
    }

    public void printPendingTransfers(int id, String username, BigDecimal amount) {
        System.out.println(id+"          "+username+"                $ "+amount);
    }

    public int transferApproval() {
        System.out.println("\n\n\n1: Pending");
        System.out.println("2: Approved");
        System.out.println("3: Rejected");
        System.out.println("---------");
        return promptForMenuSelection("Please choose an option: ");
    }


    // Displays a list of users
    public void printUserList(List<User> users) {
        System.out.println("-------------------------------------------");
        System.out.println("Users");
        System.out.println("ID          Name");
        System.out.println("-------------------------------------------");
        for (User user : users) {
            System.out.printf("%-12d%s\n", user.getId(), user.getUsername());
        }
        System.out.println("-------------------------------------------");
    }

    // Displays a logout message
    public void printLogoutMessage() {
        System.out.println("You have been logged out. Thank you for using TEnmo!");
    }

    // Displays details of a specific transfer
    public void printTransferDetails(Transfer transfer) {
        System.out.println("--------------------------------------------");
        System.out.println("Transfer Details");
        System.out.println("--------------------------------------------");
        System.out.println("Id: " + transfer.getTransferId());
        System.out.println("From: " + transfer.getAccountFrom());
        System.out.println("To: " + transfer.getAccountTo());
        System.out.println("Type: " + (transfer.getTransferTypeId() == 2 ? "Send" : "Request"));
        System.out.println("Status: " + getTransferStatusDescription(transfer.getTransferStatusId()));
        System.out.printf("Amount: $%.2f\n", transfer.getAmount());
    }

    // Displays the menu for approving or rejecting a transfer
    public void printApproveRejectMenu() {
        System.out.println("1: Approve");
        System.out.println("2: Reject");
        System.out.println("0: Don't approve or reject");
        System.out.println("---------");
    }

    // Displays a success message
    public void printSuccessMessage(String message) {
        System.out.println("Success: " + message);
    }

    // Displays an error message
    public void printErrorMessage(String message) {
        System.out.println("Error: " + message);
    }

    // Converts a transfer status ID to a human-readable description
    private String getTransferStatusDescription(int statusId) {
        switch (statusId) {
            case 1:
                return "Pending";
            case 2:
                return "Approved";
            case 3:
                return "Rejected";
            default:
                return "Unknown";
        }
    }
}