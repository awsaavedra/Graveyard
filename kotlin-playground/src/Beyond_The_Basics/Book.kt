package Beyond_The_Basics

/*
Practice Time
Let's go through an example of getting information about a book in the format of a Pair.
Generally, you want information about both the title and the author, and perhaps also the year.

Let’s create a basic book class, with a title, author, and year.
Of course, you could get each of the properties separately.
Create a method that returns both the title and the author as a Pair.
Create a method that returns the title, author and year as a Triple.
Use the documentation to find out how to use Triple.
Create a book instance.
Print out the information about the book in a sentence,
such as: “Here is your book X written by Y in Z.”
 */

class Book( val title: String, val author: String, val year:Int){


    fun getTitleAuthor(): Pair<String, String>{
        return (title to author)
    }

    fun getTitleAuthorYear(): Triple<String, String, Int> {
        return Triple(title, author, year)
    }
}


fun main(args: Array<String>){
    val book = Book("Hamlet", "Shakespeare", 1451)
    val (title, author, year) = book.getTitleAuthorYear()
    val getTitleAuthorYear = book.getTitleAuthorYear()

    // String templating
    println("The book is $title written by $author around $year")

    println("Written another way: The book is ${getTitleAuthorYear.first} by " +
            "${getTitleAuthorYear.second} around ${getTitleAuthorYear.third}")
}