package day1

import java.io.File

/**
 * Created by richi on 2016-01-01.
 */

class CheckPath

fun main (args: Array<String>) {
    val path = CheckPath().javaClass.getResource("input.txt").toURI()
    val line = File(path).readText()
    var result = 0
    var sum = 0
    line.asSequence()
        .map {
            if (it == '(') 1 else -1
        }
        .forEach {
            result ++
            sum += it
            if (sum == -1) {
                println ("Basement : $result")
            }
        }
}