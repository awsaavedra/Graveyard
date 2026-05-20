class Solution {
    fun reverseString(s: CharArray): CharArray {
        for( i in s.size downTo s.size / 2){
            var tmp = s[s.size - 1 - i]
            s[s.size - 1 - i] = s[i]
            s[i] = tmp
        }

        return s
    }

    companion object {
        fun main(solution: Solution, args: Array<String>){
            var str = "hello".toCharArray()
            println(solution.reverseString(str))
        }
    }
}
