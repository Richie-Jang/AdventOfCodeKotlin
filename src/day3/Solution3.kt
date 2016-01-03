package day3

import java.io.File

/**
 * Created by richi on 2016-01-01.
 */
class CheckPath

fun nextMove (prePos: Pair<Int,Int>, sign: Char): Pair<Int,Int> {
    when (sign) {
        '^' -> return Pair(prePos.first, prePos.second+1)
        'v' -> return Pair(prePos.first, prePos.second-1)
        '<' -> return Pair(prePos.first-1, prePos.second)
        else -> return Pair(prePos.first+1, prePos.second)
    }
}

fun main (args: Array<String>) {
    val p = CheckPath().javaClass.getResource("input.txt").toURI()
    val line = File(p).readText()
    var houseCount = 1
    val visitedMap = hashSetOf<Pair<Int,Int>>()
    var lastSantPos = Pair<Int,Int>(0,0)
    var lastRobotPos = Pair<Int,Int>(0,0)
    // set first poistion in Set
    visitedMap.add(lastSantPos)

    line.forEachIndexed { count, sign ->
        var position: Pair<Int,Int>
        if (count % 2 == 0) {
            // Santa
            lastSantPos = nextMove(lastSantPos, sign)
            position = lastSantPos
        } else {
            // Santa-Robot
            lastRobotPos = nextMove(lastRobotPos, sign)
            position = lastRobotPos
        }
        if (!visitedMap.contains(position)) {
            houseCount ++;
            visitedMap.add(position)
        }
    }

    println (houseCount)
}