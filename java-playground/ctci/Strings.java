public class Strings{

  public static void main(String[] args){
    String word = "hello";

    System.out.println(revStr(word));

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
}
