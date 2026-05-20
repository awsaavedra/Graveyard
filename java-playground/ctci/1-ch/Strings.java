import java.util.HashMap;
import java.util.Map;

public class Strings{

  public static void main(String[] args){
    String word = "hello";

    // System.out.println(revStr(word));
    

    // System.out.println(joinWords(words));

    System.out.println(isPalindrome("hello","elhlo"));
  } 


  // Implement an algorithm to determine if a string has all unique
  // character. What if you cannot use additional DS?
  // TODO: code it up and check solutions
  // sol1: time - O(n), space - O(n)
  // sol2: time - O(n*log(n)), space - O(1)

  public static boolean  hasAllUnique(String str){
    
    // WITH data structure
    // hashmap and store all letter A-Z, a-z

    // loop through the string using charAt and increment each value in hashmap
    // if a Character's value is found to be > 1, return false

    // WITHOUT data structure

    // sort string, O(n*log(n))

    // loop through string keeping track of a previous character and check it 
    // against the current character, if they are equal return false

    return true;
  }


  // reverse a string

  public static String revStr(String str){
    
    if( str.length() == 1) return str;
    else{
      StringBuffer revStr = new StringBuffer();
      for( int i = str.length() - 1; i >= 0; i--)  revStr.append(str.charAt(i));
      
      return revStr.toString();
    }

  }
  // Concating a string '+':  O(x(n^2))
  // StringBuilder concat: O(1), simply creating a resizable array of all the string
  // only copying them over when necessary
  // TODO: Did Java 11 take care of this in terms of time complexity?
  public static String joinWords(String[] words){
    StringBuilder sentence = new StringBuilder();
    for(String w: words){
      sentence.append(w);
    }

    return sentence.toString();
  }


  // 1.4, Given a string, write a function to check if it is a permutation
  // input: Tact Coa
  // O: True

  public static boolean isPalindrome( String s1, String s2){
    if( s1.length() != s2.length()) 
      return false;
    
    Map<Character, Integer> hm = new HashMap<>();
    Map<Character, Integer> hm2 = new HashMap<>();
    for(Character c: s1.toCharArray()){
      hm.put(c, hm.get(c) + 1);
    }
    for(Character c: s2.toCharArray()){
      hm2.put(c, hm2.get(c) + 1);
    }
    
    if(hm.size() != hm2.size()) return false; 
    for( Character c: hm.keySet()){
      if( hm.get(c) != hm2.get(c)) return false;
    }

    return true;
  }

  
}
