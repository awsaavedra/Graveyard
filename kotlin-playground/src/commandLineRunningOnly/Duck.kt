
// What does the compiler do without a constructor?
class Duck {

  fun quack(){
    println("Quack! Quack! Quack!")
  }
}

fun main(args: Array<String>){
  var myDuck = Duck()
  myDuck.quack()
}
