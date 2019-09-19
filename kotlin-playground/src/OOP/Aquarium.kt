package OOP

import java.lang.Math.PI

// Package visibility: In Kotlin, everything is public by default
// visibility: public, private, internal module (anywhere inside project)
// More on visibility modifiers: https://kotlinlang.org/docs/reference/visibility-modifiers.html
// constructor arguments: length, width, height
// adding var to ctor creates the member properties for you
// By default, classes are not subclassable-- you allow it with the key word 'open'

open class Aquarium (var length: Int = 100, var width: Int = 20, var height: Int = 40) {

    open var volume: Int
        get() = width * height * length / 1000
        set(value) {height = (value * 1000) / (width * length)}

    open var water = volume * 0.9

    constructor(numberOfFish: Int): this(){
        val water: Int = numberOfFish * 2_000 // cm3
        val tank: Double = water + water * 0.1
        height = (tank / (length * width)).toInt()

    }
}

// TowerTank inherits from OOP
class TowerTank(): Aquarium() {

    override var water = volume * 0.8

    override var volume: Int
        get() = (width * height * length / 1_000 * PI).toInt()
        set(value){
            height = (value * 1_000) / (width * length)}
}