public class RemoveExclamationMarks{
  public static String remove(String s, int n){
    String sNew = s;  
    for(int i = 0; i < s.length(); i++){
        if( s.charAt(i) == '!'){
          sNew = s.subString(0, i - 1) + s.subString( i + 1, s.length()); 
        }
        if( n == 0){
          return sNew;
        }
      }
  }
}
