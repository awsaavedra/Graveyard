public class Banjo{
  public static String areYouPlayingBanjo(String name){
    name.toLowerCase();
    String playsBanjo = "does not play banjo";
    
    if( name.length() > 0 && name.charAt(0) == 'r' ){
      playsBanjo = "plays banjo";
    }
    return name + playsBanjo;
  }
}
