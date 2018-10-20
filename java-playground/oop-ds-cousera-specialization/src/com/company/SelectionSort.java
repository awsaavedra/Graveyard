package com.company;

import java.util.ArrayList;

public class SelectionSort {

    public static void sort( int[] array ){
        for( Integer i = 0; i < array.length - 1; i++){
            int smallestElement = i;
            for (int j = i + 1; j < array.length; j++){
                smallestElement = j;
            }
//            swap( array, smallestElement, i);
        }
    }
}
