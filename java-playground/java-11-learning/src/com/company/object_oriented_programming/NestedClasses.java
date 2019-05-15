package com.company.object_oriented_programming;

/*
    For more in depth coverage see: https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html

    4 Types of nested classes:

        - Static classes
        - Inner classes
             - Local class (inside a scope block)
             - Anonymous class

    Key questions:
        - When should I use an inner classes with respect to OOD and OOP principles?
        - What problem are they solving?

    Note: best practices 1 file per class, but for illustrative purposes I kept them in the same place
    to make it easier to look at reference classes and examples
 */

import java.util.ArrayList;
import java.util.Scanner;

public class NestedClasses {

    private static Scanner sc = new Scanner(System.in);
    private static Button btnPrint = new Button("Print");

    public static void main(String[] args) {

        class ClickListener implements Button.OnClickListener{
            public ClickListener(){
                System.out.println("I've been attached");
            }

            @Override
            public void onClick(String title){
                System.out.println(title + " was clicked");
            }
        }
//        btnPrint.setOnClickListener(new ClickListener());
        btnPrint.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(String title) {
                System.out.println(title + " was clicked");
            }
        });
        listen();
    }

    private static void listen(){
        boolean quit = false;
        while(!quit){
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 0:
                    quit = true;
                    break;
                case 1:
                    btnPrint.onClick();
            }
        }
    }
}

class Gearbox{
    private ArrayList<Gear> gears;
    private int maxGears;
    private int currGear = 0;
    private boolean clutchIsIn;

    public Gearbox(int maxGears) {
        this.maxGears = maxGears;
        this.gears = new ArrayList<>();
        Gear neutral = new Gear(0,0.0);
        this.gears.add(neutral);

        for(int k = 0; k < maxGears; k++){
            addGear(k, k * 5.3);
        }
    }

    public void operateClutch(boolean in){
        this.clutchIsIn = in;
    }

    public void addGear(int number, double ratio){
        if((number > 0)  && (number <= maxGears)) this.gears.add(new Gear(number, ratio));
    }

    public void changeGear( int newGear){
        if( newGear >= 0 && newGear < this.gears.size() && this.clutchIsIn) {

            this.currGear = newGear;
            System.out.println("Gear " + newGear + " selected.");
        }else{
            System.out.println("Gears are grinding!");
            this.currGear = 0;
        }
    }

    public double wheelSpeed(int revs){
        if(clutchIsIn){
            System.out.println("Screech!");
            return 0.0;
        }
        return revs * gears.get(currGear).getRatio();
    }

    public class Gear{
        private int number;
        private double ratio;

        public Gear(int number, double ratio) {
            this.number = number;
            this.ratio = ratio;
        }

        public double driveSpeed(int revs){
            return revs * (this.ratio);
        }

        public int getNumber() {
            return number;
        }

        public double getRatio() {
            return ratio;
        }
    }
}

class Button {
    private String title;
    private OnClickListener onClickLister;

    public Button(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickLister = onClickListener;
    }

    public void onClick() {
        this.onClickLister.onClick(this.title);
    }

    public interface OnClickListener {
        public void onClick(String title);
    }

}
