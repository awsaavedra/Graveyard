package Aquarium

// Package visibility: In Kotlin, everything is public by default
// visibility: public, private, internal module (anywhere inside project)
// More on visibility modifiers: https://kotlinlang.org/docs/reference/visibility-modifiers.html
// constructor arguments: length, width, height
// adding var to ctor creates the member properties for you
class Aquarium (var length: Int = 100, var width: Int = 20, var height: Int = 40) {

    var volume: Int
        get() = width * height * length / 1000
        set(value) {height = (value * 1000) / (width * length)}

    var water = volume * 0.9

    constructor(numberOfFish: Int): this(){
        val water: Int = numberOfFish * 2_000 // cm3
        val tank: Double = water + water * 0.1
        height = (tank / (length * width)).toInt()

    }
}