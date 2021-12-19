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

    Day7(positions).also {
        it.partOne()
        it.partTwo()
    }
}

class Day7(private val positions : List<Int>) {
    fun partOne() {
        println(getOptimalPosition(::getFuel))
    }

    fun partTwo() {
        println(getOptimalPosition(::getFuelByDistance))
    }

    private fun getOptimalPosition(method: (Int) -> Int): Int {
        var min = this.positions.minOrNull() ?: 0
        var max = this.positions.maxOrNull() ?: 0

        while (max - min > 1) {
            val average = (max + min) / 2
            val upper = method(average)
            val lower = method(average - 1)

            if (upper < lower) {
                min = average
            }
            else {
                max = average
            }
        }

        return method(min)
    }

    private fun getFuel(goal : Int) : Int {
        var fuel = 0
        for (i in this.positions) {
            fuel += abs(i - goal)
        }
        return fuel
    }

    private fun getFuelByDistance(goal : Int) : Int {
        var fuel = 0
        for (i in this.positions) {
            fuel += List(abs(i - goal)) { it + 1}.sum()
        }
        return fuel
    }
}
