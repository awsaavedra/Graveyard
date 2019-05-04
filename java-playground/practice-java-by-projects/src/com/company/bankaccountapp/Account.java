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
        setRate();
    }

    public abstract void setRate();

    // Set account number
    private String setAccountNumber(){
        String lastTwoOfSSN = sSN.substring(sSN.length() - 2, sSN.length());
        int uniqueID = index;
        int randomNumber = (int)(Math.random() * Math.pow(10, 3));
        return lastTwoOfSSN + uniqueID + randomNumber;
    }

    // List common methods - transactions
    public void deposit( double amount){

        balance = balance + amount;
        System.out.println("Depositing $" + amount );
        printBalance();
    }

    public void withdraw(double amount){
        balance = balance - amount;
        System.out.println("Withdrawing $" + amount);
    }

    public void transfer(String toWhere, double amount) {
        balance = balance - amount;
        System.out.println("Transfering $" + amount + " to " + toWhere);
    }

    public void printBalance() {
        System.out.println("Your balance is now : " + balance);
    }

    public void showInfo(){
        System.out.println(
                "NAME: " + name +
                "\nACCOUNT NUMBER: " + accountNumber +
                "\nBALANCE: " + balance +
                "\nRATE: " + rate + "%"
        );
    }


}
