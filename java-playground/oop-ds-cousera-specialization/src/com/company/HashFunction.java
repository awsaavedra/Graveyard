package com.company;

import java.util.ArrayList;
import java.util.Arrays;

//hash functions: what options exist and which ones should you use for a given situation?
//collision resolution techniques:
// linear probing, random probing, and linear chaining (linked list)
// Resizing: when do you resize and what are the drawbacks?
//No ordered data which is a challenge, why?
public class HashFunction {
    String[] theArray;
    int arraySize;
    int itemsInArray = 0;

    public static void main(String[] args) {
        HashFunction theFunc = new HashFunction(31);

        String[] elementsToAdd2 = { "100", "510", "170", "214", "268", "398",
                "235", "802", "900", "723", "699", "1", "16", "999", "890",
                "725", "998", "978", "988", "990", "989", "984", "320", "321",
                "400", "415", "450", "50", "660", "624" };

        String[] elementsToAdd3 = { "30", "60", "90", "120", "150", "180",
                "210", "240", "270", "300", "330", "360", "390", "420", "450",
                "480", "510", "540", "570", "600", "989", "984", "320", "321",
                "400", "415", "450", "50", "660", "624" };

        theFunc.hashFunction2(elementsToAdd3, theFunc.theArray);
        theFunc.increaseArraySize(60);
        theFunc.displayTheStack();
    }

    public void hashFunction1(String[] stringsForArray, String[] theArray) {
        for(int n = 0; n < stringsForArray.length; n++){
            String newElementVal = stringsForArray[n];
            theArray[Integer.parseInt(newElementVal)] = newElementVal;
        }
    }

    public void hashFunction2(String[] stringsForArray, String[] theArray) {
        for(int n = 0; n < stringsForArray.length; n++){
            String newElementVal = stringsForArray[n];

            int arrayIdx = Integer.parseInt(newElementVal) % 29;
            System.out.println("Modulus Index = " + arrayIdx + " for value " + newElementVal);
            while(theArray[arrayIdx] != "-1"){
                ++arrayIdx;
                System.out.println("Collision Try " + arrayIdx + " Instead");
                arrayIdx %= arraySize;
            }
            theArray[arrayIdx] = newElementVal;
        }
    }

    public void increaseArraySize(int minArraySize){
        int newArraySize = getNextPrime(minArraySize);

        moveOldArray(newArraySize);
    }

    private void moveOldArray(int newArraySize) {
        String[] cleanArray = removeEmptySpacesInArray(theArray);
        theArray = new String[newArraySize];
        arraySize = newArraySize;
        fillArrayWithNeg1();
        hashFunction2(cleanArray, theArray);
    }

    private String[] removeEmptySpacesInArray(String[] arrayToClean) {
        ArrayList<String> stringList = new ArrayList<>();
        for(String theString : arrayToClean){
            if(!theString.equals("-1") && !theString.equals("")){
                stringList.add(theString);
            }
        }
        return stringList.toArray( new String[stringList.size()]);
    }

    private void fillArrayWithNeg1() {
        Arrays.fill(theArray, "-1");
    }

    public boolean isPrime(int number){
        if(number % 2 == 0)
            return false;
        for( int i = 3; i * i <= number; i += 2){
            if( number % i == 0)
                return false;
        }
        return true;
    }

    public int getNextPrime(int minNumberToCheck){
        for( int i = minNumberToCheck; true; i++){
            if(isPrime(i)){
                return i;
            }
        }
    }

    public String findKey(String key){
        int arrayIdxHash = Integer.parseInt(key) % 29;

        while(theArray[arrayIdxHash] != "-1"){
            if(theArray[arrayIdxHash] == key){
                System.out.println(key + " was found in index " + arrayIdxHash);
                return theArray[arrayIdxHash];
            }

            ++arrayIdxHash;
            arrayIdxHash %= arraySize;
        }

        return null;
    }

    HashFunction(int size){
        arraySize = size;
        theArray = new String[size];
        Arrays.fill(theArray, "-1");
    }

    public void displayTheStack(){
        int increment = 0;
        for(int i = 0; i < 3; i++){
            increment += 10;
            for(int n = 0; n < 71; n++){
                System.out.print("-");
            }

            System.out.println();

            for(int n = increment - 10; n < increment; n++){
                System.out.format("| %3s " + " ", n);
            }

            System.out.println("|");

            for(int n = 0; n < 71; n++){
                System.out.print("-");
            }
            System.out.println();

            for (int n = increment - 10; n < increment; n++){
                if (theArray[n].equals("-1"))
                    System.out.print("|       ");
                else
                    System.out.print(String.format("| %3s " + " ", theArray[n]));
            }

            System.out.println();

            for(int n = 0; n < 71; n++){
                System.out.print("-");
            }
            System.out.println();

        }
    }
}
