package day6

/**
 * Created by richi on 2016-01-02.
 */

enum class Switch {
    ON, OFF, TOGGLE
}
class Instruct(val sw: Switch, val sx: Int, val sy: Int, val ex: Int, val ey: Int)

val light2s = Array(1000) {IntArray(1000) {0}}

fun parseLine (l: String): Instruct {
    val sw =
        if (l.contains("off")) Switch.OFF else if (l.contains("on")) Switch.ON
        else Switch.TOGGLE
    val p = Regex(""".+\s(\d+),(\d+)\s\w+\s(\d+),(\d+)""").toPattern()
    val m = p.matcher(l)
    if (m.find()) {
        return Instruct(sw, m.group(1).toInt(), m.group(2).toInt(),
            m.group(3).toInt(), m.group(4).toInt())
    } else {
        throw Exception("Parse Error : %l")
    }
}

fun doInstruction (inst: Instruct) {
    for (y in inst.sy..inst.ey) {
        for (x in inst.sx..inst.ex) {
            when (inst.sw) {
                Switch.OFF -> {
                    light2s[y][x] -= 1
                    if (light2s[y][x] < 0) light2s[y][x] = 0
                }
                Switch.ON -> {
                    light2s[y][x] += 1
                }
                else -> light2s[y][x] += 2
            }
        }
    }
}

fun main (args: Array<String>) {
    val f = java.io.File(Switch.OFF.javaClass.getResource("input.txt").toURI())
    val list =
        f.readLines().map { parseLine(it) }
    list.forEach { doInstruction(it) }

    var sum = 0
    for (y in 0..999) {
        for (x in 0..999) {
            sum += light2s[y][x]
        }
    }

    println (sum)
}

