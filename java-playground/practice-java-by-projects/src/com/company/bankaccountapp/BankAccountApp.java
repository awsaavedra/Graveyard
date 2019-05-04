package com.company.bankaccountapp;

import java.util.List;

public class BankAccountApp {

    String name;
    String ssn;
    double balance;
    double rate;
    String accountNumber;

    public static void main(String[] args) {

//
//        Checking checkingAccount1 = new Checking("Tom Wilson", "12345676", 1500);
//        Savings savingsAccount1 = new Savings("Rich Lowe" , "12345676", 2500);
//
//        savingsAccount1.compound();
//
//        checkingAccount1.showInfo();
//        System.out.println("***********");
//        savingsAccount1.showInfo();

        // Read a CSV File then create new accounts based on that data

        String file = "/Users/Alexander/workspace/java-playground/practice-java-by-projects/src/com/company/bankaccountapp/NewBankAccounts.csv";
        List<String[]> newAccountHolders = utilities.CSV.read(file);
        for(String[] accountHolder: newAccountHolders) {
            System.out.println("NEW ACCOUNT");
            System.out.println(accountHolder[0]);
            System.out.println(accountHolder[1]);
            System.out.println(accountHolder[2]);
            System.out.println(accountHolder[3]);

        }

    }

}
