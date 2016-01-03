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

fun searchToEnd(g: HashMap<String, HashSet<LocAndDist>>, cur: String, visited: LinkedHashSet<String>, distance: Int) {
    val list = g[cur]!!.toList()
    val needvisitedlist = list.filter {!visited.contains(it.loc)}
    val needcount = needvisitedlist.size
    if (needcount > 0) {
        for ((i,d) in needvisitedlist) {
            visited.add(i)
            searchToEnd(g, i, visited, distance + d)
            visited.remove(i)
        }
    } else {
        results.add (distance)
    }
}

fun findAWay (start: String, graph: HashMap<String, HashSet<LocAndDist>>) {
    val list = graph[start]!!.toList()
    val visited = linkedSetOf(start)
    for ((l,d) in list) {
        if (!visited.contains(l)) {
            visited.add(l)
            searchToEnd(graph, l, visited, d)
            visited.remove(l)
        }
    }
}

fun main (args: Array<String>) {
    val graph = createGraph()
    for (i in graph.keys) {
        findAWay (i, graph)
    }

    println ("Shortest Route : ${results.min()}")
    println ("Longest Route : ${results.max()}")
}
