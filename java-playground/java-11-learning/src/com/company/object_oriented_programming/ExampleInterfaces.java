package com.company.object_oriented_programming;

/*
    Read more here: https://docs.oracle.com/javase/tutorial/java/concepts/interface.html

    Note: best practices 1 file per class, but for illustrative purposes I kept them in the same place
    to make it easier to look at reference classes and examples
 */

public class ExampleInterfaces {

    public static void main(String[] args) {
        DeskPhone deskPhone = new DeskPhone(); // can instantiate desk phone

        MobilePhone mp = new MobilePhone(1234);
        mp.turnOn();
        mp.call(2345);
        mp.pickUp();
    }
}

interface Telephone{
    void turnOn();
    void dial(int phoneNumber);
    void pickUp();
    boolean call(int number);
    boolean Ringing();
}

class MobilePhone implements Telephone{

    public int number;
    private boolean ringing;
    private boolean isOn = false;

    public MobilePhone(int number) {
        this.number = number;
    }

    @Override
    public void turnOn() {
        isOn = true;
        System.out.println("Mobile phone powered up");
    }

    @Override
    public void dial(int phoneNumber) {
        if( isOn){
            System.out.println("Now ringing " + phoneNumber + " on mobile phone." );
        }else{
            System.out.println("Phone is swithced off");
        }
    }

    @Override
    public void pickUp() {
        if( Ringing()){
            System.out.println("Answer the desk phone");
            ringing = false;
        }
    }

    @Override
    public boolean call(int number) {
        if( this.number == number && this.isOn){
            ringing = true;
            System.out.println("*Melody ring!*");
        }else{
            ringing = false;
            System.out.println("Mobile phone is not turned on");
        }
        return ringing;
    }

    @Override
    public boolean Ringing() {
        return ringing;
    }
}

class DeskPhone implements Telephone{

    public int number;
    private boolean ringing;

    @Override
    public void turnOn() {
        System.out.println("Nothing happened, desk phone doesn't have this feature");
    }

    @Override
    public void dial(int phoneNumber) {
        System.out.println("*Ring ring!*");
    }

    @Override
    public void pickUp() {
        if( Ringing()){
            System.out.println("Answer the mobile phone");
            ringing = false;
        }
    }

    @Override
    public boolean call(int number) {
        if( this.number == number){
            ringing = true;
            System.out.println("*Ring ring!*");
        }else{
            ringing = false;
        }
        return ringing;
    }

    @Override
    public boolean Ringing() {
        return ringing;
    }
}