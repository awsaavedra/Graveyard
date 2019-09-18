package Aquarium

class Fish(val friendly: Boolean = true, volumeNeeded: Int) {
    val size: Int

    init { //init blocks run before or after secondary ctor?
        println("first init block")
    }

    constructor(): this(true, 9){
        println("Running secondary constructor")

    }
    init{
        // just like ctor, how?
        // Can you initialize properties?
        if(friendly){
            size = volumeNeeded
        }else{
            size = volumeNeeded * 2
        }
    }

    init{
        println("constructed fish of size $volumeNeeded final size ${this.size}")

    }

    init{
        println("last init block")
    }
}

fun makeDefaultFish() = Fish(true, 50)

fun fishExample(){
    val fish = Fish(true, 20)
    println("Is the fish friendly? ${fish.friendly}. It needs volume ${fish.size}")
}