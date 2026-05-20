package com.company;

public class Student extends Person
{
    private int id;

    public Student(String n, int id)
    {
        super(n);
        this.id = id;
    }

    public String toString()
    {
        return super.toString()+", "+id;
    }
}