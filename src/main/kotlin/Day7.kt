import kotlin.math.abs

fun main() {
    val input = {}::class.java.getResource("Day7.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val positions = input.split(",").map { f ->
        f.trim().toInt()
    }

    Day7().also {
        it.partOne(positions)
        it.partTwo(positions)
    }
}

class Day7 {
    fun partOne(positions : List<Int>) {
        println(getOptimalPosition(positions, ::getFuel))
    }

    fun partTwo(positions : List<Int>) {
        println(getOptimalPosition(positions, ::getFuelByDistance))
    }

    private fun getOptimalPosition(positions : List<Int>, method: (List<Int>, Int) -> Int): Int {
        var min = positions.minOrNull() ?: 0
        var max = positions.maxOrNull() ?: 0

        while (max - min > 1) {
            val average = (max + min) / 2
            val upper = method(positions, average)
            val lower = method(positions, average - 1)

            if (upper < lower) {
                min = average
            }
            else {
                max = average
            }
        }

        return method(positions, min)
    }

    private fun getFuel(positions : List<Int>, goal : Int) : Int {
        var fuel = 0
        for (i in positions) {
            fuel += abs(i - goal)
        }
        return fuel
    }

    private fun getFuelByDistance(positions : List<Int>, goal : Int) : Int {
        var fuel = 0
        for (i in positions) {
            fuel += List(abs(i - goal)) { it + 1}.sum()
        }
        return fuel
    }
}
