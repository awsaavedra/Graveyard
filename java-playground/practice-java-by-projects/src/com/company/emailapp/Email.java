package com.company.emailapp;

/*
    Scenario: You are an IT Support Admin Specialist and are charged
    with the task of creating email accounts for new hires.

    Your application should od the following:

        - Generate an email with the following syntax: firstname.lastname@department.company.com
        - Determine the department (sales, dev, accounting), if none leave blank
        - Generate a random String for a pw
        - Have set methods to change the pw, set the mailbox capacity, and define an alternate email
            address
        - Have get methods to display the name, email, and mailbox capacity
 */


import java.util.Scanner;

public class Email {

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String department;
    private int mailboxCapacity = 500;
    private int defaultPasswordLength = 10;
    private String alternateEmail;
    private String companySuffix = "abc123.company.com";

    public Email(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

        //Call a method asking for the department - return the department
        this.department = setDepartment();
        System.out.println("Department: " + this.department);

        // Call method that returns a random password
        this.password = randomPassword(defaultPasswordLength);
        System.out.println("Your password is: " + this.password);

        // Combine elements to generate email
        email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + department + companySuffix;

    }

    // Ask for the department
    private String setDepartment(){

        System.out.println("Enter the department:\n1 for Sales\n2 for Development\n3 for Accounting\n0 for none");

        Scanner in = new Scanner(System.in);
        int departmentChoice = in.nextInt();
        if (departmentChoice == 1) return "sales";
        else if (departmentChoice == 2) return "dev";
        else if (departmentChoice == 3) return "acct";
        else return "";

    }

    // Generate a random password
    private String randomPassword(int length){

        String passwordSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890!@#$%^&*()";
        char[] password = new char[length];

        for( int i = 0; i < length; i++){
            int random = (int) (Math.random() * passwordSet.length());
            password[i] = passwordSet.charAt(random);
        }
        return new String(password);
    }

    // Set the mailbox capacity
    public void setMailboxCapacity(int capacity){

        this.mailboxCapacity = capacity;

    }

    // Set the alternate email
    public void setAlternateEmail(String alternateEmail){
        this.alternateEmail = alternateEmail;
    }

    // Change the password
    public void changePassword(String password){
        this.password = password;
    }

    public int getMailboxCapacity(){ return mailboxCapacity; }
    public String getAlternateEmail(){ return alternateEmail; }
    public String getPassword(){ return password; }

    public String showInfo(){
        return "DISPLAY NAME: " + firstName + " " + lastName +
                "\nCOMPANY EMAIL: " + email +
                "\nMAILBOX CAPACITY: " + mailboxCapacity + " mb";
    }
}
