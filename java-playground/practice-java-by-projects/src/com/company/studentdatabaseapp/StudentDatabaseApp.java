package com.company.studentdatabaseapp;

/*

    Ask the user how many new students will be added to the database

    the user should be prompted to enter the name and year for each student
    the student should have a 5-digit unique ID< with the first number being their grade level
    A student can enroll in the following courses:
        History 101
        Math 101
        English 101
        Chemistry 101
        Comp Sci 101
    Each course costs 600 to enroll
    The student should be able to view their balance and pay the tuition
    To see the status of the student, we should see their name, ID, courses enrolled, and balance
 */

public class StudentDatabaseApp {

    public static void main(String[] args) {

        Student stu1 = new Student();
        stu1.enroll();
        stu1.payTuition();
        // Ask how many new users we want to add

        // Create n number of new students


    }


}
