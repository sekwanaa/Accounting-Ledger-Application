package com.pluralsight;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean isRunning = true;
            while (isRunning) {
                System.out.println("""
                        
                        Welcome to the Account Ledger Application. What would you like to do today?
                        
                        [E]Add Expense (credit)
                        [P]Make Payment (debit)
                        [L]Ledger
                        [X]Exit
                        """);
                String input = scanner.nextLine();
                switch (input) {
                    case "E", "e":
                        String[] collectedDepositInfo = collectInfo(scanner);
                        Ledger.addExpense(collectedDepositInfo);
                        break;
                    case "P", "p":
                        System.out.println("Making Payment ...\n\n");
                        String[] collectedPaymentInfo = collectInfo(scanner);
                        Ledger.addPayment(collectedPaymentInfo);
                        break;
                    case "L", "l":
                        System.out.println("Accessing Ledger ...\n\n");
                        Ledger.homeScreen(scanner);
                        break;
                    case "X", "x":
                        System.out.println("Exiting ...");
                        isRunning = false;
                        break;
                    default:
                        System.out.println("That is not a valid input, please try again.");
                }
            }
        } catch (InputMismatchException e) {
            throw new RuntimeException("Wrong input type. Please try again" + e);
        }
    }


    public static String[] collectInfo(Scanner scanner) {
        System.out.print("Enter item description: ");
        String description = scanner.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        return new String[]{description, vendor, Double.toString(amount)};
    }


}
