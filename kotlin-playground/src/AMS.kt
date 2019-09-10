import java.util.*


fun main(args: Array<String>){
    println("Hello, ${args[0]}!")
    feedTheFish()
}

fun feedTheFish(){
    val day = randomDay()
    val food = "pellets"
    println("Today is $day and the fish eat $food")
}

fun randomDay(): String {
    val week = listOf("Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday", "Saturday", "Sunday")

    return week[Random().nextInt(7)]
}


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

