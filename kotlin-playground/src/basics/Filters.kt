package basics

// Are filters eager or lazy? Could it be both, and if so, how is that possible?

fun main(args: Array<String>){
//    val decoration = listOf("rock", "pagoda", "plastic plant", "alligator", "flowerpot")

//    println(decoration.filter{true})
    // single quote '' designate characters and double quotes "" designate strings

//    println(decoration.filter { it[0] == 'p' })
//    basics.eagerExample()
//    basics.lazyExample()
    filterSpices()
}

// Are filters by default eager or lazy? Why was this design decision made?
fun eagerExample(){
    val decorations =  listOf("rock", "pagoda", "plastic plant", "alligator", "flowerpot")

    val eager = decorations.filter { it[0] == 'p' }
    println(eager)

}

fun lazyExample(){

    val decorations =  listOf("rock", "pagoda", "plastic plant", "alligator", "flowerpot")
    // apply filter lazily
    val filtered = decorations.asSequence().filter { it[0] == 'p' }
    println(filtered)
    println(filtered.toList())

    // apply map lazily
    val lazyMap = decorations.asSequence().map{
        println("map: $it")
        it
    }

    println(lazyMap)
    println("first: ${lazyMap.first()}")
    println("all: ${lazyMap.toList()}")
}

fun filterSpices(){
    val spices = listOf("curry", "pepper", "cayenne", "ginger", "red curry", "green curry", "red pepper" )
    val sortByLength = spices.filter { it.contains("curry") }.sortedBy { it.length }
    println(sortByLength)

    val startsWithC = spices.filter { it.startsWith('c')}.filter { it.endsWith('e') }
//    val startsWithCTwo = spices
    println(startsWithC)
    val firstThreeElements = spices.take(3).filter{it.startsWith('c')}
    println(firstThreeElements)
}
