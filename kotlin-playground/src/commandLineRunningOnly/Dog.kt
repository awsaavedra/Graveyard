
// the properties defined in the ctor are created first
class Dog(val name: String, var weight: Int, breedParam:String){

  // After the ctor properties, the initializer block runs next
  init {
    println("Dog $name has been created.")
  }

  var activities = arrayOf("Walks")
  var breed = breedParam.toUpperCase()
  // var temperament: String // since it hasn't been initialized it won't compile.
  // The second initializer block runs after the properties
  // have been created
  init {
    println("The breed is $breed")
  }

  fun bark(){
    println( if (weight < 20) "Yip!" else "Woof!")
  }
}

fun main(args: Array<String>){
  val d = Dog("Fido", 30, "Lab")

  d.bark()
}
