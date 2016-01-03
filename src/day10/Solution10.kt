package day10

fun lookandsay (value: String): String {
    var cur = ' '
    var count = 0
    val list = arrayListOf<Pair<Char, Int>>()
    for (i in value) {
        if (cur == i) {
            count ++
        } else {
            if (cur != ' ') {
                list.add(Pair(cur, count))
                cur = i
                count = 1
            } else {
                cur = i
                count++
            }
        }
    }
    list.add(Pair(cur,count))
    return list.map{ it.second.toString() + it.first.toString() }.joinToString(separator = "")
}

fun main(args: Array<String>) {
    val start = "1321131112"
    val result = (1..50).fold(start) {acc, i -> lookandsay(acc)}
    println (result.length)
}