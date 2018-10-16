package com.company;

public class Person {
    public void method1(){
        System.out.println("Person 1 ");
    }

    public void method2(){
        System.out.println("Person 2 ");
    }
}

public class Student extends Person{
    public void method1(){
        System.out.println("Student 1 ");
        super.method1();
        method2();
    }

    public void method2() {
        System.out.println("Student 2 ");
    }
}
public class Undergrad extends Student{
    public void method2(){
        System.out.println("Undergrad 2 ");
    }
}
