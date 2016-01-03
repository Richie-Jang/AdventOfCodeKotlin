package day8

/**
 * Created by richi on 2016-01-02.
 */
class CheckPath

// Part1
fun getCountEscapLiteralAndChars (str: String): Pair<Int,Int> {
    var isChecking = false
    var resultLiteral = 0
    var resultChars = 0
    var xCount = 0
    for (i in 0 until str.length) {
        if (!isChecking && str[i] == '\\') {
            isChecking = true
            continue
        }

        if (isChecking) {
            val c = str[i]
            if (xCount == 0) {
                if (c == '\\') {
                    resultLiteral ++; isChecking = false
                } else if (c == '\"') {
                    resultLiteral ++; isChecking = false
                } else if (c == 'x') {
                    xCount ++
                }
            } else {
                if (xCount < 2) xCount++ else if (xCount == 2) {
                    resultLiteral ++
                    xCount = 0
                    isChecking = false
                }
            }
        } else {
            resultChars ++
        }
    }
    return Pair(resultLiteral, resultChars)
}

fun part2GetNewStringCount (str: String): Int {
    val doubleQuote = str.filter { it == '\"' }.length
    val backslash = str.filter { it == '\\'}.length
    return str.length + 2 + doubleQuote + backslash
}

fun testCode() {
    val t1 = """"""""
    val t2 = """"abc""""
    val t3 = """"aaa\"aaa""""
    val t4 = """"\x27""""

    val list = arrayListOf(t1,t2,t3,t4)
                .map { l ->
                    val t = l.length
                    val (a,b) = getCountEscapLiteralAndChars(l)
                    Pair(t, a+b-2)
                }
    println (list.fold(0) {acc, i -> acc+i.first-i.second})

}

fun testCode2() {
//    val t1 = """"""""
//    println (t1.replace("\"", "\\\""))
//    val t2 = """"abc""""
//    println (t2.replace("\"", "\\\""))
//    val t3 = """\x27a\xabc"""
//    println (t3.replace("\\x", "\\\\x"))
    val t1 = """"""""
    val t2 = """"abc""""
    val t3 = """"aaa\"aaa""""
    val t4 = """"\x27""""
    val list = listOf(t1,t2,t3,t4)
    println (list.map {Pair(it.length, part2GetNewStringCount(it))}
                .fold(0) { acc, i -> acc + i.second - i.first})
}

fun main (args: Array<String>) {
    val f = java.io.File(CheckPath::class.java.getResource("input.txt").toURI())
    val list = f.readLines()
                .map {line ->
                    val totallength = line.length
                    //val (a,b) = getCountEscapLiteralAndChars(line)
                    //val stringlength = (b+a)-2
                    //Pair(totallength, stringlength)}
                    Pair(totallength, part2GetNewStringCount(line))}
    //println (list.fold (0) {acc, item -> acc + item.first - item.second})
    println (list.fold (0) {acc, item -> acc + item.second - item.first})
    //testCode()

    //testCode2()
}