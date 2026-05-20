package Beyond_The_Basics

fun main(args: Array<String>){

    // Note: listOf(~~) is immutable
    val symptoms = mutableListOf("white spots", "red spots", "not eating", "bloated", "belly up")

    symptoms.add("white fungus")

    symptoms.remove("white fungus")

    println(symptoms.subList(4, symptoms.size)) // What is the output?

    listOf(1, 5, 3).sum() // 9

    println(listOf("a", "b", "cc").sumBy { it.length }) // what's the output?

    val cures = mapOf("white spots" to "Ich", "red sores" to "hole disease")
    println(cures.get("white spots"))
    println(cures["white spots"])

    println(cures.getOrDefault("bloating", "sorry I don't know"))

    cures.getOrElse("bloating"){ "No cure for this" }

    // remember what mapOf is? Immutable map
    val inventory = mutableMapOf( "fish net" to 1)

    inventory.put("tank scrubber", 3)

    inventory.remove("fish net")
}