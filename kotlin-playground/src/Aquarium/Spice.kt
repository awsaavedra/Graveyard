package Aquarium

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