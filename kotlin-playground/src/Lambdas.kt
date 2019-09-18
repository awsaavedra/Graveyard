
// Lambdas are also called anonymous classes

// What are higher order functions?
// Why are lambdas useful when dealing with higher order functions?
// e.g. of higher order functions: .filter{~~~~}, .repeat()

fun main( args: Array<String>){
    var swim = { println("swim \n" )}()
    swim

    // Kotlin syntax without type inference
//    val waterFilter = { dirty: Int -> dirty / 2 }

    // modified version over waterFilter using
    // water filter CAN be any function that takes an int and returns an int

    println(waterFilter(dirty))

    // call dirty processor
    dirtyProcessor()


}

val waterFilter: (Int) -> Int =  { dirty -> dirty / 2 }
var dirty = 20
fun feedFish(dirty: Int) = dirty + 10

fun updateDirty(dirty: Int, operation: (Int) -> Int): Int{
    return operation(dirty)
}

fun dirtyProcessor(){
    dirty = updateDirty(dirty, waterFilter)
    dirty = updateDirty(dirty, ::feedFish)
    dirty = updateDirty(dirty){
        dirty ->
        dirty + 50
    }
}