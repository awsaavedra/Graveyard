package Aquarium

fun main (args: Array<String>){
    buildAquarium()
}

fun buildAquarium(){

    val myAquarium = Aquarium() // like new key word, by calling constructor

    // underneath the hood, kotlin created a getter for all 3 properties
    println("Length: ${myAquarium.length} " +
            "Width: ${myAquarium.width} " +
            "Height: ${myAquarium.height}")

    myAquarium.height = 80
    println("Height: ${myAquarium.height} cm")

    println("Volume: ${myAquarium.volume} liters")
}