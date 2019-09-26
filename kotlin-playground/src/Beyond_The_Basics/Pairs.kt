package Beyond_The_Basics

fun main(args: Array<String>){
    println("hello!")

    val equipment = "fishnet" to "catching fish" to "of big size" to "and strong"
    val fishnet = "fishnet" to "catching fish"

    println(equipment)
    println(equipment.first)
    println(equipment.first.first)

    val (tool, use) = fishnet //destructuring declarations

    println("The $tool is a tool for $use.")


    println(fishnet.toString())
    println(fishnet.toList())

    fun giveMeATool(): Pair<String, String> {
        return ("fishnet" to "catching")
    }

    val (tool2, use2) = giveMeATool()

    println(tool2)

    println(use2)
}