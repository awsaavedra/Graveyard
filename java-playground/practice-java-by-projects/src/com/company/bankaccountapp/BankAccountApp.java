package com.company.bankaccountapp;

public class BankAccountApp {

    String name;
    String ssn;
    double balance;
    double rate;
    String accountNumber;

    public static void main(String[] args) {

        Checking checkingAccount1 = new Checking("Tom Wilson", "12345676", 1500);
        Savings savingsAccount1 = new Savings("Rich Lowe" , "12345676", 2500);

        checkingAccount1.showInfo();
        System.out.println("***********");
        savingsAccount1.showInfo();

        savingsAccount1.deposit(5000);
        savingsAccount1.deposit(200);
        savingsAccount1.transfer("Brokerage", 3000);

        // Read a CSV File then create new accounts based on that data

    }

}
