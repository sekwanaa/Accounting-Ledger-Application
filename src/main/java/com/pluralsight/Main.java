package com.pluralsight;

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
                        if (collectedDepositInfo != null) {
                            Ledger.addExpense(collectedDepositInfo);
                        }
                        break;
                    case "P", "p":
                        String[] collectedPaymentInfo = collectInfo(scanner);
                        if (collectedPaymentInfo != null) {
                            Ledger.addPayment(collectedPaymentInfo);
                        }
                        break;
                    case "L", "l":
                        System.out.print("\nAccessing Ledger");
                        Thread.sleep(1000);
                        System.out.print("...");
                        Thread.sleep(1000);
                        System.out.print("...");
                        Thread.sleep(1000);
                        System.out.print("...\n");
                        Thread.sleep(1000);
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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static String[] collectInfo(Scanner scanner) {
        System.out.print("Enter item description: ");
        String description = scanner.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter amount: $");
        if (scanner.hasNextDouble()) {
            double amount = scanner.nextDouble();
            scanner.nextLine();
            return new String[]{description, vendor, Double.toString(amount)};
        } else {
            scanner.nextLine();
            System.out.println("\nSorry, this is not the correct input type.");
            return null;
        }
    }


}
