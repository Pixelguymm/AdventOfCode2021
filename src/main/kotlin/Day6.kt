fun main() {
    val input = {}::class.java.getResource("Day6.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val fish = input.split(",").map { f ->
        f.trim().toInt()
    }

    Day6(fish).also {
        it.partOne()
        it.partTwo()
    }
}

class Day6(private val fish : List<Int>) {
    fun partOne() {
        println(getFish(80))
    }

    fun partTwo() {
        println(getFish(256))
    }

    private fun getFish( days : Int) : Long {
        val cycles = mutableMapOf<Int, Long>()
        this.fish.sorted().forEach { i ->
            cycles[i] = (cycles[i] ?: 0) + 1
        }

        for (d in 1..days) {
            val kids = cycles[0] ?: 0
            for (i in 0..7) {
                cycles[i] = cycles[i + 1] ?: 0
            }
            cycles[8] = kids
            cycles[6] = (cycles[6] ?: 0) + kids
        }

        return cycles.values.sum()
    }
}