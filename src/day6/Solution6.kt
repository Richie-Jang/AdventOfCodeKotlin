package day6

import java.io.File
import java.net.URI
import java.util.regex.Pattern

/**
 * Created by richi on 2015-12-30.
 */
class TempLook

// light off (1000 x 1000)
val lights = Array(1000) {BooleanArray(1000) {false}}

/**
 * togglestate : 0 -> off, 1 -> on, 2 -> toggle
 */
data class Instructor (val togglestate: Int, val x1: Int, val y1: Int, val x2: Int, val y2: Int)

fun makeInputList (inputfile: URI): List<Instructor> {
    fun parsingLine(l: String): Instructor {
        val status = if (l.startsWith("toggle")) 2 else if (l.contains("off")) 0 else 1
        val pat = Pattern.compile("""[\w\s]+\s(\d+),(\d+)\s\w+\s(\d+),(\d+)""")
        val mat = pat.matcher(l)
        if (mat.find()) {
            val v1 = mat.group(1).toInt();
            val v2 = mat.group(2).toInt();
            val v3 = mat.group(3).toInt();
            val v4 = mat.group(4).toInt();
            return Instructor(status, v1,v2,v3,v4);
        } else {
            throw Exception("$l can not parse")
        }
    }
    val file = File(inputfile)
    require(file.exists()) {"$inputfile is not existed..."}
    return file.readLines().map {parsingLine(it)}.toList()
}

fun main (args: Array<String>) {
    val inlist = makeInputList(TempLook().javaClass.getResource("input.txt").toURI())
    //println ("${inlist.size}")
    inlist.forEach {
        for (y in it.y1 .. it.y2)
            for (x in it.x1 .. it.x2) {
                when (it.togglestate) {
                    0 -> lights[y][x] = false
                    1 -> lights[y][x] = true
                    2 -> lights[y][x] = !(lights[y][x])
                }
            }
    }

    var sum = 0
    for (y in 0 .. 999)
        for (x in 0 .. 999) {
            if (lights[y][x]) sum += 1
        }

    println (sum)
}