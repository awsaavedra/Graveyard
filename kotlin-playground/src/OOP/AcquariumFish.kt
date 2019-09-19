package OOP

// What are the differences between abstract classes and interfaces?
// Can abstract classes or interfaces have constructors?
// Can both contain implementations of methods?
// What is interface delegation and why is it useful to a class via composition?
abstract class AcquariumFish{
    abstract val color: String
}

class Shark(override val color: String = "gray") : AcquariumFish(), FishAction {
    override fun eat() {
        println("hunt and eat fish")
    }
}

class Plecostomus(override val color: String = "gold") : AcquariumFish(), FishAction {
    override fun eat() {
        println("munch on algae")
    }
}

interface FishAction{
    fun eat()
}

// Use an interface if you have a lot of methods,and one or two default implementations
// Use an abstract class anytime you cannot complete a class