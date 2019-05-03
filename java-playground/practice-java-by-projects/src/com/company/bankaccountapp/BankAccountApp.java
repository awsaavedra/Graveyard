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
        // Read a CSV File then create new accounts based on that data

    }

}
