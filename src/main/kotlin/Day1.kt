fun main() {
    val input = {}::class.java.getResource("Day1.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val scans = input.split("\n").map { n ->
        n.trim().toInt()
    }

    Day1().also {
        it.partOne(scans)
        it.partTwo(scans)
    }
}

class Day1 {
    fun partOne(scans : List<Int>) = countIncreases(scans, 1)

    fun partTwo(scans : List<Int>) = countIncreases(scans, 3)

    private fun countIncreases(scans : List<Int>, gap : Int) {
        var counter = 0
        for (s in gap until scans.size) {
            if (scans[s] > scans[s - gap]) counter ++
        }

        println(counter)
    }
}