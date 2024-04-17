package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Ledger {


    public static void homeScreen(Scanner scanner) {
        /*
         * All
         * Deposits
         * Payments
         * Reports
         *  - MTD
         *  - previous month
         *  - YTD
         *  - previous year
         *  - search by vendor
         *  - previous screen
         * Home
         * */
        System.out.println("""

                [A] Show all entries
                [E] Display only expenses (credit)
                [D] Display only payments (debit)
                [R] Custom reports...
                [H] Home"""
        );
        String input = scanner.nextLine();
        switch (input) {
            case "A", "a" -> displayLedger("none");
            case "E", "e" -> displayLedger("expenses");
            case "D", "d" -> displayLedger("payments");
            case "R", "r" -> customReports(scanner);
        }
    }

    private static void displayLedger(String filter) {
        switch (filter) {
            case "none":
                try (FileReader reader = new FileReader("ledger/transactions.csv")) {
                    BufferedReader br = new BufferedReader(reader);
                    String input;
                    while ((input = br.readLine()) != null) {
                        System.out.println(input);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("File not found");
                }
                break;
            case "expenses":
                try (FileReader reader = new FileReader("ledger/transactions.csv")) {
                    BufferedReader br = new BufferedReader(reader);
                    String input;
                    while ((input = br.readLine()) != null) {
                        String[] categories = input.split("\\|");
                        if (categories[categories.length - 1].contains("-")) {
                            System.out.println(input);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException("File not found");
                }
                break;
            case "payments":
                try (FileReader reader = new FileReader("ledger/transactions.csv")) {
                    BufferedReader br = new BufferedReader(reader);
                    String input;
                    while ((input = br.readLine()) != null) {
                        String[] categories = input.split("\\|");
                        if (!categories[categories.length - 1].contains("-")) {
                            System.out.println(input);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException("File not found");
                }
                break;
        }
    }

    private static void customReports(Scanner scanner) {

    }


    public static void addExpense(String[] depositInfo) {
//        date | time | description | vendor | amount
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm:ss");
        LocalDateTime dt = LocalDateTime.now();
        String formattedDate = dt.format(formatter);
        String description = depositInfo[0];
        String vendor = depositInfo[1];
        double amount = Double.parseDouble(depositInfo[2]);
        System.out.printf("%s | %s | %s | %.2f", formattedDate, description, vendor, amount);
    }


    public static void addPayment(String[] paymentInfo) {
//        date | time | description | vendor | amount
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm:ss");
        LocalDateTime dt = LocalDateTime.now();
        String formattedDate = dt.format(formatter);
        String description = paymentInfo[0];
        String vendor = paymentInfo[1];
        double amount = Double.parseDouble(paymentInfo[2]);
        System.out.printf("%s | %s | %s | %.2f", formattedDate, description, vendor, amount);
    }


}
