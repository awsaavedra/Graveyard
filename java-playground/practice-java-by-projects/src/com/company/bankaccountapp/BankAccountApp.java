package com.company.bankaccountapp;

import java.util.LinkedList;
import java.util.List;

public class BankAccountApp {

    String name;
    String ssn;
    double balance;
    double rate;
    String accountNumber;

    public static void main(String[] args) {

        List<Account> accounts = new LinkedList<Account>();

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
            String name = accountHolder[0];
            String sSN = accountHolder[1];
            String accountType = accountHolder[2];
            double initDeposit = Double.parseDouble(accountHolder[3]);


            if( accountType.equals("Savings")){
                accounts.add(new Savings(name, sSN, initDeposit));
            }else if( accountType.equals("Checking")){
                accounts.add(new Checking(name, sSN, initDeposit));
            }else{
                System.out.println("ERROR READING ACCOUNT TYPE");
            }
        }

        for( Account account: accounts){
            System.out.println("\n*********");
            account.showInfo();
        }
    }
}
