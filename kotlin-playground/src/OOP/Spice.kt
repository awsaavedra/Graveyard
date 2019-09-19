package OOP

class SimpleSpice{
    var name: String = "curry"
    var spiceLvl: String = "mild"
    var heat: Int = 0
        get() { return 5 }
}

fun main(args: Array<String>){
    val simpleSpice = SimpleSpice()
    println("${simpleSpice.name} ${simpleSpice.heat}")
}

class Spice(val name: String, val spiciness: String = "mild") {

    var spices = listOf(
        Spice("curry", "mild"),
        Spice("ginger", "medium"),
        Spice("cayenne", "spicy"),
        Spice("red curry", "medium"),
        Spice("green curry", "mild"),
        Spice("hot pepper", "extremely spicy")
    )

    val spice = Spice("cayenne", spiciness = "spicy")
    val spiceList = spices.filter { it.heat < 5}


    private val heat: Int
        get() {
            return when(spiciness){
                "mild" -> 1
                "medium" -> 3
                "spicy" -> 5
                "very spicy" -> 7
                "extremely spicy" -> 10
                else -> 0
            }
        }

    fun makeSalt(){
        Spice("Salt")
    }

}