package Beyond_The_Basics

fun main(args: Array<String>){

    val list :List<Int> = listOf(1,2,3,4,5,6,7,8,9,10)

    println(reverseTheList(list))

    println(list.reversed())
}

fun reverseTheList(list: List<Int>): List<Int>{
    val result = mutableListOf<Int>()

    for(i in list.size - 1 downTo 0){
        result.add(list[i])
    }

    return result
}