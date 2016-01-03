package day7

import java.io.File

/**
 * Created by richi on 2015-12-30.
 */

class CheckPath()
/*
Test Input

123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i
 */


val lines = """
123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i"""

fun parseLine (l: String): Circuit {
    if (l.contains("NOT")) {
        // Not Parsing
        val p = Regex("""NOT (\w+) \-> (\w+)""")
        val m = p.toPattern().matcher(l)
        if (m.find()) {
            return Circuit.UnaryOp (Gates.NOT, CreateWireOrValue(m.group(1)), CreateWire(m.group(2)))
        } else {
            throw Exception ("NOT: Parsing Error -> $l")
        }
    } else if (l.contains("AND") || l.contains("OR")) {
        // AND OR  parsing
        val p = Regex("""(\w+) (AND|OR) (\w+) \-> (\w+)""")
        val m = p.toPattern().matcher(l)
        if (m.find()) {
            val g = if (m.group(2) == "AND") Gates.AND else Gates.OR
            return Circuit.BinaryOp(g, CreateWireOrValue(m.group(1)), CreateWireOrValue(m.group(3)), CreateWire(m.group(4)))
        } else {
            throw Exception ("AND | OR: Parsing Error -> $l")
        }
    } else if (l.contains("LSHIFT") || l.contains("RSHIFT")) {
        // LSHIFT RSHIFT parsing
        val p = Regex("""(\w+) (LSHIFT|RSHIFT) (\w+) \-> (\w+)""")
        val m = p.toPattern().matcher(l)
        if (m.find()) {
            val g = if (m.group(2) == "LSHIFT") Gates.LSHIFT else Gates.RSHIFT
            return Circuit.BinaryOp(g, CreateWire(m.group(1)), CreateValue(m.group(3).toInt()), CreateWire(m.group(4)))
        } else {
            throw Exception ("LSHIFT | RSHIFT: Parsing Error -> $l")
        }
    } else {
        // assign parsing
        val p = Regex("""(\w+) \-> (\w+)""")
        val m = p.toPattern().matcher(l)
        if (m.find()) {
            return Circuit.UnaryOp(Gates.ASSIGN,CreateWireOrValue(m.group(1)),CreateWire(m.group(2)))
        } else {
            throw Exception ("Assign: Parsing Error -> $l")
        }
    }
}

fun main (args : Array<String>) {
    //val list = lines.split("\n").filter{ it != "" }.map { parseLine(it) }
    val path = CheckPath().javaClass.getResource("input.txt").toURI()
    val list = File(path).readLines().map { parseLine(it) }

    val vmap = hashMapOf<String, Int>()

    // extract Assign Gate
    list.filter { it is Circuit.UnaryOp && it.op == Gates.ASSIGN }
        .forEach { (it as Circuit.UnaryOp).eval(vmap) }

    if (vmap.containsKey("b")) vmap.set("b", 956) else vmap.put("b", 956)

    val q = linkedListOf<Circuit>()
    q.addAll (list.filterNot { it is Circuit.Value || it is Circuit.Wire })

    while (q.isNotEmpty()) {
        val cur = q.poll()
        if (cur is Circuit.BinaryOp) {
            if (!cur.eval(vmap)) q.addLast(cur)
        }
        if (cur is Circuit.UnaryOp) {
            if (cur.c1 is Circuit.Value) {
                if (cur.op == Gates.ASSIGN && cur.cwire == Circuit.Wire("b")) continue
            }
            if (!cur.eval(vmap)) q.addLast(cur)
        }
    }
    //println ("Map Size : ${vmap.size}")
    vmap.forEach { s, i -> println ("$s -> $i") }
    println ("RESULT a -> ${vmap.get("a")}")
}
