import kotlin.math.abs

fun main() {
    val input = {}::class.java.getResource("Day2.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val directions = input.split("\n").map { n ->
        n.trim().split(" ")
    }
    Day2().also {
        it.partOne(directions)
        it.partTwo(directions)
    }
}

class Day2 {
    fun partOne(directions : List<List<String>>) {
        var x = 0
        var y = 0
        for (d in directions) {
            val num = (d[1].toIntOrNull() ?: 0)
            if (d[0] == "up") y += num
            else if (d[0] == "down") y -= num
            else x += num
        }

        println(x * abs(y))
    }

    fun partTwo(directions : List<List<String>>) {
        var x = 0
        var y = 0
        var aim = 0
        for (d in directions) {
            val num = (d[1].toIntOrNull() ?: 0)
            if (d[0] == "up") aim += num
            else if (d[0] == "down") aim -= num
            else {
                x += num
                y += aim * num
            }
        }

        println(x * abs(y))
    }
}