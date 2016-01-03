package day5

/**
 * Created by richi on 2016-01-02.
 */
class CheckPath

fun checkPairOverTwin (str: String): Boolean {
    for (i in 0 until str.length-2) {
        val c1 = Pair(str[i], str[i+1])
        var count = 1
        for (j in i+2 until str.length-1) {
            val c2 = Pair(str[j], str[j+1])
            if (c1 == c2) {
                count++
                if (count >= 2) return true
            }
        }
    }
    return false
}

fun checkBelongStr (str: String): Boolean {
    for (i in 0 until str.length-2) {
        if (str[i] == str[i+2]) {
            return true
        }
    }
    return false
}

fun testCode () {
    val test1 = "qjhvhtzxzqqjkmpb"
    val test2 = "xxyxx"
    val test3 = "uurcxstgmygtbstg"
    val test4 = "ieodomkazucvgmuy"

    val t1 = checkPairOverTwin(test1) && checkBelongStr(test1)
    val t2 = checkPairOverTwin(test2) && checkBelongStr(test2)
    val t3 = checkPairOverTwin(test3) && checkBelongStr(test3)
    val t4 = checkPairOverTwin(test4) && checkBelongStr(test4)

    println (t1)
    println (t2)
    println (t3)
    println (t4)
}

fun main (args: Array<String>) {
    val f = java.io.File(CheckPath().javaClass.getResource("input.txt").toURI())
    println (f.readLines().filter { data -> checkPairOverTwin(data) && checkBelongStr(data) }
        .size)
}