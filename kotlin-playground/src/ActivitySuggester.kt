fun main(args: Array<String>){
    println(whatShouldIDoToday("sad"))

    var fortune = ""

    repeat(10){
        fortune = getFortuneCookie()
        println("\nYour fortune is: $fortune")
        if(fortune.contains("Take it easy")) {
            return
        }

    }
    getFortuneCookie()
}

fun whatShouldIDoToday(mood: String,
                       weather: String = "sunny",
                       temperature: Int = 24): String {
    return when {
        shouldIGoForAWalk(mood, weather) -> "go for a walk"
        shouldIStayInBed(mood, weather, temperature) -> "stay in bed"
        shouldIGoSwimming(temperature) -> "go swimming"
        else -> "Stay home and read."
    }
}


fun shouldIGoSwimming(temperature: Int) = temperature > 35
fun shouldIStayInBed(mood: String, weather: String, temperature: Int) = mood == "sad" && weather == "rainy" && temperature == 0
fun shouldIGoForAWalk(mood:String, weather: String) =  mood == "happy" && weather == "Sunny"


// Fortune cookie program



fun getFortuneCookie(): String {
    val fortunes = listOf("Great success ahead!", "Look to the stars and find wisdom",
        "Things will go well for you today.",
        "Enjoy a wonderful day of success.",
        "Be humble and all will turn out well.",
        "Today is a good day for exercising restraint.",
        "Take it easy and enjoy life!",
        "Treasure your friends because they are your greatest fortune.")
    println("Enter your birthday: ")

    var birthday = readLine()?.toIntOrNull() ?: 1

    return fortunes[birthday.rem(fortunes.size)]
}

