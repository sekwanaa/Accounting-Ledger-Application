package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Ledger {


    public static void homeScreen(Scanner scanner) {
        boolean isRunning = true;
        while(isRunning) {
            System.out.print("""

                [A] Show all entries
                [E] Display only expenses
                [I] Display only income
                [R] Custom Reports...
                [H] Home
                
                Enter Choice:\s""");
            String input = scanner.nextLine();
            switch (input) {
                case "A", "a" -> getLedgerData("none");
                case "E", "e" -> getLedgerData("expenses");
                case "I", "i" -> getLedgerData("income");
                case "R", "r" -> customReports(scanner);
                case "H", "h" -> isRunning = false;
                default -> System.out.println("Please enter a valid choice...");
            }
        }
    }


    private static List<Transaction> getLedgerData() {
        List<Transaction> ledgerData = new ArrayList<>();
        try (FileReader reader = new FileReader("ledger/transactions.csv")) {
            BufferedReader br = new BufferedReader(reader);
            String input;
            while ((input = br.readLine()) != null) {
                String[] categories = input.split("\\|");
                Transaction transaction = new Transaction(LocalDate.parse(categories[0]), LocalTime.parse(categories[1]), categories[2], categories[3], Double.parseDouble(categories[4]));
                ledgerData.add(transaction);
            }
        } catch (IOException e) {
            throw new RuntimeException("File not found");
        }
        return ledgerData;
    }

    private static void getLedgerData(String filter) {
        List<Transaction> ledgerData = new ArrayList<>();
        try (FileReader reader = new FileReader("ledger/transactions.csv")) {
            BufferedReader br = new BufferedReader(reader);
            String input;
            switch (filter) {
                case "none":
                    while ((input = br.readLine()) != null) {
                        String[] categories = input.split("\\|");
                        Transaction transaction = new Transaction(LocalDate.parse(categories[0]), LocalTime.parse(categories[1]), categories[2], categories[3], Double.parseDouble(categories[4]));
                        ledgerData.add(transaction);
                    }
                    displayLedgerData(ledgerData);
                    break;
                case "expenses":
                    while ((input = br.readLine()) != null) {
                        String[] categories = input.split("\\|");
                        Transaction transaction = new Transaction(LocalDate.parse(categories[0]), LocalTime.parse(categories[1]), categories[2], categories[3], Double.parseDouble(categories[4]));
                        if (transaction.amount() < 0) {
                            ledgerData.add(transaction);
                        }
                    }
                    displayLedgerData(ledgerData);
                    break;
                case "income":
                    while ((input = br.readLine()) != null) {
                        String[] categories = input.split("\\|");
                        Transaction transaction = new Transaction(LocalDate.parse(categories[0]), LocalTime.parse(categories[1]), categories[2], categories[3], Double.parseDouble(categories[4]));
                        if (transaction.amount() > 0) {
                            ledgerData.add(transaction);
                        }
                    }
                    displayLedgerData(ledgerData);
                    break;
            }
        } catch (IOException e) {
            System.err.println("File not found");
        }
    }

    private static void displayLedgerData(List<Transaction> ledgerData) {
        System.out.println("\n-----------------TRANSACTIONS-----------------");
        System.out.println("Total transactions: " + ledgerData.size());
        for (Transaction transaction : ledgerData) {
            if (transaction != null) {
                System.out.printf("""
                        ==============++==============================
                        | Date        || %s
                        | Time        || %s
                        | Description || %s
                        | Vendor      || %s
                        | Amount      || %.2f
                        """, transaction.date(), transaction.time(), transaction.description(), transaction.vendor(), transaction.amount());
            }
        }
        System.out.println("==============++==============================");
    }

    private static void customReports(Scanner scanner) {
        boolean isRunning = true;
        while (isRunning) {
            System.out.print("""
                
                Which custom report would you like to view?
                [1] Month to date
                [2] Previous month
                [3] Year to date
                [4] Previous year
                [5] Search by vendor
                [6] Custom search...
                [0] Previous screen
                
                Enter Choice:\s""");
            int customReportChoice = scanner.hasNextInt() ? scanner.nextInt() : -19962;
            scanner.nextLine();
            if (customReportChoice == -19962) {
                System.out.println("That is not a valid choice...");
                break;
            }

            LocalDate date = LocalDate.now();
            int month = date.getMonthValue();
            int year = date.getYear();
            List<Transaction> ledger = getLedgerData();
//            StringBuilder output = new StringBuilder();
            List<Transaction> output = new ArrayList<>();
            switch (customReportChoice) {
                case 1:
                    for (Transaction transaction : ledger) {
                        LocalDate transactionDate = transaction.date();
                        if (transactionDate.getMonthValue() == month && transactionDate.getYear() == year) {
                            output.add(transaction);
                        }
                    }
                    break;
                case 2:
                    for (Transaction transaction : ledger) {
                        LocalDate transactionDate = transaction.date();
                        if (transactionDate.getMonthValue() == month-1 && transactionDate.getYear() == year) {
                            output.add(transaction);
                        }
                    }
                    break;
                case 3:
                    for (Transaction transaction : ledger) {
                        LocalDate transactionDate = transaction.date();
                        if (transactionDate.getYear() == year) {
                            output.add(transaction);
                        }
                    }
                    break;
                case 4:
                    for (Transaction transaction : ledger) {
                        LocalDate transactionDate = transaction.date();
                        if (transactionDate.getYear() == year - 1) {
                            output.add(transaction);
                        }
                    }
                    break;
                case 5:
                    System.out.print("\nPlease enter the vendor name: ");
                    String vendorName = scanner.nextLine().toLowerCase();
                    for (Transaction transaction : ledger) {
                        String vendor = transaction.vendor().toLowerCase();
                        if (vendor.contains(vendorName)) {
                            output.add(transaction);
                        }
                    }
                    break;

                case 6:
                    System.out.print("\n-----------------Leave field blank if you do not want to search with that filter-----------------\n");
                    System.out.println("-----------------------------------Everything is optional----------------------------------------\n");
                    Map<String, String> filters = new HashMap<>();
                    System.out.print("Start Date i.e 1999-02-18: ");
                    String startDateSearch = scanner.hasNextLine() ? scanner.nextLine() : "";
                    System.out.print("End Date i.e 1997-10-29: ");
                    String endDateSearch = scanner.hasNextLine() ? scanner.nextLine() : "";
                    System.out.print("Transaction description: ");
                    String descriptionSearch = scanner.hasNextLine() ? scanner.nextLine() : "";
                    System.out.print("Transaction vendor/payer: ");
                    String vendorSearch = scanner.hasNextLine() ? scanner.nextLine() : "";
                    System.out.print("Transaction amount:  ");
                    String amountSearch = scanner.hasNextLine() ? scanner.nextLine() : "";

                    if (!Objects.equals(startDateSearch, "")) {
                        filters.put("startDateSearch", startDateSearch);
                    }
                    if (!Objects.equals(endDateSearch, "")) {
                        filters.put("endDateSearch", endDateSearch);
                    }
                    if (!Objects.equals(descriptionSearch, "")) {
                        filters.put("descriptionSearch", descriptionSearch);
                    }
                    if (!Objects.equals(vendorSearch, "")) {
                        filters.put("vendorSearch", vendorSearch);
                    }
                    if (!Objects.equals(amountSearch, "")) {
                        filters.put("amountSearch", amountSearch);
                    }

                    List<Transaction> filteredLedger = filterSearch(filters);
                    output.addAll(filteredLedger);
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Please select a valid option...");
                    break;
            }
            if (output.isEmpty() && customReportChoice != 0) {
                System.out.println("Sorry, there were no transactions matching your search criteria...");
            } else {
                displayLedgerData(output);
            }
        }
    }


    public static List<Transaction> filterSearch(Map<String, String> filters) {
        List<Transaction> filteredData = new ArrayList<>();
        List<Transaction> data = getLedgerData();
        for (Transaction row : data) {
            try {
                if (!row.date().isAfter(LocalDate.parse(filters.getOrDefault("startDateSearch", "")))) {
                    continue;
                }
                if (!row.date().isBefore(LocalDate.parse(filters.getOrDefault("endDateSearch", "")))) {
                    continue;
                }
            } catch (DateTimeParseException ignored) {}

            if (!row.description().contains(filters.getOrDefault("descriptionSearch", ""))) {
                continue;
            }
            if (!row.vendor().contains(filters.getOrDefault("vendorSearch", ""))) {
                continue;
            }
            if (!Double.toString(row.amount()).contains(filters.getOrDefault("amountSearch", ""))) {
                continue;
            }
            filteredData.add(row);
        }
        return filteredData;
    }


        public static void addExpense(String[] depositInfo, Scanner scanner) {
//        date | time | description | vendor | amount
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
        LocalDateTime dt = LocalDateTime.now();
        String formattedDate = dt.format(formatter);
        String description = depositInfo[0];
        String vendor = depositInfo[1];
        double amount = Double.parseDouble(depositInfo[2]);
        String info = String.format("%s|%s|%s|-%.2f\n", formattedDate, description, vendor, amount);
        try {
            enterInfoIntoLedger(info);
            Thread.sleep(500);
            System.out.print("\n...");
            Thread.sleep(1000);
            System.out.print("...");
            Thread.sleep(1000);
            System.out.print("...");
            Thread.sleep(500);
            System.out.println("\n\nSuccessfully added expense!\n\n");
            System.out.println("Press ENTER to continue");
            scanner.nextLine();
        } catch (Exception e) {
            throw new RuntimeException("Sorry there was an issue entering your expense" + e);
        }
    }


    public static void addPayment(String[] paymentInfo, Scanner scanner) {
//        date | time | description | vendor | amount
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
        LocalDateTime dt = LocalDateTime.now();
        String formattedDate = dt.format(formatter);
        String description = paymentInfo[0];
        String vendor = paymentInfo[1];
        double amount = Double.parseDouble(paymentInfo[2]);
        String info = String.format("%s|%s|%s|%.2f\n", formattedDate, description, vendor, amount);
        try {
            enterInfoIntoLedger(info);
            Thread.sleep(500);
            System.out.print("\n...");
            Thread.sleep(1000);
            System.out.print("...");
            Thread.sleep(1000);
            System.out.print("...");
            Thread.sleep(500);
            System.out.println("\n\nSuccessfully added payment!\n\n");
            System.out.println("Press ENTER to continue");
            scanner.nextLine();
        } catch (Exception e) {
            throw new RuntimeException("Sorry there was an issue entering your payment" + e);
        }
    }


    private static void enterInfoIntoLedger(String info) {
        try (FileWriter fileWriter = new FileWriter("ledger/transactions.csv", true)) {
            fileWriter.write(info);
        } catch (IOException e) {
            throw new RuntimeException("File not found: " + e);
        }
    }


    public static void displayBalance() {
        List<Transaction> ledger = getLedgerData();
        LocalDate date = LocalDate.now();
        double expenses = 0;
        double income = 0;
        for (Transaction transaction : ledger) {
            if (transaction.amount() < 0) {
                expenses += Math.abs(transaction.amount());
            } else if (transaction.amount() > 0) {
                income += transaction.amount();
            }
        }
        double totalBalance = income - expenses;
        System.out.printf("""

                ===================++==============
                 Date              || %s
                -------------------++--------------
                 Expenses          || $-%.2f
                 Income            || $ %.2f
                -------------------++--------------
                """, date, expenses, income);
        if (totalBalance < 0) {
            System.out.printf("""
                 Total             || $%.2f
                """, totalBalance);
        } else {
            System.out.printf("""
                 Total             || $ %.2f
                 """, totalBalance);
        }
        System.out.println("===================++==============");
    }
}
