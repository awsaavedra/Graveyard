package basics
import java.util.*


fun main(args: Array<String>){
    println("Hello, ${args[0]}")
    feedTheFish()

    // other control flow operators

    var bubbles = 0
    while(bubbles < 50){
        bubbles++;
    }

    repeat(2){
        println("A fish is swimming")
    }

    //what's the difference between an expression and statement in Kotlin?
    // Why doesn't the below work?

//    val noValue = for(x in 1..2){}
//    val notThisEither = while(false){}


}

fun shouldChangeWater(
    day:String,
    temperature: Int = 22,
    dirty: Int = getDirtySensorReading()
): Boolean{


    // evaluation
    val isTooHot = temperature > 30
    val isDirty = dirty > 30
    val isSunday = day == "Sunday"

    return when{

        // control flow logic using when key word
        isTooHot(temperature) -> true
        isDirty(dirty) -> true
        isSunday(day) -> true
        else -> false
    }
}

fun isTooHot(temperature: Int) = temperature > 30

fun isDirty(dirty: Int) = dirty > 30

fun isSunday(day: String) = day == "Sunday"


// Default parameters are called at call time with kotlin
fun makeNewAquarium() = println("Building a new acquarium.....")

// expensive function to run
// everytime you run the basics.aquariumStatusReport(aquarium) a new aquarium
// w/o passing a value for the aquarium argument a new aquarium will be made
fun aquariumStatusReport(aquarium: Any = makeNewAquarium()){ // What does any do?

}

fun feedTheFish(){
    val day = randomDay()
    val food = fishFood(day)
    println("Today is $day and the fish eat $food")
    shouldChangeWater(day, 20, 50)
    shouldChangeWater(day)
    shouldChangeWater(day, dirty = 50)

    if(shouldChangeWater(day)){
        println("Change the water today")
    }
}

fun randomDay(): String {
    val week = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    return week[Random().nextInt(7)]
}

fun fishFood(day : String): String{

    var food = "fasting"

    return when(day){
        "Monday" -> "flakes"
        "Tuesday" -> "pellets"
        "Wednesday" -> "redworms"
        "Thursday" -> "granules"
        "Friday" -> "mosquitoes"
        "Sunday" -> "plankton"
        else -> "fasting"

    }
}


fun getDirtySensorReading() = 20




//fun swim(time: Int, speed: String = "fast"){
//    println("swimming $speed")
//}


//fun basics.main(args: Array<String>){
//    println("Hello, wolrd!")
//    dayOfWeek()
//
//    // fetching first element is an expression
//    // So use {} instead of just dollar sign
//    println("Hello, ${args[0]}")
//
//    val isUnit = println("This is an expression")
//    println(isUnit)
//
//    val temperature = 10
//    val isHot = if (temperature > 50) true else false
//    println(isHot)
//
//    val msg = "You are ${ if (temperature > 50) "fried" else "safe"} fish"
//    println(msg)
//}
//
//fun dayOfWeek(){
//    val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
//    when(day){
//        1 -> "MONDAY"
//        2 -> "TUESDAY"
//        3 -> "WEDNESDAY"
//        4 -> "THURSDAY"
//        5 -> "FRIDAY"
//        6 -> "SATURDAY"
//        7 -> "SUNDAY"
//        else -> "Time has stopped"
//    }
//
//    print("What day is it today? " + day)
//}