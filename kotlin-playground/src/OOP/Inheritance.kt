package OOP


fun main(args: Array<String>){
    delegate()
}
fun delegate(){
    val pleco = Plecostomus2()
    println("Fish has color ${pleco.color}")
    pleco.eat()
}

interface FishAction2 {
    fun eat()
}

interface FishColor{
    val color: String
}

// How is this class implemented? Through interface delegation
// Use interfaces to plugin behavior instead of subclassing
class Plecostomus2(fishColor: FishColor = GoldColor): FishAction2 by PrintingFishAction("a lot of algae"),
    FishColor by fishColor

//Singleton pattern
object GoldColor: FishColor{
    override val color = "gold"
}

object RedColor: FishColor{
    override val color = "red"
}

class PrintingFishAction( val food: String): FishAction2 {
    override fun eat(){
        println(food)
    }
}