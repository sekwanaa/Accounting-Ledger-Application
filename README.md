# Accounting Ledger Application

The goal of this application is to allow the user to add and view accounting transactions.\
It allows the user to add expenses and payments.\
Additionally, the user is able to view their transactions. If they would like, they can view all transactions, or filter them by payments or expenses.\
The user also has the ability to create reports such as:
   *  Month to date
   *  Previous month
   *  Year to date
   *  Previous year
   *  Search by vendor
   *  Custom search...
       * Custom search allows for users to input optional search fields such as:
         * start date
         * end date
         * transaction description
         * transaction vendor
         * transaction amount

### Home Screen 

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/11df1bf6-0820-41d7-9772-8ae76f1ad1a8)

<details> 
  
  **<summary> Adding transactions </summary>**

### Adding Expenses

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/f1e935f5-e85a-4f7f-b174-46f141375c33)

### Adding Payments

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/6772f76a-89c4-4955-a5a4-da1b53eb9915)

</details>

<details>

 **<summary> Accessing the ledger </summary>**
  
### Main Screen

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/b4c0f24a-a792-40af-983d-4c0f3cca475e)


<details>

  **<summary>Showing Entries</summary>**

### All Entries

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/7dc15397-9eb9-453a-ad3b-2ba9fac5777d)

### Displaying Only Expenses

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/bcdf4ca0-dc7a-4071-9ca1-fcc19529d109)

### Displaying Only Payments

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/8f50fc1d-08cb-4f49-bee8-325cb5b31d4d)
  
</details>

<details>

  **<summary>Custom Reports</summary>**

### Custom Report Screen

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/338fe662-e99e-47c9-8af9-0cf319d90afe)

### Month to Date

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/cd1d34f1-d5cd-4a8f-bf57-be2cf0b70a2d)

### Previous Month

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/55110012-3f93-4bf6-8c9b-29ec82d34f40)

### Year to Date

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/e88a5769-e6d8-4397-9e9c-84360b0ebf66)

### Previous Year

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/e73aa50c-4a60-41e0-a864-93c205010aea)

### Search by Vendor

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/cd83343e-f84d-4e3f-af8c-b6893dfa77f0)

### Custom Search

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/91830bf4-cc79-42c1-8f7b-68bade55e3c1)
  
</details>

</details>

### Interesting Piece of Code

> I chose this piece of code because it extracted a method that would be repeated many times and created an arrayList to be updated.

``` java
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
```

<details>

  **<summary> Error Handling </summary>**

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/9a89b107-a713-45a2-90cc-e4e67e453e7b)

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/d3d68b08-2d42-444c-ac72-0d14df8c7210)

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/a270d3fa-d281-404c-9963-61228579a885)

  
</details>

