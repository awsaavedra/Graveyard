public class MakeSentence{
  
  public static void main( String[] args){
    String[] str = {"hello " , "world"};
    System.out.println(makeSentence(str));
    System.out.println(makeSentence2(str));
  }  

  // What is the running time of this code (assume < java 6)?
  // think carefully about this and why are StringBuffer/Builders used sometimes?
  // TODO: is this question relevant for java 8+? Probably not.
  public static String makeSentence( String[] words){
    String sentence = "";
    for( String w: words) sentence = sentence + w;
    return sentence;
  }

  public static String makeSentence2(String[] words) {
  
    StringBuffer sentence = new StringBuffer();
    for( String w : words ) sentence.append(w);

    return sentence.toString();
  }
}
