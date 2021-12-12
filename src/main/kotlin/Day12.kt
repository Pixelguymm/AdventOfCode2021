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
        connections[l[0]] = connections[l[0]]?.plus(l[1]) ?:  listOf(l[1])
        connections[l[1]] = connections[l[1]]?.plus(l[0]) ?:  listOf(l[0])
    }
    println(connections)

    Day12().also {
        it.partOne(connections)
        it.partTwo(connections)
    }
}

class Day12 {
    fun partOne(connections : Map<String, List<String>>) {
        println(countPaths(connections, listOf(), "start"))
    }

    fun partTwo(connections : Map<String, List<String>>) {
        println(countPathsTwice(connections, listOf(), "start", false))
    }

    private fun countPaths(map : Map<String, List<String>>, visited : List<String>, start : String) : Int {
        if (start.lowercase() == start && visited.contains(start)) return 0
        if (start == "end") return 1

        return map[start]?.sumOf { c -> countPaths(map, visited.plus(start), c) } ?: 0
    }

    private fun countPathsTwice(map : Map<String, List<String>>, visited : List<String>, start : String, visitedTwice : Boolean) : Int {
        var v = visitedTwice
        if (start == "start" && visited.contains(start)) return 0
        if (start.lowercase() == start) {
            if (visitedTwice && visited.contains(start)) return 0
            if (!visitedTwice && visited.count { c -> c == start } > 0) v = true
        }
        if (start == "end") return 1

        return map[start]?.sumOf { c -> countPathsTwice(map, visited.plus(start), c, v) } ?: 0
    }
}