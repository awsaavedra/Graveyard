
// Will it compile? If not, how would you fix it?

class TapeDeck {
  
  var hasRecorder = false

  fun playTape() {
    println("Tape playing")
  }

  fun recordTape() {
    if(hasRecorder) {
      println("Tape recording")
    }
  }
}

fun main(args: Array<String>) {
  t.hasRecord = true
  t.playTape()
  t.recordTape()
}
