package day2

import java.io.File

/**
 * Created by richi on 2016-01-01.
 */

class CheckPath

// a x b x c
// sum = (a+a+b+b) + a*b*c

fun parseLine(l: String): Triple<Int,Int,Int> {
    val pat = Regex("""(\d+)x(\d+)x(\d+)""").toPattern()
    val mat = pat.matcher(l)
    if (mat.find()) {
        val v = (1..3).map { mat.group(it).toInt() }.sorted()
        return Triple(v[0], v[1], v[2])
    } else {
        throw Exception("Parse Error : $l")
    }
}

fun getRibbonLength (i : Triple<Int,Int,Int>): Int {
    val a = i.first
    val b = i.second
    val c = i.third

    return (a+a+b+b) + (a*b*c)
}

fun main (args: Array<String>) {
    val p = CheckPath().javaClass.getResource("input.txt").toURI()

    val list = File(p).readLines().filter { it != "" }.map { parseLine(it) }
    val result = list.fold(0) { acc,item -> acc + getRibbonLength(item) }
    println (result)
}
