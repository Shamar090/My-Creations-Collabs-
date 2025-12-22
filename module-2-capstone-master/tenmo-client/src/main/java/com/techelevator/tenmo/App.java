package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.service.*;
import com.techelevator.tenmo.dto.TransferDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class App {

    // Base URL for API endpoints
    private static final String API_BASE_URL = "http://localhost:8080/";

    // Service objects for console interaction, authentication, and transfers
    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationSvcs authenticationService = new AuthenticationSvcsImpl(API_BASE_URL);
    private final TransferSvcs transferService = new TransferSvcsImpl(API_BASE_URL);
    private final AccountSvcs accountSvcs = new AccountSvcsImpl(API_BASE_URL);

    // Stores the currently authenticated user
    private AuthenticatedUser currentUser;

    // Main method to run the application
    public void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    // Handles the login/register menu
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    // Handles user registration
    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    // Handles user login
    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else {
            String token = currentUser.getToken();
            transferService.setAuthToken(token);
            accountSvcs.setAuthToken(token);
        }
    }

    // Displays and handles the main menu options
    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 6) {
                logout();
                loginMenu();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    // Displays the current balance of the user
    private void viewCurrentBalance() {
        BigDecimal balance = transferService.getBalance();
        consoleService.printBalance(balance);
    }

    // Displays transfer history and allows viewing details of a specific transfer
    private void viewTransferHistory() {
        // TODO Auto-generated method stub
        Map<Integer, Transfer> transfers = null;

        transfers = transferService.getTransferForUser();
        Transfer transfer;
        consoleService.printTransfers();
        if(!transfers.isEmpty()){
            for (Map.Entry<Integer, Transfer> entry: transfers.entrySet()){
                transfer=entry.getValue();
                int transferType = transfer.getTransferTypeId();
                int transferId = transfer.getTransferId();
                String transferDes;
                String fromToUser;

                if(transferService.getAccountByAccId(transfer.getAccountFrom()) == currentUser.getUser().getId()){
                    transferDes =  "To";
                    fromToUser = transferService.getUserNameByUserId(transferService.getAccountByAccId(transfer.getAccountTo()));
                }else{
                    transferDes =  "From";
                    fromToUser = transferService.getUserNameByUserId(transferService.getAccountByAccId(transfer.getAccountFrom()));
                }

                consoleService.printTransfers(transferId, transferDes, fromToUser, transfer.getAmount());
            }
            int selection = consoleService.promptForInt("Please enter transfer ID to view details (0 to cancel): ");

            if ((selection != 0)) {
                viewTransfer(transfers.get(selection));
            }
        }



    }
    private void viewTransfer(Transfer transfer){
        String fromUser = transferService.getUserNameByUserId(transferService.getAccountByAccId(transfer.getAccountFrom()));
        String toUser = transferService.getUserNameByUserId(transferService.getAccountByAccId(transfer.getAccountTo()));
        String type = (transfer.getTransferTypeId()== 1)? "Request" : "Send";
        int statusID = transfer.getTransferStatusId();
        String status;
        if (statusID==1) {
            status= "Pending";
        }else if (statusID==2) {
            status= "Approved";
        }else{
            status= "Rejected";
        }
        consoleService.printTransfer(transfer.getTransferId(), fromUser, toUser, type, status, transfer.getAmount());
    }

    // Displays pending transfer requests and allows approving/rejecting them
    // Handles approving or rejecting a pending transfer
    private void viewPendingRequests() {
        Map<Integer, Transfer> pendingTransfers = null;
        pendingTransfers = transferService.getPendingTransfer();
        consoleService.printPendingTransfers();
        Transfer transfer;
        if (!pendingTransfers.isEmpty()){


            for (Map.Entry<Integer, Transfer> entry: pendingTransfers.entrySet()){
                transfer = entry.getValue();
                int transferId = transfer.getTransferId();
                String toUser =  transferService.getUserNameByUserId(transferService.getAccountByAccId(transfer.getAccountTo()));
                consoleService.printPendingTransfers(transferId, toUser, transfer.getAmount());
            }
            int transferId = consoleService.promptForInt("Please enter transfer ID to approve/reject (0 to cancel): ");

            if (transferId != 0){
                int option = consoleService.transferApproval();
                if (option !=1){
                    try {
                        transferService.updateTransferStatus(transferId,option);
                        System.out.println("Transfer " + (option == 2 ? "approved" : "rejected") + " successfully.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else {
                    System.out.println("No action taken.");
                }
            }
        }
    }

    // Handles sending TE bucks to another user
    private void sendBucks() {
        List<User> users = transferService.getAllUsers();
        consoleService.printUserList(users);

        int receiverId = consoleService.promptForInt("\nEnter ID of user you are sending to (0 to cancel): ");
        if (receiverId != 0 && receiverId != currentUser.getUser().getId()) {
            BigDecimal amount = consoleService.promptForBigDecimal("Enter amount: ");
            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                TransferDto transferDto = new TransferDto();
                transferDto.setReceiverId(receiverId);
                transferDto.setAmount(amount);
                transferDto.setType("Send");
                try {
                    Transfer sentTransfer = transferService.createTransfer(transferDto);
                    System.out.println("Transfer sent successfully. Transfer ID: " + sentTransfer.getTransferId());
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid amount. Please enter a positive amount.");
            }
        } else if (receiverId == currentUser.getUser().getId()) {
            System.out.println("You cannot send money to yourself.");
        }
    }

    // Handles requesting TE bucks from another user
    private void requestBucks() {
        List<User> users = transferService.getAllUsers();
        consoleService.printUserList(users);

        int senderId = consoleService.promptForInt("Enter ID of user you are requesting from (0 to cancel): ");
        if (senderId != 0 && senderId != currentUser.getUser().getId()) {
            BigDecimal amount = consoleService.promptForBigDecimal("Enter amount: ");
            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                TransferDto transferDto = new TransferDto();
                transferDto.setReceiverId(senderId);
                transferDto.setAmount(amount);
                transferDto.setType("Request");
                try {
                    Transfer createdTransfer = transferService.requestTransfer(transferDto);
                    System.out.println("Request sent successfully. Transfer ID: " + createdTransfer.getTransferId());
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid amount. Please enter a positive amount.");
            }
        } else if (senderId == currentUser.getUser().getId()) {
            System.out.println("You cannot request money from yourself.");
        }
    }



    // Handles user logout
    private void logout() {
        currentUser = null;
        transferService.setAuthToken(null);
        System.out.println("You have been logged out. Goodbye!");
    }

    // Main method to start the application
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
}