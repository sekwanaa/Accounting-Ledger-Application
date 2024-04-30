package com.pluralsight;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean isRunning = true;
            while (isRunning) {
                System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\nWelcome to the Account Ledger Application. \nThe goal of this application is to track expenses and income in the ledger.\n\nWhat would you like to do today?\n\n[E] Add an Expense\n[I] Add Income\n[L] Access Ledger\n[X] Exit\n\nEnter Choice: ");
                String input = scanner.nextLine();
                switch (input) {
                    case "E", "e":
                        String[] collectedDepositInfo = collectInfo(scanner, "expense");
                        if (collectedDepositInfo != null) {
                            Ledger.addExpense(collectedDepositInfo);
                        }
                        break;
                    case "I", "i":
                        String[] collectedPaymentInfo = collectInfo(scanner, "income");
                        if (collectedPaymentInfo != null) {
                            Ledger.addPayment(collectedPaymentInfo);
                        }
                        break;
                    case "L", "l":
                        System.out.print("\nAccessing Ledger");
                        Thread.sleep(500);
                        System.out.print("...");
                        Thread.sleep(1000);
                        System.out.print("...");
                        Thread.sleep(1000);
                        System.out.print("...\n");
                        Thread.sleep(500);
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


    public static String[] collectInfo(Scanner scanner, String type) {
        System.out.println("\n-------NOTE: Enter 'X' at anytime to try again-------\n");
        switch (type) {
            case "expense": {
                System.out.print("\nEnter item description: ");
                String description = scanner.nextLine();
                if (description.equalsIgnoreCase("x")) {
                    return null;
                }
                System.out.print("Enter vendor: ");
                String vendor = scanner.nextLine();
                if (vendor.equalsIgnoreCase("x")) {
                    return null;
                }
                System.out.print("Enter amount: $");
                if (scanner.hasNextDouble()) {
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    return new String[]{description, vendor, Double.toString(amount)};
                } else {
                    String amount = scanner.nextLine();
                    if (amount.equalsIgnoreCase("x")) {
                        return null;
                    }
                    System.out.println("\nSorry, this is not the correct input type.");
                    return null;
                }
            }
            case "income": {
                System.out.print("\nEnter description of your income: ");
                String description = scanner.nextLine();
                if (description.equalsIgnoreCase("x")) {
                    return null;
                }
                System.out.print("Enter vendor: ");
                String vendor = scanner.nextLine();
                if (vendor.equalsIgnoreCase("x")) {
                    return null;
                }
                System.out.print("Enter amount: $");
                if (scanner.hasNextDouble()) {
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    return new String[]{description, vendor, Double.toString(amount)};
                } else {
                    String amount = scanner.nextLine();
                    if (amount.equalsIgnoreCase("x")) {
                        return null;
                    }
                    System.out.println("\nSorry, this is not the correct input type.");
                    return null;
                }
            }
            default:
                return null;
        }
    }
}
