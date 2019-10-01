class Song(var title: String, val artist: String) {
  fun play(){
    println("Playing the song $title by $artist")
  }

  fun stop(){
    println("Stopped playing $title")
  }
}

fun main(args: Array<String>){
  val songOne = Song("The Mesopotamians", "They Might Be Giants")
  val songTwo = Song("Going Underground", "The Jam")
  val songThree = Song("Make Me Smile", "Steve Harley")

  songOne.play()
  songOne.stop()
  songTwo.play()
  songTwo.stop()
  songThree.play()
}
