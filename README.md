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

### Home Screen 

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/ab9eac89-93e7-4b95-a37d-2ff929e87c50)

<details> 
  
  **<summary> Adding transactions </summary>**

### Adding Expenses

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/ff8cc083-8a21-4218-876c-804933f0cd8c)

### Adding Payments

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/21cf6fb3-1817-42a6-808b-0187be7d600e)

</details>

<details>

 **<summary> Accessing the ledger </summary>**
  
### Main Screen

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/b4c0f24a-a792-40af-983d-4c0f3cca475e)

### Showing All Entries

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/0ad402c6-fb2a-4637-82df-cf960cd802e0)

### Displaying Only Expenses

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/826a16e4-7617-4c03-bb72-133f49fff611)

### Displaying Only Payments

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/e4ff2eaf-f6f0-4de9-9da1-8c6a07b43ed2)

### Custom Report Screen

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/567e6f3e-e9cf-43e1-a45b-cefed24decb1)

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

</details>

<details>

  **<summary> Error Handling </summary>**

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/9a89b107-a713-45a2-90cc-e4e67e453e7b)

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/d3d68b08-2d42-444c-ac72-0d14df8c7210)

![image](https://github.com/sekwanaa/Accounting-Ledger-Application/assets/112197395/a270d3fa-d281-404c-9963-61228579a885)

  
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
