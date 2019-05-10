package com.company;

public class ControlFlow {

    public static void main(String[] args) {


        for( int i = 2; i <= 8; i++){
            System.out.println("With an interest rate of: " + i + " The total interest is: "
                    + String.format("%.2f", calcInterest(10000.0, i)));
        }

        System.out.println();

        for( int i = 8; i >= 2; i--){
            System.out.println("With an interest rate of: " + i + " The total interest is: "
                    + String.format("%.2f", calcInterest(10000.0, i)));
        }

        // For each is another loop, but we will discuss it once we cover arrays and complex data types
        
//        int switchVal = 1;
//
//        switch (switchVal){
//            case 1:
//                System.out.println("Value was 1");
//                break;
//
//            case 2:
//                System.out.println("Value was 2");
//                break;
//
//            case 3:
//                System.out.println("Value was 3");
//                break;
//
//            case 4: case 5:
//                System.out.println("was a 4 or a 5");
//                break;
//
//            default:
//                System.out.println("Was not 1, 2, 3, 4,or 5");
//                break;
//        }
//
//        /*
//        Challenge:
//            create a new switch statement using char instead of int
//            create a new char variable
//            create a switch statement testing for
//            A, B, C, D, or E
//            display a message if any of these are found and then break
//            Add a default which displays a msg saying not found
//         */
//
//        char charSwitch = 'B';
//
//        switch (charSwitch){
//            case 'A':
//                System.out.println("Char was A");
//                break;
//
//            case 'B':
//                System.out.println("Char was B");
//                break;
//
//            case 'C':
//                System.out.println("Char was C");
//                break;
//
//            case 'D':
//                System.out.println("Char was D");
//                break;
//
//            default:
//                System.out.println("Char was not A, B, C, D");
//                break;
//
//        }
//
//        String month = "january";
//
//        switch(month.toLowerCase()){
//            case "january":
//                System.out.println("Jan");
//                break;
//
//            case "June":
//                System.out.println("Jun");
//                break;
//
//            default:
//                System.out.println("Not sure");
//        }
//        System.out.println("10,000 at 2% interest = " + calcInterest(10000.0, 2.0));


    }

    public static double calcInterest(double amount, double interestRate){
        return( amount * (interestRate / 100));
    }

    public static boolean isPrime(int n){
        if( n == 1) return false;

        for (int i = 2; i <= (long) Math.sqrt(n); i++){
            if( n % i == 0) return false;
        }
        return true;
    }
}
