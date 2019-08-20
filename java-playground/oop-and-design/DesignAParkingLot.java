import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/*
    Asked In : Amazon, Apple, Google and many more interviews

Solution: For our purposes right now, we’ll make the following assumptions. 
We made these specific assumptions to add a bit of complexity to the problem without adding too much. 
If you made different assumptions, that’s totally fine.

    1) The parking lot has multiple levels. Each level has multiple rows of spots.
    2) The parking lot can park motorcycles, cars, and buses.
    3) The parking lot has motorcycle spots, compact spots, and large spots.
    4) A motorcycle can park in any spot.
    5) A car can park in either a single compact spot or a single large spot.   
    6) A bus can park in five large spots that are consecutive and within the same row. It cannot park in small spots.
*/

public class DesignAParkingLot {
    public static void main(String args[] ) throws Exception {
        Car c = new Car();
        System.out.println("")
    }
}

public abstract class Vehicle{
    protected ArrayList<ParkingSpot> parkingSpots = new List<ParkingSpot>();
    protected String licensePlate;
    protected int spaceTaken;
    protected VehicleSize size;
    
    public int getSpotsRequired(){
        return spotsRequired;
    }
    
    public sizeOfVehicle getSize(){
        return size;
    }
    
    public void park(ParkingSpot ps){
        parkingSpots.add(ps);
    }
    
    public void leaveLot(){
        // clear vehicle from spot
        // notify spot it is gone
    }
    
    public abstract boolean fitsInSpot( ParkingSpot spot);
}

public enum VehicleSize{ Motorcycle, Compact, Large }

public Bus extends Vehicle{
    
    public Bus(){
        spaceTaken = 4;
        size = VehicleSize.Large;
    }
}

public Car extends Vehicle {
    public Car(){
        spaceTaken = 1;
        size = VehicleSize.Compact;
    }
}

public Motorcycle extends Vehicle {
    
    public Motorcycle(){
        spaceTaken = 1;
        size = VehicleSize.Motorcycle;
    }
    
}


public class ParkingSpot{
    
    private Vehicle vehicle; 
    private VehicleSize spotSize; 
    private int row; 
    private int spotNumber; 
    private Level level; 

    // public ParkingSpot(Level lvl, int r, int n, VehicleSize s){
        
    // }
    
    public boolean isAvailable(){
        return vehicle == null;
    }
    
    //getters
    public boolean canFitVehicle(Vehicle vehicle){
        return spotSize == vehicle.size; //check vehicle size
    }
    
    public int getRow(){
        return row;
    }
    
    public int getSpotNumber(){
        return spotNumber;
    }
    
    public void removeVehicle(){
        // remove vehicle from spot
        // notify level that a new spot is available
        
    }
    
    //setters
}



