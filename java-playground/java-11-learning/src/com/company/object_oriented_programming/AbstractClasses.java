package com.company.object_oriented_programming;

/*
   For more information on abstract classes: https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html

   Key questions for abstract classes and interfaces:

    - How abstract vs concrete should they be?
    - When should you use one over the other?

   Note: best practices 1 file per class, but for illustrative purposes I kept them in the same place
   to make it easier to look at reference classes and examples
 */

public class AbstractClasses {
    public static void main(String[] args) {
        GiantLizard liz = new GiantLizard("Godzilla");
        liz.breathe();
        liz.eat();
        System.out.println();
        Griffin grif = new Griffin("Griffin");
        grif.breathe();
        grif.eat();
        grif.fly();
    }
}


abstract class Monster{
    private String name;

    public Monster(String name) {
        this.name = name;
    }

    public abstract void eat();
    public abstract void breathe();

    public String getName() {
        return name;
    }
}

class GiantLizard extends Monster{
    public GiantLizard(String name) {
        super(name);
    }

    @Override
    public void eat() {
        System.out.println("Nom nom!");
    }

    @Override
    public void breathe() {
        System.out.println("*breathing*");
    }

    @Override
    public String getName() {
        return "Giant lizard";
    }
}

class Griffin extends Monster implements CanFly{
    public Griffin(String name) {
        super(name);
    }

    @Override
    public void eat() {
        System.out.println("snacking noises");
    }

    @Override
    public void breathe() {
        System.out.println("Load mouth breathing");
    }

    @Override
    public String getName() {
        return "Griffin";
    }

    @Override
    public void fly() {
        System.out.println("Flying " + this.getName());
    }
}

class Dragon extends Monster implements CanFly{
    public Dragon(String name) {
        super(name);
    }

    @Override
    public void eat() {

    }

    @Override
    public void breathe() {

    }

    @Override
    public String getName() {
        return "Dragon";
    }

    @Override
    public void fly() {
        System.out.println("Flying " + this.getName());
    }
}

interface CanFly{
    void fly();
}

