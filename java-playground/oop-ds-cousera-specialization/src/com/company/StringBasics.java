package com.company;

public class StringBasics {
    public static void main(String[] args) {
//        String text = new String("Hello World!");
//        String text2 ="Hello World!";
//
//        //What is an interned string?
//        String text3 = "Hello World!"; //dis be it... both text2, text3 refer to the same object
//
//        String hello_world = new String("Hello World!");
//        String hello_world2 = new String("Hello World!");
//        System.out.println(hello_world.equals(hello_world2)); // .equals is a content matcher
//        System.out.println(hello_world == hello_world2); // == is a reference matcher, in this case
//
//
//        String s = "My oh my!";
//        System.out.println(s.indexOf("my")); //What index does it output?

        String txt = "Can you hear me?  Hello, hello?";
        String[] words = txt.split(" +");
    }

    public static boolean hasLetter(String word, char letter){
        for (char currentCharacter : word.toCharArray()){
            if(currentCharacter == letter){
                return true;
            }
        }
        return false;
    }
}
