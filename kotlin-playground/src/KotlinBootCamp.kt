import java.util.*
import kotlin.math.pow

fun main( args: Array<String>){
    println("Hello, world!")

    val helloFish = "Hello fish"

    val numOfFish = 5
    val fish = "fish"

    val numOfPlants = 12
    val plant = "plant"

    println("I have $numOfFish fish and $numOfPlants plants")
    println("I have ${numOfFish + numOfPlants} fish and plants")
    println( fish == plant)
    println( fish == fish)
    println( fish != plant)

    if( numOfFish > numOfPlants) println("Good ratio!")
    else println("unhealthy ratio")

    val newFishCount = 50
    if( newFishCount in 1..100) println(newFishCount)

    when (newFishCount){
        0 -> println("Empty tank")
        50 -> println("Full tank")
        else -> println("Perfect!")
    }

    // Create three String variables for trout, haddock, and snapper
    // Use a String template to print whether you do or don't like to eat
    // these kinds of fish
    val trout = "trout"
    var haddock = "haddock"
    var snapper = "snapper"
    println("$trout is good, but I don't like $haddock or $snapper")


    /*
    when statements in Kotlin are like case or switch statements in other languages.

    Create a when statement with three comparisons:

    If the length of the fishName is 0, print an error message.
    If the length is in the range of 3...12, print "Good fish name".
    If it's anything else, print "OK fish name".
     */

    val fishName = "Alvin"

    when(fishName.length){
        0 -> println("no fish name")
        in 3..12 -> println("Good fish name")
        else -> println("OK fish name")
    }

    var myList = mutableListOf("tuna", "salmon", "shark")
    myList = mutableListOf("koi", "goldfish")

    var fishy = 12
    var plants = 5
    val swarm = listOf(fishy, plants)
    val bigSwarm = arrayOf(swarm, arrayOf("dolphin", "whale", "orka"))

    val numbers = intArrayOf(1,2,3)

    val mix = arrayOf("fish", 2)

    println(Arrays.toString(mix))
    println(Arrays.toString(bigSwarm))

    val array = Array(5){ it * 2}
    println(array.asList())


    // Ranges

    for ( e in swarm) println(e)

    for ((idx, el) in swarm.withIndex()){
        println("Fish at $idx is $el")
    }

    for (i in 'b'..'g') print(i)
    for (i in 1..5) println(i)

    for (i in 5 downTo 1) print(i)
    for (i in 3..6 step 2) print(i)

    /*
        Read the code below, and then select the correct array
        initialization that will display the corresponding output.
    */

    val array2 = Array(7){ 1000.0.pow(it)}
    val sizes = arrayOf("byte", "kilobyte", "megabyte", "gigabyte",
        "terabyte", "petabyte", "exabyte")
        for ((i, value) in array2.withIndex()) {
        println("1 ${sizes[i]} = ${value.toLong()} bytes")
    }


    /*
            Practice Time
    Looping over arrays and lists is a fundamental technique that has a lot of flexibility
    in Kotlin. Let's practice.

    Basic example
    Create an integer array of numbers called numbers, from 11 to 15.
    Create an empty mutable list for Strings.
    Write a for loop that loops over the array and adds the string
    representation of each number to the list.
    Challenge example
    How can you use a for loop to create (a list of) the numbers
    between 0 and 100 that are divisible by 7?
     */

    val list3: MutableList<Int> = mutableListOf()


    for( i in 0..100 step 7) list3.add(i)
    print(list3)

    // more concise
    for(i in 0..100 step 7) print(i.toString() + ", ")

}