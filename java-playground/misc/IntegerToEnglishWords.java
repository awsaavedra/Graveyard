// package whatever; // don't place package name!
/*
273. Integer to English Words
Share
Convert a non-negative integer to its english words representation. 
Given input is guaranteed to be less than 231 - 1.

Example 1:

Input: 123
Output: "One Hundred Twenty Three"
Example 2:

Input: 12345
Output: "Twelve Thousand Three Hundred Forty Five"
Example 3:

Input: 1234567
Output: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"

Example 4:
Input: 1234567891
Output: "One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven 
Thousand Eight Hundred Ninety One"

HashMap<Integer, String>
          0      "Zero"
          1      "One"
          2      "Two"
          3      "Three"
          4      "Four"
          5      "Five"
          6      "Six"
          7      "Seven"
          8      "Eight"
          9      "Nine"
          10     "Ten"
          11     "Eleven"
          12     "Twelve"
          13     "Thirteen"
          14
          15
          16
          17
          18
          19
          20
          30
          40
          50
          60
          70
          80
          90
          100   Hundred
          1000  Thousand
          1,000,000 Million
          1,000,000,000 Billion


          4 IIII   VI

          7>=number of digits >=4  thousand
          7-10 million
          11-13 billion

          n = 123
          123%10 => 3
          result = result + map.get(1) Three Two One
          n = n -3  => 120
          120 %100 =>20  Twenty Three
          n= n-20 100
          100%1000   =>100   One Hundred Twenty Three 

12345
      12345 % 1000 => 345
         345 % 100   
           3     "hundered"    345 % 100
          Three  "hundered"       45
                                  45/10       45%10
                            four     4            5
                                              five  
        12345/1000 -> 12
           12%1000 != 0 
              12   "Thoursand"  + previousWords
            Twelve        

n%10=5
Five

n=n-5= 12340 %100 =>40 Forty Five
n= n-40 = 12300 %1000=>300  Three Hundred Forty Five
n= n-300 =12000 %10000=> 2000 Two Thousand Three Hundred Forty Five



*/


import java.io.*;

class MyCode {
 static String[] withInTwenty = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", 
 "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen",
 "Nineteen"};
 static String[] ten = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty",
 "Ninety"};
 static String[] thousand ={"", "Thousand", "Million", "Billion"};
 
	public static void main (String[] args) {
		System.out.println("Hello Java");
    int num = 1234567891;
    int i = 0;
    String previousWord = "";
    while(num > 0){
        if(num % 1000 != 0){
            previousWord =   breaking(num%1000)  + thousand[i] + " " + previousWord;
        num = num / 1000;
        System.out.println("Number is "+ num);
        i++;
        }
    }
    System.out.println(previousWord);
	}

  public static String breaking(int num){
    if(num == 0){
        return "";
    }else if(num < 20) {
      return withInTwenty[num] + " ";
    }else if (num < 100){
        return ten[num/10] + " " + breaking(num%10); 
    }
    else
      return withInTwenty[num/100] +" "+ "Hundred" + " " + breaking(num%100); 
  }
}

