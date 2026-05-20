package Spices

class SimpleSpice{
    var name: String = "curry"
    var spiceLvl: String = "mild"
    var heat: Int = 0
        get() { return 5 }
}

fun main(args: Array<String>){
    val simpleSpice = SimpleSpice()
    println("${simpleSpice.name} ${simpleSpice.heat}")

    val spiceCabinet = listOf(SpiceContainer(Curry("Yellow Curry", "mild")),
        SpiceContainer(Curry("Red Curry", "medium")),
        SpiceContainer(Curry("Green Curry", "spicy")))

    for (element in spiceCabinet) println(element.label)
}

abstract class Spice(val name: String, val spiciness: String = "mild", color: SpiceColor) : SpiceColor by color {
    abstract fun prepareSpice()
}

class Curry(name: String, spiciness: String,
            color: SpiceColor = YellowSpiceColor) : Spice(name, spiciness, color), Grinder {
    override fun grind() {
    }

    override fun prepareSpice() {
        grind()
    }
}


interface Grinder{
    fun grind(){
        println("grinding spices")
    }
}

interface SpiceColor {
    val color: String
}

object YellowSpiceColor: SpiceColor{
    override val color: String = "Yellow"
}

data class SpiceContainer (var spice: Spice){
    val label = spice.name
}
