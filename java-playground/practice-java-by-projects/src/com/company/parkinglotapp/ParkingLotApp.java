package com.company.parkinglotapp;


/*


    https://github.com/awsaavedra/system-design-primer/blob/master/solutions/object_oriented_design/parking_lot/parking_lot.ipynb

    Design a parking lot
    Constraints and assumptions
    What types of vehicles should we support?
        Motorcycle, Car, Bus
    Does each vehicle type take up a different amount of parking spots?
        Yes
    Motorcycle spot -> Motorcycle
    Compact spot -> Motorcycle, Car
    Large spot -> Motorcycle, Car
    Bus can park if we have 5 consecutive "large" spots
    Does the parking lot have multiple levels?
    Yes

    Questions:
        - How do you decide what the layout of each level is?
        - How do you populate each level?
        - How do you remove from each level when a vehicle leaves?


    Bonus:

        - How can we track whether levels have available spots for users?
        -
 */

import java.util.ArrayList;

public class ParkingLotApp {
    ArrayList[][] levels = new ArrayList[8][8];


}
