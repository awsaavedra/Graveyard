package com.company.bankaccountapp;

public class Checking extends Account {

    // List properties specific to a checking account
    int debitCardNumber;
    int debitCardPIN;

    // Constructor to initialize checking account properties
    public Checking(String name, String sSN, double initDeposit) {
        super(name, sSN, initDeposit);
        accountNumber = "2" + accountNumber;
        setDebitCard();
    }

    @Override
    public void setRate(){ rate = getBaseRate() * 0.15; }

    // List any methods specific to the checking account
    private void setDebitCard(){
        debitCardNumber = (int)(Math.random() * Math.pow(10, 12));
        debitCardPIN = (int)(Math.random() * Math.pow(10, 4));
    }

    // List any methods specific to savings account
    @Override
    public void showInfo(){
        super.showInfo();
        System.out.println("Your Checking Account Features" +
                "\n Debit Card Number: " + debitCardNumber+
                "\n Debit Card PIN" + debitCardPIN
        );
        System.out.println("ACCOUNT TYPE: Checking");
    }
}
