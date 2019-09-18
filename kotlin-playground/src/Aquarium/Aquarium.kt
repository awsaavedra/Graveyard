package Aquarium

// Package visibility: In Kotlin, everything is public by default
// visibility: public, private, internal module (anywhere inside project)
// More on visibility modifiers: https://kotlinlang.org/docs/reference/visibility-modifiers.html
class Aquarium {
    val width: Int = 20
    var height: Int = 40
    val length: Int = 100

    var volume: Int
        get() = width * height * length / 1000
        set(value) {height = (value * 1000) / (width * length)}

}