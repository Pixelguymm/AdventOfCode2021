fun main() {
    val input = {}::class.java.getResource("Day1.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val scans = input.split("\n").map { n ->
        n.trim().toInt()
    }

    Day1(scans).also {
        it.partOne()
        it.partTwo()
    }
}

class Day1(private val scans : List<Int>) {
    fun partOne() = countIncreases(1)

    fun partTwo() = countIncreases(3)

    private fun countIncreases(gap : Int) {
        var counter = 0
        for (s in gap until this.scans.size) {
            if (this.scans[s] > this.scans[s - gap]) counter ++
        }

        println(counter)
    }
}