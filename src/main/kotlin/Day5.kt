import kotlin.math.abs
import kotlin.math.max

fun main() {
    val input = {}::class.java.getResource("Day5.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val lines = input.split("\n").map { l ->
        l.split(" -> ").map { c ->
            c.split(",").map { n ->
                n.toInt()
            }
        }
    }

    Day5(lines).also {
        it.partOne()
        it.partTwo()
    }
}

class Day5(private val lines : List<List<List<Int>>>) {
    fun partOne() {
        val straightLines = this.lines.filter { l ->
            l[0][0] == l[1][0] ||
            l[0][1] == l[1][1]
        }

        findDuplicates(straightLines)
    }

    fun partTwo() {
        findDuplicates(this.lines)
    }

    private fun findDuplicates(lines : List<List<List<Int>>>) {
        val points = mutableMapOf<String, Int>()

        for (i in lines.indices) {
            val l = lines[i]
            val pts = getPoints(l[0], l[1])

            for (p in pts) {
                val key = "${p[0]}/${p[1]}"
                val value = points[key]
                if (
                    value !== null
                ) points[key] = value + 1
                else points[key] = 1
            }
        }

        println(points.values.filter { c -> c >= 2 }.size)
    }

    private fun getPoints(start : List<Int>, end : List<Int>) : List<List<Int>> {
        val difX = end[0] - start[0]
        val difY = end[1] - start[1]

        var pts = listOf(start)
        if (difX == 0 && difY == 0) {
            return pts
        }

        val steps = max(abs(difX), abs(difY))
        pts = List(steps + 1) { i ->
            listOf(start[0] + (difX / steps * i), start[1] + (difY / steps * i))
        }
        return pts
    }
}