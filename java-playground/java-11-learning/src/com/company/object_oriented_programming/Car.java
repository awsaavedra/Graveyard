package com.company.object_oriented_programming;

/*
    An Object has state and behavior
    For more object oriented programming concepts: https://docs.oracle.com/javase/tutorial/java/concepts/index.html
    Key questions:
        - What is an object?
        - What is a Class?
        - What is inheritance?
        - What is an interface?
        - What is a package?

    Deeper questions (search on stack overflow or other tools):
        - What is object oriented programming such a ubiquitous paradigm?
        - What are some of the trade-offs between OOP and say pure functional programming?

    For a nice survey of different programming paradigms:
        https://en.wikipedia.org/wiki/Comparison_of_programming_paradigms#Main_paradigm_approaches

        Mostly, the two used right now are functional programming (not pure functional)
        and OOP (or prototypical i.e. Javascript...which is moving to more standard OOP).
 */

public class Car {

    // State
    private int doors;
    private int wheels;
    private String model;
    private String engine;
    private String colour;

    // Behaviors
    public void setModel(String model){
        String validModel = model.toLowerCase();
        if( validModel.equals("carrera") || validModel.equals("holden")){
            this.model = model;
        }else{
            this.model = "Unknown";
        }
    }

    public String getModel() {
        return this.model;
    }
}


