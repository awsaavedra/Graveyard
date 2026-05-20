package com.company;

public class reduceClass {
    public static void main(String[] args) {
        int[] a = {1,7,2,4,6,8};
        System.out.println(maxDifference( a ));
    }

    public static void reduce (int[] vals) {
        int minIndex =0; //O(1)
        for (int i = 0; i < vals.length; i++) { // O(n)
            if (vals[i] < vals[minIndex] ) {
                minIndex = i;
            }
        }
        int minVal = vals[minIndex]; //O(1)
        for (int i=0; i < vals.length; i++) { // O(n)
            vals[i] = vals[i] - minVal;
        }
    }

    //time complexity O(?) = 1 + n + 1 + n = 2n + 2 => O(n)

    public static int maxDifference (int[] vals) {
        int max = 0; // 1
        for (int i=0; i < vals.length; i++) { // n
            for (int j=0; j < vals.length; j++) { // n
                if (vals[i] - vals[j] > max) {
                    max = vals[i] - vals[j];
                }
            }
        }
        return max; // 1
    }

    //time complexity O(?) =  1 + n( c * n) + 1 = c n^2 + 1 => O(n^2)
}
