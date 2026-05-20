package OOP

fun main (args: Array<String>){
//    buildAquarium()
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

    // To make it more readable, pass in named parameters
    val smallAquarium = Aquarium(length = 20, width = 15, height = 30)
    println("Small OOP: ${smallAquarium.volume} liters")

    makeFish()
}

fun makeFish(){
    val shark = Shark()
    val pleco = Plecostomus()

    println("Shark: ${shark.color} \nPlecostomus: ${pleco.color}")

    shark.eat()
    pleco.eat()
}



