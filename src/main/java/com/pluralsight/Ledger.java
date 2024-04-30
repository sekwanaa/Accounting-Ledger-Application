package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Ledger {


    public static void homeScreen(Scanner scanner) {
        boolean isRunning = true;
        while(isRunning) {
            System.out.print("""

                [A] Show all entries
                [E] Display only expenses (credit)
                [D] Display only payments (debit)
                [R] Reports...
                [H] Home
                
                Enter Choice:\s""");
            String input = scanner.nextLine();
            switch (input) {
                case "A", "a" -> getLedgerData("none");
                case "E", "e" -> getLedgerData("expenses");
                case "D", "d" -> getLedgerData("payments");
                case "R", "r" -> customReports(scanner);
                case "H", "h" -> isRunning = false;
                default -> System.out.println("Please enter a valid choice...");
            }
        }
    }


    private static List<String> getLedgerData() {
        List<String> ledgerData = new ArrayList<>();
        try (FileReader reader = new FileReader("ledger/transactions.csv")) {
            BufferedReader br = new BufferedReader(reader);
            String input;
            while ((input = br.readLine()) != null) {
                ledgerData.add(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("File not found");
        }
        return ledgerData;
    }

    private static void getLedgerData(String filter) {
        List<String> ledgerData = new ArrayList<>();
        try (FileReader reader = new FileReader("ledger/transactions.csv")) {
            BufferedReader br = new BufferedReader(reader);
            String input;
            switch (filter) {
                case "none":
                    while ((input = br.readLine()) != null) {
                        ledgerData.add(input);
                    }
                    displayLedgerData(ledgerData);
                    break;
                case "expenses":
                    while ((input = br.readLine()) != null) {
                        String[] categories = input.split("\\|");
                        if (categories[categories.length - 1].contains("-")) {
                            ledgerData.add(input);
                        }
                    }
                    displayLedgerData(ledgerData);
                    break;
                case "payments":
                    while ((input = br.readLine()) != null) {
                        String[] categories = input.split("\\|");
                        if (!categories[categories.length - 1].contains("-")) {
                            ledgerData.add(input);
                        }
                    }
                    displayLedgerData(ledgerData);
                    break;
            }
        } catch (IOException e) {
            System.err.println("File not found");
        }
    }

    private static void displayLedgerData(List<String> ledgerData) {
        System.out.println("\n----------------------------TRANSACTIONS----------------------------\n");
        for (String transaction : ledgerData) {
            if (transaction != null) {
                System.out.println(transaction);
            }
        }
        System.out.println("\n--------------------------------------------------------------------");
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
            int customReportChoice = scanner.nextInt();
            scanner.nextLine();

            LocalDate date = LocalDate.now();
            int month = date.getMonthValue();
            int year = date.getYear();
            List<String> ledger = getLedgerData();
//            StringBuilder output = new StringBuilder();
            List<String> output = new ArrayList<>();
            switch (customReportChoice) {
                case 1:
                    for (String transaction : ledger) {
                        LocalDate transactionDate = LocalDate.parse(transaction.split("\\|")[0]);
                        if (transactionDate.getMonthValue() == month && transactionDate.getYear() == year) {
                            output.add(transaction);
                        }
                    }
                    break;
                case 2:
                    for (String transaction : ledger) {
                        LocalDate transactionDate = LocalDate.parse(transaction.split("\\|")[0]);
                        if (transactionDate.getMonthValue() == month-1 && transactionDate.getYear() == year) {
                            output.add(transaction);
                        }
                    }
                    break;
                case 3:
                    for (String transaction : ledger) {
                        LocalDate transactionDate = LocalDate.parse(transaction.split("\\|")[0]);
                        if (transactionDate.getYear() == year) {
                            output.add(transaction);
                        }
                    }
                    break;
                case 4:
                    for (String transaction : ledger) {
                        LocalDate transactionDate = LocalDate.parse(transaction.split("\\|")[0]);
                        if (transactionDate.getYear() == year - 1) {
                            output.add(transaction);
                        }
                    }
                    break;
                case 5:
                    System.out.print("\nPlease enter the vendor name: ");
                    String vendorName = scanner.nextLine().toLowerCase();
                    for (String transaction : ledger) {
                        String vendor = transaction.split("\\|")[3].toLowerCase();
                        if (vendor.contains(vendorName)) {
                            output.add(transaction);
                        }
                    }
                    break;

                case 6:
                    System.out.println("\nPlease leave field blank if you do not want to search with that filter...");
                    Map<String, String> filters = new HashMap<>();
                    String startDateSearch = "";
                    String endDateSearch = "";
                    try {
                        System.out.print("Start Date (yyyy-mm-dd): ");
                        startDateSearch = scanner.nextLine();
                        System.out.print("End Date (yyyy-mm-dd): ");
                        endDateSearch = scanner.nextLine();
                    } catch (DateTimeParseException e) {
                        System.out.println("There was an error parsing the given date...");
                    }
                    System.out.print("Description: ");
                    String descriptionSearch = scanner.hasNextLine() ? scanner.nextLine() : "";
                    System.out.print("Vendor: ");
                    String vendorSearch = scanner.hasNextLine() ? scanner.nextLine() : "";
                    System.out.print("Amount:  ");
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

                    List<String> filteredLedger = filterSearch(filters);
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


    public static List<String> filterSearch(Map<String, String> filters) {
        List<String> filteredData = new ArrayList<>();
        List<String> data = getLedgerData();
        for (String row : data) {
            String[] categories = row.split("\\|");

            try {
                if (!LocalDate.parse(categories[0]).isAfter(LocalDate.parse(filters.getOrDefault("startDateSearch", "")))) {
                    continue;
                }
                if (!LocalDate.parse(categories[0]).isBefore(LocalDate.parse(filters.getOrDefault("endDateSearch", "")))) {
                    continue;
                }
            } catch (DateTimeParseException ignored) {}

            if (!categories[2].contains(filters.getOrDefault("descriptionSearch", ""))) {
                continue;
            }
            if (!categories[3].contains(filters.getOrDefault("vendorSearch", ""))) {
                continue;
            }
            if (!categories[4].contains(filters.getOrDefault("amountSearch", ""))) {
                continue;
            }
            filteredData.add(row);
        }
        return filteredData;
    }


        public static void addExpense(String[] depositInfo) {
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
        } catch (Exception e) {
            throw new RuntimeException("Sorry there was an issue entering your expense" + e);
        }
    }


    public static void addPayment(String[] paymentInfo) {
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
}
