import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/*
// 4088060863
// 6782009000
// 4089102210

// The user checks to see whether the prefix is in the phone directory
// 408  --> True
// 40892 --> False
// 4 --> True 

Method 
1) Set<String> (Use the contains method on String to check if the prefix is present or not) 0(n)

2) Trie (Time complexity O(k)) 

*/
class TrieNode{
    public Character value;
    public Map<Character,TrieNode> next = new HashMap<>();
    public boolean end = false;
    
    public TrieNode(Character value){
        this.value = value;
    }
}

public class Trie {
    
    public TrieNode root = new TrieNode('\u0000');
    
    //Insert single number if not present 
    public void insert(String phoneNumber){
        TrieNode current = this.root;
        char[] chars = phoneNumber.toCharArray();
        char number;
        
        for(int i = 0; i < chars.length; i++){
            number = chars[i];
            
            //Insert into trie if not present already
            // hm.containsKey(key)
            if(!current.next.containsKey(number)){
                current.next.put(number, new TrieNode(number));
            }
            current = current.next.get(number);  // (key, node) -> (number, Trienode)
        }
    }
    
    //Check if user input(prefix or number) is in the list 
    public boolean isPrefix(String prefix){
        TrieNode current = this.root;
        char number;
        
        for(int i = 0; i < prefix.length(); i++){
            number = prefix.charAt(i);
            //Check whether number in in Trie Node 
            // hm.containsKey(key)
            if(!current.next.containsKey(number)){
                return false;
            }
            current = current.next.get(number);  // (key, node) -> (number, Trienode)
        }
        return true;
    }
    
    
    // 408  --> True
    // 40892 --> False
    // 4 --> True 
    public static void main(String args[] ) throws Exception {
        System.out.println("Hello World");
        
        ArrayList<String> numbers = new ArrayList();
        numbers.add("4088060863");
        numbers.add("4089102210");
        numbers.add("6782009000");
        
        Trie trie = new Trie();

        for(int i = 0; i < numbers.size(); i++){
            trie.insert(numbers.get(i));
        }
        
        System.out.println(trie.isPrefix("408"));
        System.out.println(trie.isPrefix("40892"));
        System.out.println(trie.isPrefix("4"));
    }
}
