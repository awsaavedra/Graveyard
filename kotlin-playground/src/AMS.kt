
import java.util.*

fun main(args: Array<String>){
    println("Hello, ${args[0]}")
    feedTheFish()
}

fun shouldChangeWater(
    day:String,
    temperature: Int = 22,
    dirty: Int = 20): Boolean{

    val isTooHot = temperature > 30
    val isDirty = dirty > 30
    val isSunday = day == "Sunday"
    
    return when{
        isTooHot(temperature) -> true
        isDirty(dirty) -> true
        isSunday(day) -> true
        else -> false
    }
}

fun isTooHot(temperature: Int) = temperature > 30

fun isDirty(dirty: Int) = dirty > 30

fun isSunday(day: String) = day == "Sunday"

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


//fun main(args: Array<String>){
//    getFortuneCookie()
//}
//
//fun getFortuneCookie(): String {
//    val fortunes = listOf("Great success ahead!", "Look to the stars and find wisdom",
//        "Things will go well for you today.",
//        "Enjoy a wonderful day of success.",
//        "Be humble and all will turn out well.",
//        "Today is a good day for exercising restraint.",
//        "Take it easy and enjoy life!",
//        "Treasure your friends because they are your greatest fortune.")
//    println("Enter your birthday: ")
//
//    var birthday = readLine()?.toIntOrNull() ?: 1
//
//    return fortunes[birthday.rem(fortunes.size)]
//}


//fun swim(time: Int, speed: String = "fast"){
//    println("swimming $speed")
//}


//fun main(args: Array<String>){
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