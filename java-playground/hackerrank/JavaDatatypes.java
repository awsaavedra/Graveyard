import java.util.*;
import java.io.*;



class JavaDatatypes{
    public static void main(String []argh)
    {



        Scanner sc = new Scanner(System.in);
        int t=sc.nextInt();

        for(int i=0;i<t;i++)
        {

    /*
        A byte is an 8-bit signed integer.
        A short is a 16-bit signed integer.
        An int is a 32-bit signed integer.
        A long is a 64-bit signed integer.  
    */
            try
            {
                long x=sc.nextLong();
                System.out.println(x+" can be fitted in:");
                if(x <= 127 && x >= -128){
                    System.out.println("* byte \n* short \n* int \n* long");
                }else if( x <= 32767 && x >= -32768){
                    System.out.println("* short \n* int \n* long");
                }else if ( x <= 2147483647 && x >= -2147483648){
                    System.out.println("* int \n* long");
                }else if(x <= 9223372036854775807l && x >= -9223372036854775808l){
                    System.out.println("* long");
                }
            }
            catch(Exception e)
            {
                System.out.println(sc.next()+" can't be fitted anywhere.");
            }

        }
    }
}

