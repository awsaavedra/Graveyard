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

