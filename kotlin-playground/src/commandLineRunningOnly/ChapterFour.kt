package Head_First_Kotlin


// Kotlin way, so clean, like the dog should be
class Dog(val name: String, var weight: Int, val breed: String){

    fun bark(){
        println(if (weight < 20) "Yip!" else "Woof!")
    }
}



// below is the Java-y way, so nasty
class JavaDog{

    // properties
    val name
    val weight
    val breed

    // initializer, aka constructor
    constructor(name: String, weight: Int, breed: String){
        this.name
        this.weight
        this.breed
    }

    // below are functions/methods within the class
    fun bark(){
        println(if (weight < 20) "Yip!" else "Woof!")
    }
}

fun main(args: Array<String>){
    var myDog = Dog("Fido", 70, "Mixed")
    println(myDog.name)
}