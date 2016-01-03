package day9

import java.util.*

class CheckPath

data class LocAndDist (val loc: String, val dist: Int)
val inputfile = java.io.File(CheckPath::class.java.getResource("input.txt").toURI())

fun createGraph(): HashMap<String, HashSet<LocAndDist>> {
    fun parseLine (s: String): Pair<String, LocAndDist> {
        val aa = s.split(" to ", " = ")
        return Pair(aa[0], LocAndDist(aa[1], aa[2].toInt()))
    }
    return inputfile.readLines().map { parseLine(it) }
            .fold(hashMapOf<String, HashSet<LocAndDist>>()) {s,item ->
                val k = item.first
                val v = item.second
                if (s.containsKey(k)) s[k]?.add(v)
                else s.put(k, hashSetOf(v))
                val k1 = v.loc
                val v2 = LocAndDist(k, v.dist)
                if (s.containsKey(k1)) s[k1]?.add(v2) else s.put(k1, hashSetOf(v2))
                s
            }
}

val results = arrayListOf<Int>()

fun findAWay (loc: String, graph: HashMap<String, HashSet<LocAndDist>>, visited: LinkedHashSet<String>, dist: Int) {
    val list = graph[loc]!!.toList()
    val needVisits = list.filter { !visited.contains(it.loc) }
    val needCount = needVisits.size
    if (needCount > 0) {
        needVisits.forEach {
            visited.add(it.loc)
            findAWay(it.loc, graph, visited, dist + it.dist)
            visited.remove(it.loc)
        }
    } else {
        results.add(dist)
    }
}

fun main (args: Array<String>) {
    val graph = createGraph()
    for (i in graph.keys) {
        findAWay (i, graph, linkedSetOf(i), 0)
    }

    println ("Shortest Route : ${results.min()}")
    println ("Longest Route : ${results.max()}")
}
