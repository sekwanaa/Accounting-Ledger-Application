# Capstone 1 - Accounting Ledger Application

The goal of this application is to allow the user to add and view accounting transactions.\
It allows the user to add expenses, payments, and to view their current balance.\
Additionally, the user is able to view their transactions. If they would like, they can view all transactions, or filter them by payments or expenses.\
For now, instead of using a database, each transaction is stores in a file called transactions.csv.

## Home Screen 
This is the main screen that users will encounter when working with the application.\
Users are able to:
 - Add an expense
 - Add income
 - Access the ledger screen
 - View their current balance

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/f03c4356-9511-435f-a8bb-7b131d6b0562)

<details> 
  
  **<summary> Adding transactions </summary>**

### Adding Expenses

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/f1e935f5-e85a-4f7f-b174-46f141375c33)

### Adding Payments

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/6772f76a-89c4-4955-a5a4-da1b53eb9915)

</details>

## Accessing the Ledger
The ledger is what stores user transactions, whether it be an expense or income. The user is able to view all transactions, only expenses, or only income. Users are able to also view custom pre-defined reports to further fine tune their ability to view transactions.\
Custom reports include the ability to search:

Month to Date &nbsp;&nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;&nbsp; Year to Date  &nbsp;&nbsp;&nbsp;&nbsp; |  &nbsp;&nbsp;&nbsp;&nbsp; Search by Vendor\
Previous Month &nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;&nbsp; Previous Year &nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;&nbsp; Custom Search...

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/1d73fe29-01db-4f9e-b8a6-9d3d095ace6a)

<details>

  **<summary>Showing Entries</summary>**

### All Entries

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/f78c3734-1a4c-4423-bb23-974d1650214e)

### Displaying Only Expenses

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/86700f15-fb33-4151-8c0e-cfde691f95e0)

### Displaying Only Income

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/ee5ddca5-f2ff-4733-bcef-4976a77f0db7)

</details>

<details>

  **<summary>Custom Reports</summary>**

### Custom Report Screen

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/338fe662-e99e-47c9-8af9-0cf319d90afe)

### Month to Date

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/ac99ecdb-e8f0-428f-93f1-ed8d6d522ecf)

### Previous Year

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/94c35aea-efa9-48ea-be5b-ffef466679a3)

### Search by Vendor

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/9b8b67a6-1a8d-480c-bdd4-1158c13f7140)

### Custom Search

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/b135912b-5bab-44de-952f-36a624511e22)

</details>

## Viewing Balance
Users are able to view the current balance of their ledger

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/ad0ce8a6-52c7-4fec-bfc2-74b5ab9395c7)

## Interesting Piece of Code

> I chose this piece of code because it uses a simple ternary operator, and also easily checks each transaction for whether it satisfies all the filters. As soon as one does not satisfy it, it moves on. Only when it satisfies all the search criteria will it add it to the output.

``` java
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

  List<String> filteredLedger = filterSearch(filters);
  output.addAll(filteredLedger);
  break;
```

## Error Handling

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/9a89b107-a713-45a2-90cc-e4e67e453e7b)

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/d3d68b08-2d42-444c-ac72-0d14df8c7210)

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/a270d3fa-d281-404c-9963-61228579a885)
