package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
        boolean isRunning = true;
        while(isRunning) {
            System.out.println("""

                [A] Show all entries
                [E] Display only expenses (credit)
                [D] Display only payments (debit)
                [R] Custom reports...
                [H] Home"""
            );
            String input = scanner.nextLine();
            switch (input) {
                case "A", "a" -> getLedgerData("none");
                case "E", "e" -> getLedgerData("expenses");
                case "D", "d" -> getLedgerData("payments");
                case "R", "r" -> customReports(scanner);
                case "H", "h" -> isRunning = false;
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
        switch (filter) {
            case "none":
                try (FileReader reader = new FileReader("ledger/transactions.csv")) {
                    BufferedReader br = new BufferedReader(reader);
                    String input;
                    while ((input = br.readLine()) != null) {
                        ledgerData.add(input);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("File not found");
                }
                displayLedgerData(ledgerData);
                break;
            case "expenses":
                try (FileReader reader = new FileReader("ledger/transactions.csv")) {
                    BufferedReader br = new BufferedReader(reader);
                    String input;
                    while ((input = br.readLine()) != null) {
                        String[] categories = input.split("\\|");
                        if (categories[categories.length - 1].contains("-")) {
                            ledgerData.add(input);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException("File not found");
                }
                displayLedgerData(ledgerData);
                break;
            case "payments":
                try (FileReader reader = new FileReader("ledger/transactions.csv")) {
                    BufferedReader br = new BufferedReader(reader);
                    String input;
                    while ((input = br.readLine()) != null) {
                        String[] categories = input.split("\\|");
                        if (!categories[categories.length - 1].contains("-")) {
                            ledgerData.add(input);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException("File not found");
                }
                displayLedgerData(ledgerData);
                break;
        }
    }

    private static void displayLedgerData(List<String> ledgerData) {
        for (String transaction : ledgerData) {
            if (transaction != null) {
                System.out.println(transaction);
            }
        }
    }

    private static void customReports(Scanner scanner) {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("""
                
                Which custom report would you like to view?
                [1] Month to date
                [2] Previous month
                [3] Year to date
                [4] Previous year
                [5] Search by vendor
                [0] Previous screen
                """);
            int customReportChoice = scanner.nextInt();
            scanner.nextLine();

            LocalDate date = LocalDate.now();
            int month = date.getMonthValue();
            int year = date.getYear();
            List<String> ledger = getLedgerData();
            StringBuilder output = new StringBuilder();
            switch (customReportChoice) {
                case 1:
                    for (String transaction : ledger) {
                        LocalDate transactionDate = LocalDate.parse(transaction.split("\\|")[0]);
                        if (transactionDate.getMonthValue() == month && transactionDate.getYear() == year) {
                            output.append(transaction).append("\n");
                        }
                    }
                    break;
                case 2:
                    for (String transaction : ledger) {
                        LocalDate transactionDate = LocalDate.parse(transaction.split("\\|")[0]);
                        if (transactionDate.getMonthValue() == month-1 && transactionDate.getYear() == year) {
                            output.append(transaction).append("\n");
                        }
                    }
                    break;
                case 3:
                    for (String transaction : ledger) {
                        LocalDate transactionDate = LocalDate.parse(transaction.split("\\|")[0]);
                        if (transactionDate.getYear() == year) {
                            output.append(transaction).append("\n");
                        }
                    }
                    break;
                case 4:
                    for (String transaction : ledger) {
                        LocalDate transactionDate = LocalDate.parse(transaction.split("\\|")[0]);
                        if (transactionDate.getYear() == year - 1) {
                            output.append(transaction).append("\n");
                        }
                    }
                    break;
                case 5:
                    System.out.print("\nPlease enter the vendor name: ");
                    String vendorName = scanner.nextLine().toLowerCase();
                    for (String transaction : ledger) {
                        String vendor = transaction.split("\\|")[3].toLowerCase();
                        if (vendor.contains(vendorName)) {
                            output.append(transaction).append("\n");
                        }
                    }
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
                System.out.println(output);
            }
        }
    }


    public static void addExpense(String[] depositInfo) {
//        date | time | description | vendor | amount
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm:ss");
        LocalDateTime dt = LocalDateTime.now();
        String formattedDate = dt.format(formatter);
        String description = depositInfo[0];
        String vendor = depositInfo[1];
        double amount = Double.parseDouble(depositInfo[2]);
        String info = String.format("%s|%s|%s|-%.2f\n", formattedDate, description, vendor, amount);
        try {
            enterInfoIntoLedger(info);
            Thread.sleep(1000);
            System.out.print("\n...");
            Thread.sleep(1000);
            System.out.print("...");
            Thread.sleep(1000);
            System.out.print("...");
            Thread.sleep(1000);
            System.out.println("\n\nSuccessfully added expense!\n\n");
        } catch (Exception e) {
            throw new RuntimeException("Sorry there was an issue entering your expense" + e);
        }
    }


    public static void addPayment(String[] paymentInfo) {
//        date | time | description | vendor | amount
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm:ss");
        LocalDateTime dt = LocalDateTime.now();
        String formattedDate = dt.format(formatter);
        String description = paymentInfo[0];
        String vendor = paymentInfo[1];
        double amount = Double.parseDouble(paymentInfo[2]);
        String info = String.format("%s|%s|%s|%.2f\n", formattedDate, description, vendor, amount);
        try {
            enterInfoIntoLedger(info);
            Thread.sleep(1000);
            System.out.print("\n...");
            Thread.sleep(1000);
            System.out.print("...");
            Thread.sleep(1000);
            System.out.print("...");
            Thread.sleep(1000);
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
