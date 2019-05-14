package com.company.object_oriented_programming;

public class Main {

    public static void main(String[] args) {

        Animal animal = new Animal("Animal", 1, 1, 5, 5);

        Dog dog = new Dog( "lab", 1, 50, 2, 20, 1, "black");
        dog.eat();
        dog.walk();
        dog.run();
    }
}
