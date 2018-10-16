package com.company;

public class BinarySearch {

    public static String findAirportCodeBS(String toFind, Airport[] airports){
        int low = 0;
        int high = airports.length;
        int mid;

        while( low <= high){
            mid = (low + high) / 2;
            int compare = toFind.compareTo(airports[mid].getCity());
            if(compare < 0 ){
                high = mid - 1;
            }else if ( compare > 0){
                high = mid + 1;
            }else{
                return airports.getCode();
            }
        }

        return null;
    }
}
