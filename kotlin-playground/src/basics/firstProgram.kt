package basics
fun main(args: Array<String>){
//    val name = "Misty"
//    val height = 9
//    println("Hello")
//    println("My cat is called $name")
//    println("My cat is $height inches tall")
//    val a = 6
//    val b = 7
//    val c = a + b + 10
//    val str = c.toString()
//
//    val numList = arrayOf(1, 2, 3)
//    var x = 0
//    while ( x < 3 ) {
//        println("Item $x is ${numList[x]}")
//        x = x + 1
//    }
//
// https://medium.com/@xabaras/kotlin-val-is-read-only-not-immutable-585ce2e5359b
// tl;dr: val is read-only (assign once), NOT immutable not final in Java
// var is a mutable assignment
//
//    var x = 3
//    while( x > 20){
//        x = x - 1
//        print(" x is now $x.")
//    }
//
//    for( i in 1..10){
//        x = x + 1
//        print(" x is now $x")
//    }
//
//    val name = "Hello"
//    if( x == 20){
//        println(" x must be 20.")
//    }else{
//        println(" x isn't 20.")
//    }
//    if(name.equals("Cormoran"))
//        println("$name Strike")

//    //Most white space doesn't matter
//    var w          =             3
//    // Kotlin has while, do-while, and for
//    // boolean tests
//    // <, >, ==, <=, and >=
//    var x = 4 // assign 4 to x
//    while( x > 3){
//        // The loop code will run as x is greater than 4
//        println(x)
//        x = x - 1
//    }
//
//    var z = 27
//    while (z == 10){
//        //The loop code will not run as z is 27
//        println(z)
//        z = z + 6
//    }
    var x = 1
    println("Before the loop. x = $x.")
    while (x < 4){
        println("In the loop. x = $x.")
        x = x + 1
    }
    println("After the loop. x = $x.")
}