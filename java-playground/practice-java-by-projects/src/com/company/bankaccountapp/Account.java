package com.company.bankaccountapp;

public abstract class Account implements BaseRate {

    String name;
    String sSN;
    double balance;

    static int index = 10000;
    String accountNumber;
    double rate;

    public Account(String name, String sSN, double initDeposit){
        this.name = name;
        this.sSN = sSN;
        this.balance = initDeposit;
        System.out.println("NAME: " + name + " SSN: " + sSN + " Balance: $" + balance);

        // Set account number
        index++;
        this.accountNumber = setAccountNumber();
        System.out.println("ACCOUNT NUMBER: " + this.accountNumber);
    }

    // Set account number
    private String setAccountNumber(){
        String lastTwoOfSSN = sSN.substring(sSN.length() - 2, sSN.length());
        int uniqueID = index;
        int randomNumber = (int)(Math.random() * Math.pow(10, 3));
        return lastTwoOfSSN + uniqueID + randomNumber;
    }

    // List common methods
    public void showInfo(){
        System.out.println(
                "NAME: " + name +
                "\nACCOUNT NUMBER: " + accountNumber +
                "\nBALANCE: " + balance
        );
    }
}
