fun main() {
    val input = {}::class.java.getResource("Day12.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val connections = mutableMapOf<String, List<String>>()

    val lines = input.split("\n").map { l ->
        l.trim().split("-").map { c ->
            c.trim()
        }
    }
    for (l in lines) {
        connections[l[0]] = connections[l[0]]?.plus(l[1]) ?: listOf(l[1])
        connections[l[1]] = connections[l[1]]?.plus(l[0]) ?: listOf(l[0])
    }

    Day12(connections).also {
        it.partOne()
        it.partTwo()
    }
}

class Day12(private val connections : Map<String, List<String>>) {
    fun partOne() {
        println(countPaths(listOf(), "start"))
    }

    fun partTwo() {
        println(countPathsTwice(listOf(), "start", false))
    }

    private fun countPaths(visited : List<String>, start : String) : Int {
        if (start.lowercase() == start && visited.contains(start)) return 0
        if (start == "end") return 1

        return this.connections[start]?.sumOf { c -> countPaths(visited.plus(start), c) } ?: 0
    }

    private fun countPathsTwice(visited : List<String>, start : String, visitedTwice : Boolean) : Int {
        var v = visitedTwice
        if (start == "start" && visited.contains(start)) return 0
        if (start.lowercase() == start) {
            if (visitedTwice && visited.contains(start)) return 0
            if (!visitedTwice && visited.count { c -> c == start } > 0) v = true
        }
        if (start == "end") return 1

        return this.connections[start]?.sumOf { c -> countPathsTwice(visited.plus(start), c, v) } ?: 0
    }
}